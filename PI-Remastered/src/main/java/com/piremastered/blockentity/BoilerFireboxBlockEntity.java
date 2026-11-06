package com.piremastered.blockentity;
import com.piremastered.block.BoilerFireboxBlock;
import com.piremastered.registry.ModBlockEntities;
import com.piremastered.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.*;
import org.jetbrains.annotations.Nullable;

public class BoilerFireboxBlockEntity extends BlockEntity {
 private int burnTime,totalBurnTime;
 private final ItemStackHandler fuel=new ItemStackHandler(1){
  @Override protected void onContentsChanged(int slot){BoilerFireboxBlockEntity.this.setChanged();}
  @Override public boolean isItemValid(int slot,ItemStack stack){
   return ForgeHooks.getBurnTime(stack,null)>0 && (!isHighTier() || stack.is(ModTags.Items.HIGH_TIER_FUELS));
  }
 };
 private LazyOptional<IItemHandler> itemHandler=LazyOptional.empty();
 public BoilerFireboxBlockEntity(BlockPos pos,BlockState state){super(ModBlockEntities.BOILER_FIREBOX.get(),pos,state);}
 private boolean isHighTier(){return getBlockState().getBlock() instanceof BoilerFireboxBlock b && b.isHighTier();}
 public boolean isHighTierBurner(){return isHighTier();}
 @Override public void onLoad(){super.onLoad();itemHandler=LazyOptional.of(()->fuel);}
 @Override public void invalidateCaps(){super.invalidateCaps();itemHandler.invalidate();}
 @Override public <T> LazyOptional<T> getCapability(Capability<T> cap,@Nullable Direction side){
  if(cap==ForgeCapabilities.ITEM_HANDLER)return itemHandler.cast();
  return super.getCapability(cap,side);
 }
 public boolean consumeHeatTick(){if(burnTime<=0&&!startFuel())return false;burnTime--;setChanged();return true;}
 private boolean startFuel(){
  ItemStack stack=fuel.getStackInSlot(0);
  if(isHighTier()&&!stack.is(ModTags.Items.HIGH_TIER_FUELS))return false;
  int duration=ForgeHooks.getBurnTime(stack,null);
  if(duration<=0)return false;
  totalBurnTime=duration;burnTime=duration;
  ItemStack remainder=stack.getCraftingRemainingItem();stack.shrink(1);
  if(stack.isEmpty()&&!remainder.isEmpty())fuel.setStackInSlot(0,remainder);else fuel.setStackInSlot(0,stack);
  return true;
 }
 public int getBurnTime(){return burnTime;}
 public int getTotalBurnTime(){return totalBurnTime;}
 @Override protected void saveAdditional(CompoundTag tag){super.saveAdditional(tag);tag.putInt("BurnTime",burnTime);tag.putInt("TotalBurnTime",totalBurnTime);tag.put("Fuel",fuel.serializeNBT());}
 @Override public void load(CompoundTag tag){super.load(tag);burnTime=tag.getInt("BurnTime");totalBurnTime=tag.getInt("TotalBurnTime");if(tag.contains("Fuel"))fuel.deserializeNBT(tag.getCompound("Fuel"));}
}
