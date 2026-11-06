package com.piremastered.registry;

import com.piremastered.PIRemastered;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, PIRemastered.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, PIRemastered.MOD_ID);

    public static final RegistryObject<FluidType> STEAM_TYPE = FLUID_TYPES.register("steam",
            () -> new FluidType(FluidType.Properties.create()
                    .density(-100).viscosity(100).temperature(373)));

    public static final RegistryObject<Fluid> STEAM = FLUIDS.register("steam",
            () -> new ForgeFlowingFluid.Source(steamProperties()));
    public static final RegistryObject<Fluid> FLOWING_STEAM = FLUIDS.register("flowing_steam",
            () -> new ForgeFlowingFluid.Flowing(steamProperties()));

    private static ForgeFlowingFluid.Properties steamProperties() {
        return new ForgeFlowingFluid.Properties(STEAM_TYPE, STEAM, FLOWING_STEAM);
    }

    private ModFluids() {}
}