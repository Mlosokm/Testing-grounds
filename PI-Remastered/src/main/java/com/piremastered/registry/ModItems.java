package com.piremastered.registry;
import com.piremastered.PIRemastered;
import java.util.function.Supplier;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.*;
public final class ModItems {
 public static final DeferredRegister<Item> ITEMS=DeferredRegister.create(ForgeRegistries.ITEMS,PIRemastered.MOD_ID);
 public static final RegistryObject<Item> INDUSTRIAL_BOILER=blockItem("industrial_boiler",ModBlocks.INDUSTRIAL_BOILER);
 public static final RegistryObject<Item> BOILER_CASING=blockItem("boiler_casing",ModBlocks.BOILER_CASING);
 public static final RegistryObject<Item> BOILER_FIREBOX=blockItem("boiler_firebox",ModBlocks.BOILER_FIREBOX);
 public static final RegistryObject<Item> BOILER_WATER_INPUT=blockItem("boiler_water_input",ModBlocks.BOILER_WATER_INPUT);
 public static final RegistryObject<Item> BOILER_STEAM_OUTPUT=blockItem("boiler_steam_output",ModBlocks.BOILER_STEAM_OUTPUT);
 public static final RegistryObject<Item> BOILER_PRESSURE_GAUGE=blockItem("boiler_pressure_gauge",ModBlocks.BOILER_PRESSURE_GAUGE);
 private static RegistryObject<Item> blockItem(String name,Supplier<? extends Block> block){return ITEMS.register(name,()->new BlockItem(block.get(),new Item.Properties()));}
 private ModItems(){}
}
