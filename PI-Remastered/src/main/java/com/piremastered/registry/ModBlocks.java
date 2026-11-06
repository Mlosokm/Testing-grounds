package com.piremastered.registry;
import com.piremastered.PIRemastered;
import com.piremastered.block.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.*;
public final class ModBlocks {
 public static final DeferredRegister<Block> BLOCKS=DeferredRegister.create(ForgeRegistries.BLOCKS,PIRemastered.MOD_ID);
 public static final RegistryObject<Block> INDUSTRIAL_BOILER=BLOCKS.register("industrial_boiler",()->new BoilerControllerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(4,8).sound(SoundType.METAL).requiresCorrectToolForDrops()));
 public static final RegistryObject<Block> BOILER_CASING=metal("boiler_casing",MapColor.COLOR_GRAY,3.5F);
 public static final RegistryObject<Block> BOILER_STRUCTURAL_GLASS=BLOCKS.register("boiler_structural_glass",()->new GlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(3,6).sound(SoundType.GLASS).noOcclusion()));
 public static final RegistryObject<Block> BOILER_FIREBOX=BLOCKS.register("boiler_firebox",()->new BoilerFireboxBlock(false,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(3.5F,7).sound(SoundType.METAL).requiresCorrectToolForDrops().lightLevel(s->5)));
 public static final RegistryObject<Block> BOILER_HIGH_TIER_BURNER=BLOCKS.register("boiler_high_tier_burner",()->new BoilerFireboxBlock(true,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(4,8).sound(SoundType.METAL).requiresCorrectToolForDrops().lightLevel(s->9)));
 public static final RegistryObject<Block> BOILER_SUPERHEATER=BLOCKS.register("boiler_superheater",()->new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(4,8).sound(SoundType.METAL).requiresCorrectToolForDrops().lightLevel(s->7)));
 public static final RegistryObject<Block> BOILER_WATER_INPUT=BLOCKS.register("boiler_water_input",()->new BoilerFluidHatchBlock(false,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(3,6).sound(SoundType.METAL).requiresCorrectToolForDrops()));
 public static final RegistryObject<Block> BOILER_STEAM_OUTPUT=BLOCKS.register("boiler_steam_output",()->new BoilerFluidHatchBlock(true,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(3,6).sound(SoundType.METAL).requiresCorrectToolForDrops()));
 public static final RegistryObject<Block> BOILER_PRESSURE_GAUGE=metal("boiler_pressure_gauge",MapColor.COLOR_ORANGE,2.5F);
 private static RegistryObject<Block> metal(String name,MapColor color,float hardness){return BLOCKS.register(name,()->new Block(BlockBehaviour.Properties.of().mapColor(color).strength(hardness,hardness*2).sound(SoundType.METAL).requiresCorrectToolForDrops()));}
 private ModBlocks(){}
}
