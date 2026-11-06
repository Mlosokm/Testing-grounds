package com.piremastered.blockentity;
import com.piremastered.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
public class BoilerWaterInputBlockEntity extends BoilerFluidHatchBlockEntity {
 public BoilerWaterInputBlockEntity(BlockPos pos,BlockState state){super(ModBlockEntities.BOILER_WATER_INPUT.get(),pos,state,fluid->fluid==Fluids.WATER);}
}
