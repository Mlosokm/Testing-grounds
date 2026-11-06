package com.piremastered.registry;
import com.piremastered.PIRemastered;
import com.piremastered.menu.BoilerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.*;
public final class ModMenuTypes {
 public static final DeferredRegister<MenuType<?>> MENUS=DeferredRegister.create(ForgeRegistries.MENU_TYPES,PIRemastered.MOD_ID);
 public static final RegistryObject<MenuType<BoilerMenu>> BOILER_MENU=MENUS.register("boiler",()->IForgeMenuType.create(BoilerMenu::new));
 private ModMenuTypes(){}
}
