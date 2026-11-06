package com.piremastered.registry;
import com.piremastered.PIRemastered;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.*;
import net.minecraftforge.registries.*;
public final class ModFluids {
 public static final DeferredRegister<FluidType> FLUID_TYPES=DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES,PIRemastered.MOD_ID);
 public static final DeferredRegister<Fluid> FLUIDS=DeferredRegister.create(ForgeRegistries.FLUIDS,PIRemastered.MOD_ID);
 public static final RegistryObject<FluidType> STEAM_TYPE=FLUID_TYPES.register("steam",()->new FluidType(FluidType.Properties.create().density(-100).viscosity(100).temperature(373)));
 public static final RegistryObject<Fluid> STEAM=FLUIDS.register("steam",()->new ForgeFlowingFluid.Source(steamProperties()));
 public static final RegistryObject<Fluid> FLOWING_STEAM=FLUIDS.register("flowing_steam",()->new ForgeFlowingFluid.Flowing(steamProperties()));
 public static final RegistryObject<FluidType> SUPERHEATED_STEAM_TYPE=FLUID_TYPES.register("superheated_steam",()->new FluidType(FluidType.Properties.create().density(-100).viscosity(100).temperature(673)));
 public static final RegistryObject<Fluid> SUPERHEATED_STEAM=FLUIDS.register("superheated_steam",()->new ForgeFlowingFluid.Source(superheatedProperties()));
 public static final RegistryObject<Fluid> FLOWING_SUPERHEATED_STEAM=FLUIDS.register("flowing_superheated_steam",()->new ForgeFlowingFluid.Flowing(superheatedProperties()));
 private static ForgeFlowingFluid.Properties steamProperties(){return new ForgeFlowingFluid.Properties(STEAM_TYPE,STEAM,FLOWING_STEAM);}
 private static ForgeFlowingFluid.Properties superheatedProperties(){return new ForgeFlowingFluid.Properties(SUPERHEATED_STEAM_TYPE,SUPERHEATED_STEAM,FLOWING_SUPERHEATED_STEAM);}
 private ModFluids(){}
}
