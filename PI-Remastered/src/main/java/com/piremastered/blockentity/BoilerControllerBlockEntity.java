package com.piremastered.blockentity;

import com.piremastered.menu.BoilerMenu;
import com.piremastered.registry.ModBlockEntities;
import com.piremastered.registry.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BoilerControllerBlockEntity extends BlockEntity implements MenuProvider {
    private boolean formed;
    private boolean running;
    private int progress;
    private int ticks;
    private int temperatureC = 20;
    private BlockPos burnerPos;
    private BlockPos waterValvePos;
    private BlockPos steamValvePos;

    private final ContainerData menuData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> formed ? (running ? 2 : 1) : 0;
                case 1 -> getWaterAmount();
                case 2 -> getSteamAmount();
                case 3 -> progress;
                case 4 -> getBurner() == null ? 0 : getBurner().getBurnTime();
                case 5 -> getBurner() == null ? 0 : getBurner().getTotalBurnTime();
                case 6 -> temperatureC;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {}

        @Override
        public int getCount() {
            return 7;
        }
    };

    public BoilerControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BOILER_CONTROLLER.get(), pos, state);
    }

    public ContainerData getMenuData() {
        return menuData;
    }

    public boolean isFormed() {
        return formed;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.piremastered.industrial_boiler");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BoilerMenu(id, inventory, this);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, BoilerControllerBlockEntity boiler) {
        if (level.isClientSide) {
            return;
        }

        boiler.ticks++;
        if (boiler.ticks % 20 == 0) {
            boiler.validateStructure();
        }

        boiler.running = false;
        BoilerFireboxBlockEntity burner = boiler.getBurner();
        boolean hasHeat = boiler.formed && burner != null && burner.consumeHeatTick();
        int targetTemperature = hasHeat ? burner.getActiveTemperatureC() : 20;
        boiler.temperatureC = approachTemperature(boiler.temperatureC, targetTemperature);

        if (boiler.formed && hasHeat && boiler.temperatureC >= 100) {
            BoilerMultipurposeValveBlockEntity input = boiler.getWaterValve();
            BoilerMultipurposeValveBlockEntity output = boiler.getSteamValve();

            if (input != null && output != null) {
                FluidStack storedWater = input.getTank().getFluid();
                if (!storedWater.isEmpty() && storedWater.getFluid() == Fluids.WATER) {
                    int requested = 1 + Math.min(4, (boiler.temperatureC - 100) / 250);
                    FluidStack simulatedWater = input.getTank().drain(requested, IFluidHandler.FluidAction.SIMULATE);

                    if (!simulatedWater.isEmpty()) {
                        FluidStack simulatedSteam = new FluidStack(ModFluids.STEAM.get(),
                                simulatedWater.getAmount());
                        int accepted = output.getTank().fill(simulatedSteam, IFluidHandler.FluidAction.SIMULATE);
                        int amount = Math.min(simulatedWater.getAmount(), accepted);

                        if (amount > 0) {
                            FluidStack drained = input.getTank().drain(amount, IFluidHandler.FluidAction.EXECUTE);
                            if (!drained.isEmpty()) {
                                int produced = output.getTank().fill(new FluidStack(ModFluids.STEAM.get(),
                                        drained.getAmount()), IFluidHandler.FluidAction.EXECUTE);
                                if (produced > 0) {
                                    output.setSteamTemperatureC(boiler.temperatureC);
                                    boiler.progress = (boiler.progress + 1) % 100;
                                    boiler.running = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (boiler.ticks % 20 == 0) {
            boiler.setChanged();
        }
    }

    private static int approachTemperature(int current, int target) {
        if (current < target) {
            int rise = Math.max(1, Math.min(8, (target - current) / 40));
            return Math.min(target, current + rise);
        }
        if (current > target) {
            int fall = Math.max(1, Math.min(3, (current - target) / 80));
            return Math.max(target, current - fall);
        }
        return current;
    }

    private void validateStructure() {
        if (level == null) {
            formed = false;
            return;
        }

        BoilerStructureValidator.Result result = BoilerStructureValidator.validate(level, worldPosition);
        formed = result.valid();
        burnerPos = formed ? result.solidFuelBurner() : null;
        waterValvePos = formed ? result.waterValve() : null;
        steamValvePos = formed ? result.steamValve() : null;

        if (!formed) {
            progress = 0;
            running = false;
        }
    }

    private BoilerFireboxBlockEntity getBurner() {
        return level != null && burnerPos != null
                && level.getBlockEntity(burnerPos) instanceof BoilerFireboxBlockEntity burner
                ? burner : null;
    }

    private BoilerMultipurposeValveBlockEntity getWaterValve() {
        return level != null && waterValvePos != null
                && level.getBlockEntity(waterValvePos) instanceof BoilerMultipurposeValveBlockEntity valve
                ? valve : null;
    }

    private BoilerMultipurposeValveBlockEntity getSteamValve() {
        return level != null && steamValvePos != null
                && level.getBlockEntity(steamValvePos) instanceof BoilerMultipurposeValveBlockEntity valve
                ? valve : null;
    }

    private int getWaterAmount() {
        BoilerMultipurposeValveBlockEntity valve = getWaterValve();
        if (valve == null || valve.getTank().getFluid().getFluid() != Fluids.WATER) {
            return 0;
        }
        return valve.getTank().getFluidAmount();
    }

    private int getSteamAmount() {
        BoilerMultipurposeValveBlockEntity valve = getSteamValve();
        if (valve == null || valve.getTank().getFluid().getFluid() != ModFluids.STEAM.get()) {
            return 0;
        }
        return valve.getTank().getFluidAmount();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("Formed", formed);
        tag.putInt("Progress", progress);
        tag.putInt("TemperatureC", temperatureC);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        formed = false;
        progress = tag.getInt("Progress");
        temperatureC = tag.contains("TemperatureC") ? tag.getInt("TemperatureC") : 20;
        burnerPos = null;
        waterValvePos = null;
        steamValvePos = null;
    }
}