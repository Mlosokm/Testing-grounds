package com.piremastered.blockentity;

import com.piremastered.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class BoilerFireboxBlockEntity extends BlockEntity {
    private int burnTime;
    private int totalBurnTime;
    private int activeTemperatureC = 20;

    private final ItemStackHandler fuel = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            BoilerFireboxBlockEntity.this.setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return ForgeHooks.getBurnTime(stack, null) > 0;
        }
    };

    private LazyOptional<IItemHandler> itemHandler = LazyOptional.empty();

    public BoilerFireboxBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BOILER_SOLID_FUEL_BURNER.get(), pos, state);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        itemHandler = LazyOptional.of(() -> fuel);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandler.invalidate();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction side) {
        if (capability == ForgeCapabilities.ITEM_HANDLER) {
            return itemHandler.cast();
        }
        return super.getCapability(capability, side);
    }

    public boolean consumeHeatTick() {
        if (burnTime <= 0 && !startFuel()) {
            return false;
        }
        burnTime--;
        setChanged();
        return true;
    }

    private boolean startFuel() {
        ItemStack stack = fuel.getStackInSlot(0);
        int duration = ForgeHooks.getBurnTime(stack, null);
        if (duration <= 0) {
            return false;
        }

        totalBurnTime = duration;
        burnTime = duration;
        activeTemperatureC = getFuelTemperature(stack);

        ItemStack remainder = stack.getCraftingRemainingItem();
        stack.shrink(1);
        if (stack.isEmpty() && !remainder.isEmpty()) {
            fuel.setStackInSlot(0, remainder);
        } else {
            fuel.setStackInSlot(0, stack);
        }
        setChanged();
        return true;
    }

    private int getFuelTemperature(ItemStack stack) {
        if (stack.is(Items.LAVA_BUCKET)) return 1_100;
        if (stack.is(Items.BLAZE_ROD)) return 950;
        if (stack.is(Items.COAL_BLOCK)) return 850;
        if (stack.is(Items.COAL)) return 750;
        if (stack.is(Items.CHARCOAL)) return 680;
        if (stack.is(Items.DRIED_KELP_BLOCK)) return 430;
        return 520;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public int getTotalBurnTime() {
        return totalBurnTime;
    }

    public int getActiveTemperatureC() {
        return activeTemperatureC;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("BurnTime", burnTime);
        tag.putInt("TotalBurnTime", totalBurnTime);
        tag.putInt("ActiveTemperatureC", activeTemperatureC);
        tag.put("Fuel", fuel.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        burnTime = tag.getInt("BurnTime");
        totalBurnTime = tag.getInt("TotalBurnTime");
        activeTemperatureC = tag.contains("ActiveTemperatureC") ? tag.getInt("ActiveTemperatureC") : 20;
        if (tag.contains("Fuel")) {
            fuel.deserializeNBT(tag.getCompound("Fuel"));
        }
    }
}