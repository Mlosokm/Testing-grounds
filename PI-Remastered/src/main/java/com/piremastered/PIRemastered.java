package com.piremastered;
import com.piremastered.registry.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
@Mod(PIRemastered.MOD_ID)
public final class PIRemastered {
 public static final String MOD_ID="piremastered";
 public PIRemastered() {
  IEventBus bus=FMLJavaModLoadingContext.get().getModEventBus();
  ModFluids.FLUID_TYPES.register(bus); ModFluids.FLUIDS.register(bus);
  ModBlocks.BLOCKS.register(bus); ModItems.ITEMS.register(bus);
  ModBlockEntities.BLOCK_ENTITIES.register(bus); ModMenuTypes.MENUS.register(bus);
  ModCreativeTabs.TABS.register(bus);
 }
}
