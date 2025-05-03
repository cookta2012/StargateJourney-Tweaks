
package com.cookta2012.sgjourneytweaks;

import org.slf4j.Logger;

import com.cookta2012.sgjourneytweaks.config.StargateJourneyTweaksConfig;
import com.mojang.logging.LogUtils;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;


@Mod("sgjourneytweaks")
public final class StargateJourneyTweaks {

	public static final Logger LOGGER = LogUtils.getLogger();

	//TODO: refactor each mixin into like 2 or 3 mixin types that can be applied to anything
	public StargateJourneyTweaks() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, StargateJourneyTweaksConfig.COMMON_CONFIG, "sgjourneytweaks-common.toml");
    }

}
