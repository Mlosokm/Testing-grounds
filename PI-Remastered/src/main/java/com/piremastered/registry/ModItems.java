package com.piremastered.registry;

import com.piremastered.PIRemastered;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PIRemastered.MOD_ID);

    public static final RegistryObject<Item> INDUSTRIAL_BOILER = ITEMS.register(
            "industrial_boiler",
            () -> new BlockItem(ModBlocks.INDUSTRIAL_BOILER.get(), new Item.Properties()));

    public static final RegistryObject<Item> BOILER_CASING = ITEMS.register(
            "boiler_casing",
            () -> new BlockItem(ModBlocks.BOILER_CASING.get(), new Item.Properties()));

    private ModItems() {
    }
}
