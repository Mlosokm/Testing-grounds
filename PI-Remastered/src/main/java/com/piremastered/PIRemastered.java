package com.piremastered;

import com.piremastered.registry.ModBlocks;
import com.piremastered.registry.ModCreativeTabs;
import com.piremastered.registry.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PIRemastered.MOD_ID)
public final class PIRemastered {
    public static final String MOD_ID = "piremastered";

    public PIRemastered() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModCreativeTabs.TABS.register(modBus);
    }
}
