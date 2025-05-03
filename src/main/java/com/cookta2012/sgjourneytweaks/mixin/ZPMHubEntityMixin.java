package com.cookta2012.sgjourneytweaks.mixin;


import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.cookta2012.sgjourneytweaks.config.CommonInventoryProtectionConfig;
import com.cookta2012.sgjourneytweaks.config.CommonPermissionConfig;
import com.cookta2012.sgjourneytweaks.mixin.utils.InvokerBlockEntityMixin;
import com.cookta2012.sgjourneytweaks.utils.GatedItemHandler;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.povstalec.sgjourney.common.block_entities.ProtectedBlockEntity;
import net.povstalec.sgjourney.common.block_entities.tech.ZPMHubEntity;

@Mixin(ZPMHubEntity.class)
public abstract class ZPMHubEntityMixin implements ProtectedBlockEntity, InvokerBlockEntityMixin {
	
	@Shadow(remap=false) protected LazyOptional<IItemHandler> lazyItemHandler;
	
    /* cached wrapper – one per tile-entity */
    @Unique private LazyOptional<IItemHandler> SGJTweaks$gatedSlotCap;

    @Unique private boolean isProtected = false;  // your own storage

    @Override
    public void setProtected(boolean value)
    {
        if (this.isProtected == value)
            return;                          // no change → nothing to do

        this.isProtected = value;

        /* 1 ▸  Only drop capabilities when we are SWITCHING **ON**
         *       protection.  When we switch it off we must *not* break
         *       the menu that is already open. */

        /* mark dirty & notify clients (unchanged) */
        this.setChanged();
        Level level = this.getLevel();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(
                    this.getBlockPos(),
                    this.getBlockState(),
                    this.getBlockState(), 3);
        }
    }

    @Override
    public boolean isProtected() {
        return this.isProtected;
    }

    @Override
	public boolean hasPermissions(Player player, boolean sendMessage)
	{
		if(isProtected() && !player.hasPermissions(CommonPermissionConfig.protected_zpm_hub_permissions.get()))
		{
			if(sendMessage)
				player.displayClientMessage(Component.translatable("block.sgjourney.protected_permissions").withStyle(ChatFormatting.DARK_RED), true);
			
			return false;
		}
		
		return true;
	}
    
    // ---------------- Inject into loadAdditional ----------------
    
    @Inject(
    		  method = "load(Lnet/minecraft/nbt/CompoundTag;)V",
    		  at     = @At("RETURN")
    		)
    private void onLoadAdditional(CompoundTag nbt, CallbackInfo ci) {
        if (nbt.contains("protected")) {
            this.setProtected(nbt.getBoolean("protected"));
        }
    }

    // ---------------- Inject into saveAdditional ----------------

    @Inject(
            method = "saveAdditional(Lnet/minecraft/nbt/CompoundTag;)V",
            at     = @At("RETURN")
        )
        private void afterSaveAdditional(CompoundTag nbt, CallbackInfo ci) {
            nbt.putBoolean("protected", this.isProtected);
        }
    
    // ------------- capability gating -------------
    /* ------------------------------------------------------------------
    ITEM_HANDLER capability gate
 ------------------------------------------------------------------- */
    
    /* supply the wrapper instead of LazyOptional.empty() */
    @Inject(
        method       = "getCapability(Lnet/minecraftforge/common/capabilities/Capability;Lnet/minecraft/core/Direction;)Lnet/minecraftforge/common/util/LazyOptional;",
        at           = @At("HEAD"),
        cancellable  = true,
        remap        = false
    )
    private <T> void SGJTweaks$getCapability(Capability<T> cap, @Nullable Direction side,
                                      CallbackInfoReturnable<LazyOptional<T>> cir) {
    	if(CommonInventoryProtectionConfig.zpm_hub_disable_automation.get()) {
	        if (cap == ForgeCapabilities.ITEM_HANDLER && side != null) {   // face query
	            if (SGJTweaks$gatedSlotCap == null) {
	                IItemHandler backend = lazyItemHandler.orElseThrow(() -> new IllegalStateException("ZPMHubEntity has no internal handler"));
	                SGJTweaks$gatedSlotCap = LazyOptional.of(() -> new GatedItemHandler<ZPMHubEntityMixin>(this, backend));
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
