package com.cookta2012.sgjourneytweaks.mixin.stargate;

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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClassicStargateBaseBlock.class)
public abstract class ClassicStargateBaseBlockMixin {

    @Inject(
        method = "use",                            
        at = @At(
            value = "INVOKE",
            target =
                "Lnet/povstalec/sgjourney/common/block_entities/stargate/" +
                "ClassicStargateEntity;addStargateToNetwork()V"
        )
    )
    private void displayConstructedGateId(
            BlockState state, Level level, BlockPos pos, Player player,
            InteractionHand hand, BlockHitResult hit,
            CallbackInfoReturnable<InteractionResult> cir) {

        if (level.isClientSide()) return;          // server‑side only

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof ClassicStargateEntity stargate) {
            stargate.displayID();
        }
    }
}
