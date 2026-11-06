package com.piremastered.block;
import com.piremastered.blockentity.BoilerFireboxBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BoilerFireboxBlock extends BaseEntityBlock {
 private final boolean highTier;
 public BoilerFireboxBlock(boolean highTier,Properties properties){super(properties);this.highTier=highTier;}
 public boolean isHighTier(){return highTier;}
 @Override public BlockEntity newBlockEntity(BlockPos pos,BlockState state){return new BoilerFireboxBlockEntity(pos,state);}
 @Override public RenderShape getRenderShape(BlockState state){return RenderShape.MODEL;}
 @Nullable @Override public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level,BlockState state,BlockEntityType<T> type){return null;}
}
