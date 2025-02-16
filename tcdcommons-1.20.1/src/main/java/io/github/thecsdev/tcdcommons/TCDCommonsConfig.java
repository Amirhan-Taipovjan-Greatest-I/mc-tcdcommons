package io.github.thecsdev.tcdcommons;

import io.github.thecsdev.tcdcommons.api.config.AutoConfig;
import io.github.thecsdev.tcdcommons.api.config.annotation.SerializedAs;

public class TCDCommonsConfig extends AutoConfig
{
	// ==================================================
	@SerializedAs("enablePlayerBadges") //mitigate side-effects of field renaming
	public boolean enablePlayerBadges;
	// ==================================================
	public TCDCommonsConfig(String name)
	{
		super(name);
		this.enablePlayerBadges = true;
	}
	// ==================================================
}