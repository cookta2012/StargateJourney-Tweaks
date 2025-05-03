package com.cookta2012.sgjourneytweaks.utils;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.povstalec.sgjourney.common.block_entities.ProtectedBlockEntity;

public final class GatedItemHandler<T extends ProtectedBlockEntity> implements IItemHandler {
    private final T inventory;
    private final IItemHandler     backend;

    public GatedItemHandler(T inventory, IItemHandler backend) {
        this.inventory     = inventory;
        this.backend = backend;
    }

    /* — helpers — */
    private boolean closed() { return inventory.isProtected(); }

    /* — IItemHandler — */
    @Override public int getSlots() {
        return backend.getSlots();                   // slot count never changes
    }

    @Override public @NotNull ItemStack getStackInSlot(int slot) {
        return closed() ? ItemStack.EMPTY
                        : backend.getStackInSlot(slot);
    }

    @Override public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return closed() ? stack                      // reject everything
                        : backend.insertItem(slot, stack, simulate);
    }

    @Override public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return closed() ? ItemStack.EMPTY
                        : backend.extractItem(slot, amount, simulate);
    }

    @Override public int getSlotLimit(int slot) {
        return backend.getSlotLimit(slot);           // safe to expose
    }

    @Override public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return !closed() && backend.isItemValid(slot, stack);
    }


}