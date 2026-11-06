package com.piremastered.blockentity;

import com.piremastered.registry.ModBlockEntities;
import com.piremastered.registry.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

public class BoilerMultipurposeValveBlockEntity extends BlockEntity {
    public static final int TANK_CAPACITY = 16_000;
    private int steamTemperatureC = 100;

    private final FluidTank tank = new FluidTank(TANK_CAPACITY, BoilerMultipurposeValveBlockEntity::isSupportedFluid) {
        @Override
        protected void onContentsChanged() {
            BoilerMultipurposeValveBlockEntity.this.setChanged();
        }
    };

    private final IFluidHandler pipeHandler = new IFluidHandler() {
        @Override
        public int getTanks() {
            return tank.getTanks();
        }

        @Override
        public FluidStack getFluidInTank(int tankIndex) {
            return tank.getFluidInTank(tankIndex);
        }

        @Override
        public int getTankCapacity(int tankIndex) {
            return tank.getTankCapacity(tankIndex);
        }

        @Override
        public boolean isFluidValid(int tankIndex, FluidStack stack) {
            return tank.isFluidValid(tankIndex, stack);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            FluidStack normalized = resource.copy();
            int incomingTemperature = readTemperature(resource);
            normalized.setTag(null);
            int accepted = tank.fill(normalized, action);
            if (accepted > 0 && action.execute() && resource.getFluid() == ModFluids.STEAM.get()) {
                steamTemperatureC = incomingTemperature;
                setChanged();
            }
            return accepted;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            FluidStack normalized = resource.copy();
            normalized.setTag(null);
            return addTemperature(tank.drain(normalized, action));
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            return addTemperature(tank.drain(maxDrain, action));
        }
    };

    private LazyOptional<IFluidHandler> fluidHandler = LazyOptional.empty();

    public BoilerMultipurposeValveBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BOILER_MULTIPURPOSE_VALVE.get(), pos, state);
    }

    private static boolean isSupportedFluid(FluidStack stack) {
        return stack.getFluid() == Fluids.WATER || stack.getFluid() == ModFluids.STEAM.get();
    }

    private static int readTemperature(FluidStack stack) {
        if (stack.hasTag() && stack.getTag().contains("PITemperatureC")) {
            return Math.max(20, Math.min(2_000, stack.getTag().getInt("PITemperatureC")));
        }
        return 100;
    }

    private FluidStack addTemperature(FluidStack stack) {
        if (!stack.isEmpty() && stack.getFluid() == ModFluids.STEAM.get()) {
            stack.getOrCreateTag().putInt("PITemperatureC", steamTemperatureC);
        }
        return stack;
    }

    public FluidTank getTank() {
        return tank;
    }

    public void setSteamTemperatureC(int temperatureC) {
        steamTemperatureC = Math.max(20, Math.min(2_000, temperatureC));
        setChanged();
    }

    public int getSteamTemperatureC() {
        return steamTemperatureC;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        fluidHandler = LazyOptional.of(() -> pipeHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        fluidHandler.invalidate();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction side) {
        if (capability == ForgeCapabilities.FLUID_HANDLER) {
            return fluidHandler.cast();
        }
        return super.getCapability(capability, side);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Tank", tank.writeToNBT(new CompoundTag()));
        tag.putInt("SteamTemperatureC", steamTemperatureC);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Tank")) {
            tank.readFromNBT(tag.getCompound("Tank"));
        }
        steamTemperatureC = tag.contains("SteamTemperatureC") ? tag.getInt("SteamTemperatureC") : 100;
    }
}