package com.piremastered.blockentity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;
import java.util.function.Predicate;
import net.minecraft.world.level.material.Fluid;
public abstract class BoilerFluidHatchBlockEntity extends BlockEntity {
 public static final int TANK_CAPACITY=16000;
 protected final FluidTank tank;
 private LazyOptional<IFluidHandler> fluidHandler=LazyOptional.empty();
 protected BoilerFluidHatchBlockEntity(BlockEntityType<?> type,BlockPos pos,BlockState state,Predicate<Fluid> filter){
  super(type,pos,state);
  tank=new FluidTank(TANK_CAPACITY,stack->filter.test(stack.getFluid())){
   @Override protected void onContentsChanged(){BoilerFluidHatchBlockEntity.this.setChanged();}
  };
 }
 public FluidTank getTank(){return tank;}
 @Override public void onLoad(){super.onLoad();fluidHandler=LazyOptional.of(()->tank);}
 @Override public void invalidateCaps(){super.invalidateCaps();fluidHandler.invalidate();}
 @Override public <T> LazyOptional<T> getCapability(Capability<T> cap,@Nullable Direction side){
  if(cap==ForgeCapabilities.FLUID_HANDLER)return fluidHandler.cast();
  return super.getCapability(cap,side);
 }
 @Override protected void saveAdditional(CompoundTag tag){super.saveAdditional(tag);tag.put("Tank",tank.writeToNBT(new CompoundTag()));}
 @Override public void load(CompoundTag tag){super.load(tag);if(tag.contains("Tank"))tank.readFromNBT(tag.getCompound("Tank"));}
}
