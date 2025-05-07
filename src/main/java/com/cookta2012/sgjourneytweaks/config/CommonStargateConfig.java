/*
 * Totally not yoinked from
 * https://github.com/Povstalec/StargateJourney/blob/stargatejourney-1.20.1/src/main/java/net/povstalec/sgjourney/common/config/StargateJourneyConfig.java
 * Thanks Postalec (Wold)
 * 
 */

package com.cookta2012.sgjourneytweaks.config;

import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.povstalec.sgjourney.common.config.SGJourneyConfigValue.*;

public class CommonStargateConfig {

	public static BooleanValue classic_gate_disable_auto_id;

	public static void init(Builder server) {
		classic_gate_disable_auto_id = new BooleanValue(server, "server.classic_gate_disable_auto_id",
				true,
				"This fixes the Classic Gate to act more like");
		
	}

}
