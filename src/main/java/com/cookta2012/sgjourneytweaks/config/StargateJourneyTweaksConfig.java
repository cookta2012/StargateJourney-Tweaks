/*
 * Totally not yoinked from
 * https://github.com/Povstalec/StargateJourney/blob/stargatejourney-1.20.1/src/main/java/net/povstalec/sgjourney/common/config/StargateJourneyConfig.java
 * Thanks Postalec (Wold)
 * 
 */

package com.cookta2012.sgjourneytweaks.config;

import java.io.File;

import com.cookta2012.sgjourneytweaks.StargateJourneyTweaks;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class StargateJourneyTweaksConfig
{
	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec COMMON_CONFIG;

    static
	{
		COMMON_BUILDER.push("Stargate Journey Common Config");
		
		generalServerConfig(COMMON_BUILDER);
		
		COMMON_BUILDER.push("Permission Config");
		CommonPermissionConfig.init(COMMON_BUILDER);
		COMMON_BUILDER.pop();
		
		COMMON_BUILDER.push("Inventory Config");
		CommonInventoryProtectionConfig.init(COMMON_BUILDER);
		COMMON_BUILDER.pop();
		
		COMMON_BUILDER.push("Stargate Config");
		CommonStargateConfig.init(COMMON_BUILDER);
		COMMON_BUILDER.pop();

		
		COMMON_BUILDER.pop();
		COMMON_CONFIG = COMMON_BUILDER.build();
	}
	
	public static void loadConfig(ForgeConfigSpec config, String path)
	{
		StargateJourneyTweaks.LOGGER.info("Loading Config: " + path);
		final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
		StargateJourneyTweaks.LOGGER.info("Built config: " + path);
		file.load();
		StargateJourneyTweaks.LOGGER.info("Loaded Config: " + path);
		config.setConfig(file);
	}
	
	private static void generalServerConfig(ForgeConfigSpec.Builder server)
	{
		/*
		server.comment("Stargate Journey General Config");
		
		disable_energy_use = new SGJourneyConfigValue.BooleanValue(server, "server.disable_energy_requirements", 
				false,
				"Disable energy requirements for blocks added by Stargate Journey");
		*/
	}
	
	@SuppressWarnings("unused")
	private static void generalClientConfig(ForgeConfigSpec.Builder client)
	{
		/*
		client.comment("Stargate Journey Client Config");
		
		disable_smooth_animations = new SGJourneyConfigValue.BooleanValue(client, "client.disable_smooth_animations", 
				false, 
				"Disables smooth animations of Stargate Journey Block Entities");
		*/
	}
}