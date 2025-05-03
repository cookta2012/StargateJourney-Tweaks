package com.cookta2012.sgjourneytweaks.config;


import net.minecraftforge.common.ForgeConfigSpec;
import net.povstalec.sgjourney.common.config.SGJourneyConfigValue.*;

public class CommonInventoryProtectionConfig {

	public static BooleanValue zpm_hub_disable_automation;
	public static BooleanValue dhd_disable_automation;
	
	public static void init(ForgeConfigSpec.Builder server)
	{
		zpm_hub_disable_automation = new BooleanValue(server, "zpm_hub_disable_automation", 
				true, 
				"This tells if we disable the sided inventory of the protected ZPM Hubs");
		dhd_disable_automation = new BooleanValue(server, "dhd_disable_automation", 
				true, 
				"This tells if we disable the sided inventory of the protected DHDs");

	}

}
