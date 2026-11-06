package com.piremastered.registry;
import com.piremastered.PIRemastered;
import com.piremastered.blockentity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.*;
public final class ModBlockEntities {
 public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES=DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES,PIRemastered.MOD_ID);
 public static final RegistryObject<BlockEntityType<BoilerControllerBlockEntity>> BOILER_CONTROLLER=BLOCK_ENTITIES.register("boiler_controller",()->BlockEntityType.Builder.of(BoilerControllerBlockEntity::new,ModBlocks.INDUSTRIAL_BOILER.get()).build(null));
 public static final RegistryObject<BlockEntityType<BoilerFireboxBlockEntity>> BOILER_FIREBOX=BLOCK_ENTITIES.register("boiler_firebox",()->BlockEntityType.Builder.of(BoilerFireboxBlockEntity::new,ModBlocks.BOILER_FIREBOX.get()).build(null));
 public static final RegistryObject<BlockEntityType<BoilerWaterInputBlockEntity>> BOILER_WATER_INPUT=BLOCK_ENTITIES.register("boiler_water_input",()->BlockEntityType.Builder.of(BoilerWaterInputBlockEntity::new,ModBlocks.BOILER_WATER_INPUT.get()).build(null));
 public static final RegistryObject<BlockEntityType<BoilerSteamOutputBlockEntity>> BOILER_STEAM_OUTPUT=BLOCK_ENTITIES.register("boiler_steam_output",()->BlockEntityType.Builder.of(BoilerSteamOutputBlockEntity::new,ModBlocks.BOILER_STEAM_OUTPUT.get()).build(null));
 private ModBlockEntities(){}
}
