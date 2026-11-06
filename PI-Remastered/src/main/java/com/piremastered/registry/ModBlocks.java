package com.piremastered.registry;

import com.piremastered.PIRemastered;
import com.piremastered.block.BoilerControllerBlock;
import com.piremastered.block.BoilerFireboxBlock;
import com.piremastered.block.BoilerMultipurposeValveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, PIRemastered.MOD_ID);

    public static final RegistryObject<Block> INDUSTRIAL_BOILER = BLOCKS.register(
            "industrial_boiler",
            () -> new BoilerControllerBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL).strength(4, 8).sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BOILER_CASING =
            metal("boiler_casing", MapColor.COLOR_GRAY, 3.5F);

    public static final RegistryObject<Block> BOILER_STRUCTURAL_GLASS = BLOCKS.register(
            "boiler_structural_glass",
            () -> new GlassBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_BLUE).strength(3, 6)
                    .sound(SoundType.GLASS).noOcclusion()));

    public static final RegistryObject<Block> BOILER_SOLID_FUEL_BURNER = BLOCKS.register(
            "boiler_solid_fuel_burner",
            () -> new BoilerFireboxBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).strength(3.5F, 7).sound(SoundType.METAL)
                    .requiresCorrectToolForDrops().lightLevel(state -> 5)));

    public static final RegistryObject<Block> BOILER_MULTIPURPOSE_VALVE = BLOCKS.register(
            "boiler_multipurpose_valve",
            () -> new BoilerMultipurposeValveBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY).strength(3.5F, 7).sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));

    private static RegistryObject<Block> metal(String name, MapColor color, float hardness) {
        return BLOCKS.register(name, () -> new Block(BlockBehaviour.Properties.of()
                .mapColor(color).strength(hardness, hardness * 2).sound(SoundType.METAL)
                .requiresCorrectToolForDrops()));
    }

    private ModBlocks() {}
}