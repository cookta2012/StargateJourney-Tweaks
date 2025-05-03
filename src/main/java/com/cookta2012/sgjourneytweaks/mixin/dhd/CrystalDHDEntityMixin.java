package com.cookta2012.sgjourneytweaks.mixin.dhd;

import com.cookta2012.sgjourneytweaks.config.CommonInventoryProtectionConfig;
import com.cookta2012.sgjourneytweaks.utils.GatedItemHandler;
import javax.annotation.Nullable;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.povstalec.sgjourney.common.block_entities.dhd.CrystalDHDEntity;


@Mixin(CrystalDHDEntity.class)
public abstract class CrystalDHDEntityMixin {
	
	@Shadow(remap=false) protected LazyOptional<IItemHandler> handler;
	
    /* cached wrapper – one per tile-entity */
    @Unique private LazyOptional<IItemHandler> SGJTweaks$gatedSlotCap;

    /* supply the wrapper instead of LazyOptional.empty() */
    @Inject(
        method       = "getCapability(Lnet/minecraftforge/common/capabilities/Capability;Lnet/minecraft/core/Direction;)Lnet/minecraftforge/common/util/LazyOptional;",
        at           = @At("HEAD"),
        cancellable  = true,
        remap        = false
    )
    private <T> void SGJTweaks$getCapability(Capability<T> cap, @Nullable Direction side,
                                      CallbackInfoReturnable<LazyOptional<T>> cir) {
    	if(CommonInventoryProtectionConfig.dhd_disable_automation.get()) {
	        if (cap == ForgeCapabilities.ITEM_HANDLER && side != null) {   // face query
	            if (SGJTweaks$gatedSlotCap == null) {
	                IItemHandler backend = handler.orElseThrow(() -> new IllegalStateException("DHD has no internal handler"));
	                SGJTweaks$gatedSlotCap = LazyOptional.of(() -> new GatedItemHandler<CrystalDHDEntity>((CrystalDHDEntity)(Object)this, backend));
	            }
	            cir.setReturnValue(SGJTweaks$gatedSlotCap.cast());
	        }
    	}
    }

    /* make sure the wrapper goes away when the tile-entity’s caps do */
    @Inject(method = "invalidateCaps", at = @At("HEAD"), remap = false)
    private void SGJTweaks$invalidate(CallbackInfo ci) {
        if (SGJTweaks$gatedSlotCap != null) {
        	SGJTweaks$gatedSlotCap.invalidate();
        	SGJTweaks$gatedSlotCap = null;
        }
    }
}
