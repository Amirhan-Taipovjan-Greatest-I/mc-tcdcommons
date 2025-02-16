package io.github.thecsdev.tcdcommons.api.server.registry;

import io.github.thecsdev.tcdcommons.api.registry.TCDCommonsRegistry;

/**
 * Contains registries specific to dedicated servers.<br/>
 * Must not be used for "common" code that is shared with client code.
 */
public final class TCDCommonsServerRegistry extends TCDCommonsRegistry
{
	// ==================================================
	protected TCDCommonsServerRegistry() {}
	// ==================================================
	/**
	 * Calls the static constructor for this class
	 * if it hasn't been called yet.
	 */
	public static void init() {}
	static
	{
		// ---------- server-side player badge registration process
		//registering before the server starts...
		/*LifecycleEvent.SERVER_BEFORE_START.register(server ->
		{
			//clear it first in case any were left over, and then register new badges
			PlayerSessionBadges.clear();
			PLAYER_BADGE.invoker().badgeRegistrationCallback(PlayerSessionBadges);
		}); -- or.. a mod can just register directly, like with any other registry...*/
		//...and clearing after the server stops (not really needed)
		//LifecycleEvent.SERVER_STOPPED.register(server -> PlayerSessionBadges.clear()); --not needed
	}
	// ==================================================
}