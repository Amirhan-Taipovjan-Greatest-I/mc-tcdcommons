package io.github.thecsdev.tcdcommons;

import static io.github.thecsdev.tcdcommons.api.registry.TCDCommonsRegistry.registerCommandArgumentType;

import java.util.NoSuchElementException;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import io.github.thecsdev.tcdcommons.command.PlayerBadgeCommand;
import io.github.thecsdev.tcdcommons.command.argument.PlayerBadgeIdentifierArgumentType;
import io.github.thecsdev.tcdcommons.network.TCDCommonsNetworkHandler;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.util.Identifier;

public class TCDCommons extends Object
{
	// ==================================================
	public static final Logger LOGGER = LoggerFactory.getLogger(getModID());
	// --------------------------------------------------
	private static final String ModID = "tcdcommons";
	private static TCDCommons Instance;
	// --------------------------------------------------
	private final ModContainer modInfo;
	private final TCDCommonsConfig config;
	// ==================================================
	/**
	 * Initializes this mod. This action may only be performed by the {@link FabricLoader}.
	 * @throws IllegalStateException When attempting to initialize another instance of this mod.
	 * @throws UnsupportedOperationException When attempting to initialize a class that extends this one.
	 * @throws NoSuchElementException If the {@link FabricLoader} fails to retrieve this mod's info.
	 */
	public TCDCommons()
	{
		//validate instance first
		if(isModInitialized())
			throw new IllegalStateException(getModID() + " has already been initialized.");
		else if(!isInstanceValid(this))
			throw new UnsupportedOperationException("Invalid " + getModID() + " type: " + this.getClass().getName());
		
		//assign instance
		Instance = this;
		modInfo = FabricLoader.getInstance().getModContainer(getModID()).get();
		
		//log stuff
		LOGGER.info("Initializing '" + getModName() + "' " + modInfo.getMetadata().getVersion() +
				" as '" + getClass().getSimpleName() + "'.");
		
		//load config
		this.config = new TCDCommonsConfig(getModID());
		this.config.loadFromFileOrCrash(true);
		
		//init stuff
		TCDCommonsNetworkHandler.init();
		
		//register commands (must load config first)
		registerCommandArgumentType(
				new Identifier(getModID(), "player_badge_identifier"),
				PlayerBadgeIdentifierArgumentType.class,
				PlayerBadgeIdentifierArgumentType::pbId);
		CommandRegistrationEvent.EVENT.register((dispatcher, regAcc, regEnv) ->
		{
			PlayerBadgeCommand.register(dispatcher);
		});
	}
	// --------------------------------------------------
	/** Returns the Fabric {@link ModContainer} the containing information about this mod. */
	public ModContainer getModInfo() { return modInfo; }
	// ==================================================
	/** Returns the instance of this mod. */
	public static @Nullable TCDCommons getInstance() { return Instance; }
	public TCDCommonsConfig getConfig() { return this.config; }
	// --------------------------------------------------
	public static String getModName() { return getInstance().getModInfo().getMetadata().getName(); }
	public static String getModID() { return ModID; }
	// --------------------------------------------------
	public static boolean isModInitialized() { return isInstanceValid(Instance); }
	private static boolean isInstanceValid(TCDCommons instance) { return isServer(instance) || isClient(instance); }
	// --------------------------------------------------
	public static boolean isServer() { return isServer(Instance); }
	public static boolean isClient() { return isClient(Instance); }
	
	private static boolean isServer(TCDCommons arg0) { return arg0 instanceof io.github.thecsdev.tcdcommons.server.TCDCommonsServer; }
	private static boolean isClient(TCDCommons arg0) { return arg0 instanceof io.github.thecsdev.tcdcommons.client.TCDCommonsClient; }
	// ==================================================
}