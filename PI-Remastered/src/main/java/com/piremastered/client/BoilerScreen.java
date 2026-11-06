package com.piremastered.client;
import com.piremastered.menu.BoilerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
public class BoilerScreen extends AbstractContainerScreen<BoilerMenu> {
 public BoilerScreen(BoilerMenu menu,Inventory inventory,Component title){
  super(menu,inventory,title);imageWidth=208;imageHeight=132;titleLabelX=8;titleLabelY=7;
 }
 @Override protected void renderBg(GuiGraphics g,float partialTick,int mouseX,int mouseY){
  int x=leftPos,y=topPos;
  g.fill(x,y,x+imageWidth,y+imageHeight,0xFF24282B);
  g.fill(x+4,y+4,x+imageWidth-4,y+imageHeight-4,0xFF41494D);
  g.fill(x+8,y+23,x+imageWidth-8,y+imageHeight-8,0xFF202629);
  int water=Math.min(100,menu.getData(1)*100/16000);
  int steam=Math.min(100,menu.getData(2)*100/16000);
  int burn=menu.getData(5)<=0?0:Math.min(100,menu.getData(4)*100/menu.getData(5));
  g.fill(x+12,y+46,x+112,y+53,0xFF101416);g.fill(x+12,y+46,x+12+water,y+53,0xFF3989C9);
  g.fill(x+12,y+69,x+112,y+76,0xFF101416);g.fill(x+12,y+69,x+12+steam,y+76,0xFFE18A38);
  g.fill(x+12,y+92,x+112,y+99,0xFF101416);g.fill(x+12,y+92,x+12+burn,y+99,0xFFE3B54D);
 }
 @Override protected void renderLabels(GuiGraphics g,int mouseX,int mouseY){
  g.drawString(font,title,titleLabelX,titleLabelY,0xFFE6E6E6,false);
  String status=switch(menu.getData(0)){case 2->"RUNNING";case 1->"FORMED - IDLE";default->"INCOMPLETE STRUCTURE";};
  g.drawString(font,"STATUS: "+status,12,29,0xFFE8E8E8,false);
  g.drawString(font,"WATER  "+menu.getData(1)+" / 16000 mB",12,36,0xFF9CCCF0,false);
  g.drawString(font,"STEAM  "+menu.getData(2)+" / 16000 mB",12,59,0xFFFFBC79,false);
  g.drawString(font,"FIREBOX  "+menu.getData(4)+" ticks",12,82,0xFFFFDA78,false);
  g.drawString(font,"PRESSURE  "+menu.getData(6)+"%",124,49,0xFFE6E6E6,false);
  g.drawString(font,"PROGRESS  "+menu.getData(3)+"%",124,72,0xFFE6E6E6,false);
  g.drawString(font,"Fuel goes in the firebox.",12,109,0xFFB7C1C5,false);
 }
 @Override public void render(GuiGraphics g,int mouseX,int mouseY,float partialTick){
  renderBackground(g);super.render(g,mouseX,mouseY,partialTick);renderTooltip(g,mouseX,mouseY);
 }
}
