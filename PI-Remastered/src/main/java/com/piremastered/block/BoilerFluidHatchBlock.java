package com.piremastered.block;
import com.piremastered.blockentity.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
public class BoilerFluidHatchBlock extends BaseEntityBlock {
 private final boolean steamOutput;
 public BoilerFluidHatchBlock(boolean steamOutput,Properties properties){super(properties);this.steamOutput=steamOutput;}
 @Override public BlockEntity newBlockEntity(BlockPos pos,BlockState state){return steamOutput?new BoilerSteamOutputBlockEntity(pos,state):new BoilerWaterInputBlockEntity(pos,state);}
 @Override public RenderShape getRenderShape(BlockState state){return RenderShape.MODEL;}
}
