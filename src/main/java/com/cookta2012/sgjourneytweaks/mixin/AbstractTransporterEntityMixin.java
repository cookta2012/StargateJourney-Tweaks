package com.cookta2012.sgjourneytweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.cookta2012.sgjourneytweaks.config.CommonPermissionConfig;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.povstalec.sgjourney.common.block_entities.ProtectedBlockEntity;
import net.povstalec.sgjourney.common.block_entities.tech.AbstractTransporterEntity;
@Mixin(AbstractTransporterEntity.class)
public abstract class AbstractTransporterEntityMixin implements ProtectedBlockEntity {
    @Unique private boolean isProtected = false;  // your own storage

    @Override
    public void setProtected(boolean value)
    {

        if (this.isProtected == value)
            return;                          // no change → nothing to do
        this.isProtected = value;
        ((AbstractTransporterEntity)(Object)this).setChanged();
        
    }

    @Override
    public boolean isProtected() {
        return this.isProtected;
    }

    @Override
	public boolean hasPermissions(Player player, boolean sendMessage)
	{
		if(isProtected() && !player.hasPermissions(CommonPermissionConfig.protected_transport_rings_permissions.get()))
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

}
