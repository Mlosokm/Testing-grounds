package com.piremastered.registry;

import com.piremastered.PIRemastered;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PIRemastered.MOD_ID);

    public static final RegistryObject<CreativeModeTab> PI_REMASTERED_TAB = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.piremastered.main"))
                    .icon(() -> new ItemStack(ModItems.INDUSTRIAL_BOILER.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.INDUSTRIAL_MANUAL.get());
                        output.accept(ModItems.INDUSTRIAL_BOILER.get());
                        output.accept(ModItems.BOILER_CASING.get());
                        output.accept(ModItems.BOILER_STRUCTURAL_GLASS.get());
                        output.accept(ModItems.BOILER_SOLID_FUEL_BURNER.get());
                        output.accept(ModItems.BOILER_ELECTRIC_SUPERHEATER.get());
                        output.accept(ModItems.BOILER_MULTIPURPOSE_VALVE.get());
                    }).build());

    private ModCreativeTabs() {}
}