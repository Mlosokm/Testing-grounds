package com.piremastered.registry;
import com.piremastered.PIRemastered;
import com.piremastered.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.*;
public final class ModBlocks {
 public static final DeferredRegister<Block> BLOCKS=DeferredRegister.create(ForgeRegistries.BLOCKS,PIRemastered.MOD_ID);
 public static final RegistryObject<Block> INDUSTRIAL_BOILER=BLOCKS.register("industrial_boiler",()->new BoilerControllerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(4,8).sound(SoundType.METAL).requiresCorrectToolForDrops()));
 public static final RegistryObject<Block> BOILER_CASING=BLOCKS.register("boiler_casing",()->new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(3.5F,7).sound(SoundType.METAL).requiresCorrectToolForDrops()));
 public static final RegistryObject<Block> BOILER_FIREBOX=BLOCKS.register("boiler_firebox",()->new BoilerFireboxBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(3.5F,7).sound(SoundType.METAL).requiresCorrectToolForDrops().lightLevel(s->5)));
 public static final RegistryObject<Block> BOILER_WATER_INPUT=BLOCKS.register("boiler_water_input",()->new BoilerFluidHatchBlock(false,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(3,6).sound(SoundType.METAL).requiresCorrectToolForDrops()));
 public static final RegistryObject<Block> BOILER_STEAM_OUTPUT=BLOCKS.register("boiler_steam_output",()->new BoilerFluidHatchBlock(true,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(3,6).sound(SoundType.METAL).requiresCorrectToolForDrops()));
 public static final RegistryObject<Block> BOILER_PRESSURE_GAUGE=BLOCKS.register("boiler_pressure_gauge",()->new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(2.5F,5).sound(SoundType.METAL)));
 private ModBlocks(){}
}
