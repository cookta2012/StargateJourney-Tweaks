package com.cookta2012.sgjourneytweaks.mixin.stargate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.povstalec.sgjourney.common.block_entities.stargate.ClassicStargateEntity;
import net.povstalec.sgjourney.common.blocks.ClassicStargateBaseBlock;

@Mixin(ClassicStargateBaseBlock.class)
public abstract class ClassicStargateBaseBlockMixin {
    @Inject(
            method = "use(Lnet/minecraft/world/level/block/state/BlockState;"
                 + "Lnet/minecraft/world/level/Level;"
                 + "Lnet/minecraft/core/BlockPos;"
                 + "Lnet/minecraft/world/entity/player/Player;"
                 + "Lnet/minecraft/world/InteractionHand;"
                 + "Lnet/minecraft/world/phys/BlockHitResult;)"
                 + "Lnet/minecraft/world/InteractionResult;",
            at = @At(
                value = "INVOKE",
                target = "Lnet/povstalec/sgjourney/common/block_entities/stargate/ClassicStargateEntity;addStargateToNetwork()V"
            ),
            remap = false
        )
        private void SGJTweaks$constructedGateDispalayID(
                BlockState state,
                Level level,
                BlockPos pos,
                Player player,
                InteractionHand hand,
                BlockHitResult result,
                CallbackInfoReturnable<InteractionResult> cir
        ) {
            BlockEntity baseEntity = level.getBlockEntity(pos);
            if (baseEntity instanceof ClassicStargateEntity stargate) {
            	stargate.displayID();
            }
        }
}
