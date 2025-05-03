package com.cookta2012.sgjourneytweaks.mixin.stargate;

import net.povstalec.sgjourney.common.block_entities.stargate.ClassicStargateEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.cookta2012.sgjourneytweaks.config.CommonStargateConfig;

@Mixin(ClassicStargateEntity.class)
public abstract class ClassicStargateEntityMixin {
    /** Prevent the constructor from ever calling displayID() */
    @Redirect(
        method = "<init>(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
        at = @At(
            value  = "INVOKE",
            target = "Lnet/povstalec/sgjourney/common/block_entities/stargate/ClassicStargateEntity;displayID()V"
        ),
	    remap  = false
    )
    private void sgpatch$skipDisplayID(ClassicStargateEntity self) {
    	if(!CommonStargateConfig.classic_gate_disable_auto_id.get()) {
    		self.displayID();
    	}
    }
}
