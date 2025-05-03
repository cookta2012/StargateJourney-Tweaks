package com.cookta2012.sgjourneytweaks.mixin.utils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;


@Mixin(BlockEntity.class)
public interface InvokerBlockEntityMixin {
	
  @Invoker("setChanged")
  public abstract void setChanged();
  @Invoker("getLevel")
  public abstract Level getLevel();
  @Invoker("getBlockPos")
  public abstract BlockPos getBlockPos();
  @Invoker("getBlockState")
  public abstract BlockState getBlockState();
}
