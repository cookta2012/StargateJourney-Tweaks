package com.cookta2012.sgjourneytweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.povstalec.sgjourney.common.block_entities.ProtectedBlockEntity;
import net.povstalec.sgjourney.common.blocks.ProtectedBlock;
import net.povstalec.sgjourney.common.blocks.RingPanelBlock;

@Mixin(RingPanelBlock.class)
public abstract class RingPanelBlockMixin implements ProtectedBlock{
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
}
