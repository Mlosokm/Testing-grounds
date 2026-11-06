package com.piremastered.blockentity;
import com.piremastered.block.BoilerFireboxBlock;
import com.piremastered.registry.ModBlocks;
import java.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public final class BoilerStructureValidator {
 public record Result(boolean valid,BlockPos firebox,BlockPos waterHatch,BlockPos steamHatch,boolean hasSuperheater,boolean hasHighTierBurner,String error){
  static Result invalid(String why){return new Result(false,null,null,null,false,false,why);}
 }
 private BoilerStructureValidator(){}
 public static Result validate(Level level,BlockPos controllerPos){
  if(!isShell(level.getBlockState(controllerPos)))return Result.invalid("Access panel must be connected to the casing shell");
  Set<BlockPos> shell=new HashSet<>();ArrayDeque<BlockPos> queue=new ArrayDeque<>();
  shell.add(controllerPos.immutable());queue.add(controllerPos.immutable());
  while(!queue.isEmpty()&&shell.size()<=5000){
   BlockPos p=queue.removeFirst();
   for(Direction d:Direction.values()){
    BlockPos n=p.relative(d);
    if(!shell.contains(n)&&isShell(level.getBlockState(n))){shell.add(n);queue.addLast(n);}
   }
  }
  if(!queue.isEmpty())return Result.invalid("Shell exceeds safety scan limit");
  int minX=Integer.MAX_VALUE,minY=Integer.MAX_VALUE,minZ=Integer.MAX_VALUE,maxX=Integer.MIN_VALUE,maxY=Integer.MIN_VALUE,maxZ=Integer.MIN_VALUE;
  for(BlockPos p:shell){minX=Math.min(minX,p.getX());minY=Math.min(minY,p.getY());minZ=Math.min(minZ,p.getZ());maxX=Math.max(maxX,p.getX());maxY=Math.max(maxY,p.getY());maxZ=Math.max(maxZ,p.getZ());}
  int w=maxX-minX+1,h=maxY-minY+1,d=maxZ-minZ+1;
  if(w<3||h<3||d<3||w>15||h>15||d>15)return Result.invalid("Each boiler dimension must be between 3 and 15 blocks");
  BlockPos firebox=null,highTierFirebox=null,water=null,steam=null;int heaters=0,highBurners=0;
  for(int x=minX;x<=maxX;x++)for(int y=minY;y<=maxY;y++)for(int z=minZ;z<=maxZ;z++){
   BlockPos p=new BlockPos(x,y,z);BlockState s=level.getBlockState(p);
   boolean ex=x==minX||x==maxX,ey=y==minY||y==maxY,ez=z==minZ||z==maxZ;
   boolean boundary=ex||ey||ez;
   if(boundary){
    if(!shell.contains(p)||!isAllowedFace(s))return Result.invalid("Boundary is incomplete or contains an unsupported panel");
    int edges=(ex?1:0)+(ey?1:0)+(ez?1:0);
    if(edges>=2&&!s.is(ModBlocks.BOILER_CASING.get()))return Result.invalid("Every corner and frame edge must be Boiler Casing");
    if(s.is(ModBlocks.BOILER_WATER_INPUT.get()))water=p;
    if(s.is(ModBlocks.BOILER_STEAM_OUTPUT.get()))steam=p;
   }else if(!s.isAir()){
    if(s.is(ModBlocks.BOILER_FIREBOX.get())||s.is(ModBlocks.BOILER_HIGH_TIER_BURNER.get())){
     if(firebox==null)firebox=p;
     if(s.getBlock() instanceof BoilerFireboxBlock b&&b.isHighTier()){highBurners++;highTierFirebox=p;}
    }else if(s.is(ModBlocks.BOILER_SUPERHEATER.get()))heaters++;
    else return Result.invalid("Unsupported block found inside boiler chamber");
   }
  }
  if(water==null||steam==null||firebox==null)return Result.invalid("Needs water input, steam output and at least one burner");
  if(!(level.getBlockEntity(water) instanceof BoilerWaterInputBlockEntity)||!(level.getBlockEntity(steam) instanceof BoilerSteamOutputBlockEntity))return Result.invalid("A fluid hatch block entity is missing");
  if(highBurners>0&&heaters==0)return Result.invalid("High-Tier Burner requires Boiler Superheater blocks");
  return new Result(true,highTierFirebox!=null?highTierFirebox:firebox,water,steam,heaters>0,highBurners>0,"");
 }
 private static boolean isShell(BlockState s){
  return s.is(ModBlocks.BOILER_CASING.get())||s.is(ModBlocks.BOILER_STRUCTURAL_GLASS.get())||s.is(ModBlocks.INDUSTRIAL_BOILER.get())||s.is(ModBlocks.BOILER_WATER_INPUT.get())||s.is(ModBlocks.BOILER_STEAM_OUTPUT.get())||s.is(ModBlocks.BOILER_PRESSURE_GAUGE.get());
 }
 private static boolean isAllowedFace(BlockState s){
  return s.is(ModBlocks.BOILER_CASING.get())||s.is(ModBlocks.BOILER_STRUCTURAL_GLASS.get())||s.is(ModBlocks.INDUSTRIAL_BOILER.get())||s.is(ModBlocks.BOILER_WATER_INPUT.get())||s.is(ModBlocks.BOILER_STEAM_OUTPUT.get())||s.is(ModBlocks.BOILER_PRESSURE_GAUGE.get());
 }
}
