package com.piremastered.registry;
import com.piremastered.PIRemastered;
import com.piremastered.item.IndustrialManualItem;
import java.util.function.Supplier;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.*;
public final class ModItems {
 public static final DeferredRegister<Item> ITEMS=DeferredRegister.create(ForgeRegistries.ITEMS,PIRemastered.MOD_ID);
 public static final RegistryObject<Item> INDUSTRIAL_MANUAL=ITEMS.register("industrial_manual",()->new IndustrialManualItem(new Item.Properties()));
 public static final RegistryObject<Item> INDUSTRIAL_BOILER=blockItem("industrial_boiler",ModBlocks.INDUSTRIAL_BOILER);
 public static final RegistryObject<Item> BOILER_CASING=blockItem("boiler_casing",ModBlocks.BOILER_CASING);
 public static final RegistryObject<Item> BOILER_STRUCTURAL_GLASS=blockItem("boiler_structural_glass",ModBlocks.BOILER_STRUCTURAL_GLASS);
 public static final RegistryObject<Item> BOILER_FIREBOX=blockItem("boiler_firebox",ModBlocks.BOILER_FIREBOX);
 public static final RegistryObject<Item> BOILER_HIGH_TIER_BURNER=blockItem("boiler_high_tier_burner",ModBlocks.BOILER_HIGH_TIER_BURNER);
 public static final RegistryObject<Item> BOILER_SUPERHEATER=blockItem("boiler_superheater",ModBlocks.BOILER_SUPERHEATER);
 public static final RegistryObject<Item> BOILER_WATER_INPUT=blockItem("boiler_water_input",ModBlocks.BOILER_WATER_INPUT);
 public static final RegistryObject<Item> BOILER_STEAM_OUTPUT=blockItem("boiler_steam_output",ModBlocks.BOILER_STEAM_OUTPUT);
 public static final RegistryObject<Item> BOILER_PRESSURE_GAUGE=blockItem("boiler_pressure_gauge",ModBlocks.BOILER_PRESSURE_GAUGE);
 public static final RegistryObject<Item> TURBINE_CASING=blockItem("turbine_casing",ModBlocks.TURBINE_CASING);
 public static final RegistryObject<Item> TURBINE_STRUCTURAL_GLASS=blockItem("turbine_structural_glass",ModBlocks.TURBINE_STRUCTURAL_GLASS);
 public static final RegistryObject<Item> TURBINE_VALVE=blockItem("turbine_valve",ModBlocks.TURBINE_VALVE);
 public static final RegistryObject<Item> TURBINE_ROTOR_SHAFT=blockItem("turbine_rotor_shaft",ModBlocks.TURBINE_ROTOR_SHAFT);
 public static final RegistryObject<Item> TURBINE_ROTATION_COMPLEX=blockItem("turbine_rotation_complex",ModBlocks.TURBINE_ROTATION_COMPLEX);
 public static final RegistryObject<Item> TURBINE_BLADE_COPPER=blockItem("turbine_blade_copper",ModBlocks.TURBINE_BLADE_COPPER);
 public static final RegistryObject<Item> TURBINE_BLADE_GOLD=blockItem("turbine_blade_gold",ModBlocks.TURBINE_BLADE_GOLD);
 public static final RegistryObject<Item> TURBINE_BLADE_IRON=blockItem("turbine_blade_iron",ModBlocks.TURBINE_BLADE_IRON);
 public static final RegistryObject<Item> TURBINE_BLADE_DIAMOND=blockItem("turbine_blade_diamond",ModBlocks.TURBINE_BLADE_DIAMOND);
 public static final RegistryObject<Item> TURBINE_BLADE_NETHERITE=blockItem("turbine_blade_netherite",ModBlocks.TURBINE_BLADE_NETHERITE);
 private static RegistryObject<Item> blockItem(String n,Supplier<? extends Block>b){return ITEMS.register(n,()->new BlockItem(b.get(),new Item.Properties()));}
 private ModItems(){}
}
