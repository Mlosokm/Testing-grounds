package com.piremastered.blockentity;
import com.piremastered.menu.BoilerMenu;
import com.piremastered.registry.ModBlockEntities;
import com.piremastered.registry.ModFluids;
import net.minecraft.core.BlockPos;
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
 private boolean formed,running,superheated;
 private int progress,ticks;
 private BlockPos fireboxPos,waterHatchPos,steamHatchPos;
 private final ContainerData menuData=new ContainerData(){
  @Override public int get(int i){return switch(i){
   case 0->formed?(running?2:1):0;case 1->getWaterAmount();case 2->getSteamAmount();
   case 3->progress;case 4->getFirebox()==null?0:getFirebox().getBurnTime();
   case 5->getFirebox()==null?0:getFirebox().getTotalBurnTime();case 6->superheated?1:0;default->0;};}
  @Override public void set(int i,int v){}
  @Override public int getCount(){return 7;}
 };
 public BoilerControllerBlockEntity(BlockPos pos,BlockState state){super(ModBlockEntities.BOILER_CONTROLLER.get(),pos,state);}
 public ContainerData getMenuData(){return menuData;}
 public boolean isFormed(){return formed;}
 @Override public Component getDisplayName(){return Component.translatable("block.piremastered.industrial_boiler");}
 @Override public AbstractContainerMenu createMenu(int id,Inventory inventory,Player player){return new BoilerMenu(id,inventory,this);}
 public static void serverTick(Level level,BlockPos pos,BlockState state,BoilerControllerBlockEntity b){
  if(level.isClientSide)return;
  b.ticks++;if(b.ticks%20==0)b.validateStructure();
  b.running=false;b.superheated=false;
  if(b.formed){
   BoilerFireboxBlockEntity fire=b.getFirebox();BoilerWaterInputBlockEntity input=b.getWaterHatch();BoilerSteamOutputBlockEntity output=b.getSteamHatch();
   if(fire!=null&&input!=null&&output!=null){
    FluidStack water=input.getTank().drain(1,IFluidHandler.FluidAction.SIMULATE);
    if(!water.isEmpty()){
     boolean hot=fire.isHighTierBurner();net.minecraft.world.level.material.Fluid fluid=hot?ModFluids.SUPERHEATED_STEAM.get():ModFluids.STEAM.get();
     int accepted=output.getTank().fill(new FluidStack(fluid,water.getAmount()),IFluidHandler.FluidAction.SIMULATE);
     int amount=Math.min(water.getAmount(),accepted);
     if(amount>0&&fire.consumeHeatTick()){
      FluidStack drained=input.getTank().drain(amount,IFluidHandler.FluidAction.EXECUTE);
      if(!drained.isEmpty()){output.getTank().fill(new FluidStack(fluid,drained.getAmount()),IFluidHandler.FluidAction.EXECUTE);b.progress=(b.progress+1)%100;b.running=true;b.superheated=hot;}
     }
    }
   }
  }
  if(b.ticks%20==0)b.setChanged();
 }
 private void validateStructure(){
  if(level==null){formed=false;return;}
  BoilerStructureValidator.Result r=BoilerStructureValidator.validate(level,worldPosition);
  formed=r.valid();fireboxPos=formed?r.firebox():null;waterHatchPos=formed?r.waterHatch():null;steamHatchPos=formed?r.steamHatch():null;
  if(!formed){progress=0;running=false;superheated=false;}
 }
 private BoilerFireboxBlockEntity getFirebox(){return level!=null&&fireboxPos!=null&&level.getBlockEntity(fireboxPos) instanceof BoilerFireboxBlockEntity be?be:null;}
 private BoilerWaterInputBlockEntity getWaterHatch(){return level!=null&&waterHatchPos!=null&&level.getBlockEntity(waterHatchPos) instanceof BoilerWaterInputBlockEntity be?be:null;}
 private BoilerSteamOutputBlockEntity getSteamHatch(){return level!=null&&steamHatchPos!=null&&level.getBlockEntity(steamHatchPos) instanceof BoilerSteamOutputBlockEntity be?be:null;}
 private int getWaterAmount(){BoilerWaterInputBlockEntity h=getWaterHatch();return h==null?0:h.getTank().getFluidAmount();}
 private int getSteamAmount(){BoilerSteamOutputBlockEntity h=getSteamHatch();return h==null?0:h.getTank().getFluidAmount();}
 @Override protected void saveAdditional(CompoundTag tag){super.saveAdditional(tag);tag.putBoolean("Formed",formed);tag.putInt("Progress",progress);}
 @Override public void load(CompoundTag tag){super.load(tag);formed=false;progress=tag.getInt("Progress");fireboxPos=null;waterHatchPos=null;steamHatchPos=null;}
}
