package com.piremastered.blockentity;

import com.piremastered.block.BoilerControllerBlock;
import com.piremastered.menu.BoilerMenu;
import com.piremastered.registry.ModBlockEntities;
import com.piremastered.registry.ModBlocks;
import com.piremastered.registry.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BoilerControllerBlockEntity extends BlockEntity implements MenuProvider {
 private boolean formed;
 private boolean running;
 private int progress;
 private int ticks;
 private BlockPos fireboxPos;
 private BlockPos waterHatchPos;
 private BlockPos steamHatchPos;
 private final ContainerData menuData=new ContainerData(){
  @Override public int get(int index){
   return switch(index){
    case 0->formed?(running?2:1):0;
    case 1->getWaterAmount();
    case 2->getSteamAmount();
    case 3->progress;
    case 4->getFirebox()==null?0:getFirebox().getBurnTime();
    case 5->getFirebox()==null?0:getFirebox().getTotalBurnTime();
    case 6->getSteamAmount()*100/BoilerFluidHatchBlockEntity.TANK_CAPACITY;
    default->0;
   };
  }
  @Override public void set(int index,int value){}
  @Override public int getCount(){return 7;}
 };

 public BoilerControllerBlockEntity(BlockPos pos,BlockState state){super(ModBlockEntities.BOILER_CONTROLLER.get(),pos,state);}
 public ContainerData getMenuData(){return menuData;}
 @Override public Component getDisplayName(){return Component.translatable("block.piremastered.industrial_boiler");}
 @Override public AbstractContainerMenu createMenu(int id,Inventory inventory,Player player){return new BoilerMenu(id,inventory,this);}

 public static void serverTick(Level level,BlockPos pos,BlockState state,BoilerControllerBlockEntity boiler){
  if(level.isClientSide)return;
  boiler.ticks++;
  if(boiler.ticks%20==0)boiler.validateStructure();
  boiler.running=false;
  if(boiler.formed){
   BoilerFireboxBlockEntity firebox=boiler.getFirebox();
   BoilerWaterInputBlockEntity input=boiler.getWaterHatch();
   BoilerSteamOutputBlockEntity output=boiler.getSteamHatch();
   if(firebox!=null&&input!=null&&output!=null){
    FluidStack water=input.getTank().drain(1,IFluidHandler.FluidAction.SIMULATE);
    if(!water.isEmpty()){
     FluidStack steam=new FluidStack(ModFluids.STEAM.get(),water.getAmount());
     int accepted=output.getTank().fill(steam,IFluidHandler.FluidAction.SIMULATE);
     int amount=Math.min(water.getAmount(),accepted);
     if(amount>0&&firebox.consumeHeatTick()){
      FluidStack drained=input.getTank().drain(amount,IFluidHandler.FluidAction.EXECUTE);
      if(!drained.isEmpty()){
       output.getTank().fill(new FluidStack(ModFluids.STEAM.get(),drained.getAmount()),IFluidHandler.FluidAction.EXECUTE);
       boiler.progress=(boiler.progress+1)%100;
       boiler.running=true;
      }
     }
    }
   }
  }
  if(boiler.ticks%20==0)boiler.setChanged();
 }

 private void validateStructure(){
  if(level==null){formed=false;return;}
  Direction facing=getBlockState().getValue(BoilerControllerBlock.FACING);
  Direction right=facing.getClockWise();
  BlockPos foundFirebox=null,foundWater=null,foundSteam=null;
  int fireboxes=0,waters=0,steams=0,gauges=0;

  // 3x3x3 shell: controller at front centre, with one air block in the middle.
  for(int x=-1;x<=1;x++){
   for(int y=-1;y<=1;y++){
    for(int z=0;z<=2;z++){
     BlockPos target=worldPosition.relative(right,x).above(y).relative(facing.getOpposite(),z);
     BlockState targetState=level.getBlockState(target);
     if(x==0&&y==0&&z==0){
      if(!targetState.is(ModBlocks.INDUSTRIAL_BOILER.get())){formed=false;return;}
      continue;
     }
     if(x==0&&y==0&&z==1){
      if(!targetState.isAir()){formed=false;return;}
     }else if(targetState.is(ModBlocks.BOILER_CASING.get())){
      // Structural block.
     }else if(targetState.is(ModBlocks.BOILER_FIREBOX.get())&&y==-1){
      fireboxes++;if(foundFirebox==null)foundFirebox=target;
     }else if(targetState.is(ModBlocks.BOILER_WATER_INPUT.get())&&y==1){
      waters++;if(foundWater==null)foundWater=target;
     }else if(targetState.is(ModBlocks.BOILER_STEAM_OUTPUT.get())&&y==1){
      steams++;if(foundSteam==null)foundSteam=target;
     }else if(targetState.is(ModBlocks.BOILER_PRESSURE_GAUGE.get())&&x!=0){
      gauges++;
     }else{
      formed=false;return;
     }
    }
   }
  }

  formed=fireboxes>0&&waters>0&&steams>0&&gauges>0
   &&level.getBlockEntity(foundFirebox) instanceof BoilerFireboxBlockEntity
   &&level.getBlockEntity(foundWater) instanceof BoilerWaterInputBlockEntity
   &&level.getBlockEntity(foundSteam) instanceof BoilerSteamOutputBlockEntity;
  fireboxPos=formed?foundFirebox:null;
  waterHatchPos=formed?foundWater:null;
  steamHatchPos=formed?foundSteam:null;
  if(!formed)progress=0;
 }

 private BoilerFireboxBlockEntity getFirebox(){
  return level!=null&&fireboxPos!=null&&level.getBlockEntity(fireboxPos) instanceof BoilerFireboxBlockEntity be?be:null;
 }
 private BoilerWaterInputBlockEntity getWaterHatch(){
  return level!=null&&waterHatchPos!=null&&level.getBlockEntity(waterHatchPos) instanceof BoilerWaterInputBlockEntity be?be:null;
 }
 private BoilerSteamOutputBlockEntity getSteamHatch(){
  return level!=null&&steamHatchPos!=null&&level.getBlockEntity(steamHatchPos) instanceof BoilerSteamOutputBlockEntity be?be:null;
 }
 private int getWaterAmount(){BoilerWaterInputBlockEntity hatch=getWaterHatch();return hatch==null?0:hatch.getTank().getFluidAmount();}
 private int getSteamAmount(){BoilerSteamOutputBlockEntity hatch=getSteamHatch();return hatch==null?0:hatch.getTank().getFluidAmount();}

 @Override protected void saveAdditional(CompoundTag tag){
  super.saveAdditional(tag);tag.putBoolean("Formed",formed);tag.putInt("Progress",progress);
 }
 @Override public void load(CompoundTag tag){
  super.load(tag);formed=false;progress=tag.getInt("Progress");fireboxPos=null;waterHatchPos=null;steamHatchPos=null;
 }
}
