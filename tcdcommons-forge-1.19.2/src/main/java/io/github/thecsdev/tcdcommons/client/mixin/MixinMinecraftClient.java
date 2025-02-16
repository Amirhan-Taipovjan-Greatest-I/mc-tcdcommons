package io.github.thecsdev.tcdcommons.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.thecsdev.tcdcommons.api.client.registry.TCDCommonsClientRegistry;
import net.minecraft.client.Minecraft;

@Mixin(Minecraft.class)
public abstract class MixinMinecraftClient
{
	// ==================================================
	/*
	 * A reference to the {@link #onOpened()} method that used
	 * my mixins to invoke that method when this {@link TScreen}
	 * is opened. Please avoid using this yourself, as it may
	 * be subject to changes.
	 *
	private java.lang.reflect.Method TSCREEN_METHOD_ONOPENED;
	// ==================================================
	@Shadow public Screen screen;
	// ==================================================
	@Inject(method = "<init>", at = @At("RETURN"), require = 1) //TODO - is it risky to inject into init?
	public void onInit(GameConfig args, CallbackInfo callback)
	{
		//handle reflection
		try
		{
			TSCREEN_METHOD_ONOPENED = TScreen.class.getDeclaredMethod("onOpened");
			TSCREEN_METHOD_ONOPENED.setAccessible(true);
		}
		catch(NoSuchMethodException e)
		{
			String msg = "[" + TCDCommons.getModID() + "] " + "Failed to obtain TScreen#onOpened()";
			TCDCommons.crash(msg, e);
			//throw new CrashException(new CrashReport(msg, e));
		}
		//test HUD screen - may annoy others using this mod in dev. environment
		/*if(FabricLoader.getInstance().isDevelopmentEnvironment())
		{
			var sid = new Identifier(TCDCommons.getModID(), "hud_test");
			TCDCommonsClientRegistry.InGameHud_Screens.put(sid, new TestTScreenHud());
		}*
	}
	// --------------------------------------------------
	@Inject(method = "setScreen", at = @At("RETURN"))
	public void onSetScreen(Screen screen, CallbackInfo callback)
	{
		if(screen instanceof TScreen && this.screen == screen)
		{
			try { TSCREEN_METHOD_ONOPENED.invoke(((TScreen)screen)); }
			catch(Exception e)
			{
				String msg = "[" + TCDCommons.getModID() + "] " + "Failed to invoke TScreen#onOpened()";
				TCDCommons.crash(msg, e);
				//throw new CrashException(new CrashReport(msg, e));
			}
		}
	}*/
	// --------------------------------------------------
	@Inject(method = "resizeDisplay", at = @At("RETURN"))
	public void onResolutionChanged(CallbackInfo callback)
	{
		//basically update the sizes of hud screens
		TCDCommonsClientRegistry.reInitHudScreens();
	}
	// ==================================================
}