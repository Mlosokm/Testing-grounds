package com.piremastered.registry;
import com.piremastered.PIRemastered;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.*;
import net.minecraftforge.registries.*;
public final class ModFluids {
 public static final DeferredRegister<FluidType> FLUID_TYPES=DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES,PIRemastered.MOD_ID);
 public static final DeferredRegister<Fluid> FLUIDS=DeferredRegister.create(ForgeRegistries.FLUIDS,PIRemastered.MOD_ID);
 public static final RegistryObject<FluidType> STEAM_TYPE=FLUID_TYPES.register("steam",()->new FluidType(FluidType.Properties.create().density(-100).viscosity(100).temperature(373)));
 public static final RegistryObject<Fluid> STEAM=FLUIDS.register("steam",()->new ForgeFlowingFluid.Source(STEAM_PROPERTIES));
 public static final RegistryObject<Fluid> FLOWING_STEAM=FLUIDS.register("flowing_steam",()->new ForgeFlowingFluid.Flowing(STEAM_PROPERTIES));
 private static final ForgeFlowingFluid.Properties STEAM_PROPERTIES=new ForgeFlowingFluid.Properties(STEAM_TYPE,STEAM,FLOWING_STEAM);
 private ModFluids(){}
}
