package com.cookta2012.sgjourneytweaks.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.povstalec.sgjourney.common.config.SGJourneyConfigValue.*;


public class CommonPermissionConfig {

	public static IntValue protected_zpm_hub_permissions;
	public static IntValue protected_rings_panel_permissions;
	public static IntValue protected_transport_rings_permissions;
	
	public static void init(ForgeConfigSpec.Builder server)
	{
		protected_zpm_hub_permissions = new IntValue(server, "server.protected_zpm_hub_permissions",
				0, 0, 4,
				"Decides the player permission level required to modify or break protected ZPM Hubs");
		
		protected_rings_panel_permissions = new IntValue(server, "server.protected_rings_panel_permissions",
				0, 0, 4,
				"Decides the player permission level required to modify or break protected Rings Panels (This does not include the inventory inside the panel)");
		
		protected_transport_rings_permissions = new IntValue(server, "server.protected_transport_rings_permissions",
				0, 0, 4,
				"Decides the player permission level required to modify or break protected Transport Rings");
		
	}

}
