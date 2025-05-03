package com.cookta2012.sgjourneytweaks.mixin.dhd;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.cookta2012.sgjourneytweaks.mixin.utils.InvokerBlockEntityMixin;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.world.level.Level;
import net.povstalec.sgjourney.common.block_entities.dhd.AbstractDHDEntity;

@Mixin(AbstractDHDEntity.class)
public abstract class AbstractDHDEntityMixin implements InvokerBlockEntityMixin{
	
    @Shadow(remap = false) protected boolean isProtected;
    @Shadow(remap = false) public abstract void invalidateCaps();

    @Inject(
    	    method = "setProtected(Z)V",
    	    at     = @At("HEAD"),
    	    cancellable = true,
    	    remap = false
    )
    public void setProtected(boolean value, CallbackInfo ci)
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
        ci.cancel();
    }
    

    
    
    
}
