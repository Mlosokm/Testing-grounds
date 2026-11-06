package com.piremastered.registry;

import com.piremastered.PIRemastered;
import com.piremastered.blockentity.BoilerControllerBlockEntity;
import com.piremastered.blockentity.BoilerFireboxBlockEntity;
import com.piremastered.blockentity.BoilerMultipurposeValveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PIRemastered.MOD_ID);

    public static final RegistryObject<BlockEntityType<BoilerControllerBlockEntity>> BOILER_CONTROLLER =
            BLOCK_ENTITIES.register("boiler_controller", () ->
                    BlockEntityType.Builder.of(BoilerControllerBlockEntity::new,
                            ModBlocks.INDUSTRIAL_BOILER.get()).build(null));

    public static final RegistryObject<BlockEntityType<BoilerFireboxBlockEntity>> BOILER_SOLID_FUEL_BURNER =
            BLOCK_ENTITIES.register("boiler_solid_fuel_burner", () ->
                    BlockEntityType.Builder.of(BoilerFireboxBlockEntity::new,
                            ModBlocks.BOILER_SOLID_FUEL_BURNER.get()).build(null));

    public static final RegistryObject<BlockEntityType<BoilerMultipurposeValveBlockEntity>> BOILER_MULTIPURPOSE_VALVE =
            BLOCK_ENTITIES.register("boiler_multipurpose_valve", () ->
                    BlockEntityType.Builder.of(BoilerMultipurposeValveBlockEntity::new,
                            ModBlocks.BOILER_MULTIPURPOSE_VALVE.get()).build(null));

    private ModBlockEntities() {}
}