package com.cookta2012.sgjourneytweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.povstalec.sgjourney.common.block_entities.ProtectedBlockEntity;
import net.povstalec.sgjourney.common.blocks.ProtectedBlock;
import net.povstalec.sgjourney.common.blocks.tech.ZPMHubBlock;

@Mixin(ZPMHubBlock.class)
public abstract class ZPMHubBlockMixin implements ProtectedBlock {

    @Override
    public ProtectedBlockEntity getProtectedBlockEntity(BlockGetter level,
                                                        BlockPos pos,
                                                        BlockState state) {
        BlockEntity be = level.getBlockEntity(pos);
        return be instanceof ProtectedBlockEntity pbe ? pbe : null;
    }

    @Override
    public boolean hasPermissions(BlockGetter level,
                                  BlockPos pos,
                                  BlockState state,
                                  Player player,
                                  boolean sendMessage) {
        ProtectedBlockEntity pbe = getProtectedBlockEntity(level, pos, state);
        return pbe.hasPermissions(player, sendMessage);
    }
    
    // ====== NEW: catch use(...) when holding an item too ======
    @Inject(
      method = "use",
      at = @At("HEAD"),
      cancellable = true
    )
    private void beforeUseWithItem(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        BlockHitResult hit,
        CallbackInfoReturnable<InteractionResult> cir
    ) {
        if (!level.isClientSide()) {
            var be = level.getBlockEntity(pos);
            if (be instanceof ProtectedBlockEntity pbe) {
                if (!pbe.hasPermissions(player, /*sendMessage=*/ true)) {
                    cir.setReturnValue(InteractionResult.FAIL);
                }
            }
        }
    }
}
