package com.piremastered.block;
import com.piremastered.blockentity.BoilerControllerBlockEntity;
import com.piremastered.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
public class BoilerControllerBlock extends BaseEntityBlock {
 public static final DirectionProperty FACING=DirectionProperty.create("facing",Direction.Plane.HORIZONTAL);
 public BoilerControllerBlock(Properties properties){super(properties);registerDefaultState(stateDefinition.any().setValue(FACING,Direction.NORTH));}
 @Override public BlockEntity newBlockEntity(BlockPos pos,BlockState state){return new BoilerControllerBlockEntity(pos,state);}
 @Override public RenderShape getRenderShape(BlockState state){return RenderShape.MODEL;}
 @Override public BlockState getStateForPlacement(net.minecraft.world.item.context.BlockPlaceContext context){return defaultBlockState().setValue(FACING,context.getHorizontalDirection().getOpposite());}
 @Override protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block,BlockState> builder){builder.add(FACING);}
 @Override public InteractionResult use(BlockState state,Level level,BlockPos pos,Player player,InteractionHand hand,BlockHitResult hit){
  if(!level.isClientSide&&player instanceof ServerPlayer serverPlayer&&level.getBlockEntity(pos) instanceof BoilerControllerBlockEntity be)NetworkHooks.openScreen(serverPlayer,be,pos);
  return InteractionResult.sidedSuccess(level.isClientSide);
 }
 @Nullable @Override public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level,BlockState state,BlockEntityType<T> type){
  return level.isClientSide?null:createTickerHelper(type,ModBlockEntities.BOILER_CONTROLLER.get(),BoilerControllerBlockEntity::serverTick);
 }
}
