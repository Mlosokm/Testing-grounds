package com.piremastered.registry;

import com.piremastered.PIRemastered;
import com.piremastered.item.IndustrialManualItem;
import java.util.function.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PIRemastered.MOD_ID);

    public static final RegistryObject<Item> INDUSTRIAL_MANUAL =
            ITEMS.register("industrial_manual", () -> new IndustrialManualItem(new Item.Properties()));

    public static final RegistryObject<Item> INDUSTRIAL_BOILER =
            blockItem("industrial_boiler", ModBlocks.INDUSTRIAL_BOILER);
    public static final RegistryObject<Item> BOILER_CASING =
            blockItem("boiler_casing", ModBlocks.BOILER_CASING);
    public static final RegistryObject<Item> BOILER_STRUCTURAL_GLASS =
            blockItem("boiler_structural_glass", ModBlocks.BOILER_STRUCTURAL_GLASS);
    public static final RegistryObject<Item> BOILER_SOLID_FUEL_BURNER =
            blockItem("boiler_solid_fuel_burner", ModBlocks.BOILER_SOLID_FUEL_BURNER);
    public static final RegistryObject<Item> BOILER_ELECTRIC_SUPERHEATER =
            blockItem("boiler_electric_superheater", ModBlocks.BOILER_ELECTRIC_SUPERHEATER);
    public static final RegistryObject<Item> BOILER_MULTIPURPOSE_VALVE =
            blockItem("boiler_multipurpose_valve", ModBlocks.BOILER_MULTIPURPOSE_VALVE);

    private static RegistryObject<Item> blockItem(String name, Supplier<? extends Block> block) {
        return ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private ModItems() {}
}