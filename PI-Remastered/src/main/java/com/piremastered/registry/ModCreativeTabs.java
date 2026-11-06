package com.piremastered.registry;
import com.piremastered.PIRemastered;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.*;
public final class ModCreativeTabs {
 public static final DeferredRegister<CreativeModeTab> TABS=DeferredRegister.create(Registries.CREATIVE_MODE_TAB,PIRemastered.MOD_ID);
 public static final RegistryObject<CreativeModeTab> PI_REMASTERED_TAB=TABS.register("main",()->CreativeModeTab.builder()
  .title(Component.translatable("itemGroup.piremastered.main"))
  .icon(()->new ItemStack(ModItems.INDUSTRIAL_BOILER.get()))
  .displayItems((parameters,output)->{
   output.accept(ModItems.INDUSTRIAL_MANUAL.get());output.accept(ModItems.INDUSTRIAL_BOILER.get());
   output.accept(ModItems.BOILER_CASING.get());output.accept(ModItems.BOILER_STRUCTURAL_GLASS.get());output.accept(ModItems.BOILER_FIREBOX.get());
   output.accept(ModItems.BOILER_HIGH_TIER_BURNER.get());output.accept(ModItems.BOILER_SUPERHEATER.get());output.accept(ModItems.BOILER_WATER_INPUT.get());
   output.accept(ModItems.BOILER_STEAM_OUTPUT.get());output.accept(ModItems.BOILER_PRESSURE_GAUGE.get());
   output.accept(ModItems.TURBINE_CASING.get());output.accept(ModItems.TURBINE_STRUCTURAL_GLASS.get());output.accept(ModItems.TURBINE_VALVE.get());
   output.accept(ModItems.TURBINE_ROTOR_SHAFT.get());output.accept(ModItems.TURBINE_ROTATION_COMPLEX.get());
   output.accept(ModItems.TURBINE_BLADE_COPPER.get());output.accept(ModItems.TURBINE_BLADE_GOLD.get());output.accept(ModItems.TURBINE_BLADE_IRON.get());
   output.accept(ModItems.TURBINE_BLADE_DIAMOND.get());output.accept(ModItems.TURBINE_BLADE_NETHERITE.get());
  }).build());
 private ModCreativeTabs(){}
}
