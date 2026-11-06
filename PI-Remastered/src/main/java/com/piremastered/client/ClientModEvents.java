package com.piremastered.client;
import com.piremastered.PIRemastered;
import com.piremastered.registry.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
@Mod.EventBusSubscriber(modid=PIRemastered.MOD_ID,bus=Mod.EventBusSubscriber.Bus.MOD,value=Dist.CLIENT)
public final class ClientModEvents {
 private ClientModEvents(){}
 @SubscribeEvent public static void onClientSetup(FMLClientSetupEvent event){
  event.enqueueWork(()->MenuScreens.register(ModMenuTypes.BOILER_MENU.get(),BoilerScreen::new));
 }
}
