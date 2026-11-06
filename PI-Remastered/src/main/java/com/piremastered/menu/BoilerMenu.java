package com.piremastered.menu;
import com.piremastered.blockentity.BoilerControllerBlockEntity;
import com.piremastered.registry.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
public class BoilerMenu extends AbstractContainerMenu {
 private final ContainerData data;
 private final BlockPos pos;
 public BoilerMenu(int id,Inventory inventory,BoilerControllerBlockEntity controller){this(id,inventory,controller.getBlockPos(),controller.getMenuData());}
 public BoilerMenu(int id,Inventory inventory,FriendlyByteBuf buffer){this(id,inventory,buffer.readBlockPos(),new SimpleContainerData(7));}
 private BoilerMenu(int id,Inventory inventory,BlockPos pos,ContainerData data){
  super(ModMenuTypes.BOILER_MENU.get(),id);this.pos=pos;this.data=data;addDataSlots(data);
 }
 public int getData(int index){return data.get(index);}
 @Override public boolean stillValid(Player player){return player.distanceToSqr(pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5)<64.0D;}
}
