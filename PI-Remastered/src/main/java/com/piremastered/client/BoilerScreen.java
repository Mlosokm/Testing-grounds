package com.piremastered.client;
import com.piremastered.PIRemastered;
import com.piremastered.menu.BoilerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
public class BoilerScreen extends AbstractContainerScreen<BoilerMenu> {
 private static final ResourceLocation TEXTURE=new ResourceLocation(PIRemastered.MOD_ID,"textures/gui/boiler.png");
 public BoilerScreen(BoilerMenu menu,Inventory inventory,Component title){super(menu,inventory,title);imageWidth=208;imageHeight=132;titleLabelX=8;titleLabelY=7;}
 @Override protected void renderBg(GuiGraphics g,float partialTick,int mouseX,int mouseY){
  int x=leftPos,y=topPos;g.blit(TEXTURE,x,y,0,0,imageWidth,imageHeight,imageWidth,imageHeight);
  int water=Math.max(0,Math.min(16000,menu.getData(1))),steam=Math.max(0,Math.min(16000,menu.getData(2)));
  int burn=menu.getData(5)<=0?0:Math.max(0,Math.min(100,menu.getData(4)*100/menu.getData(5)));
  int wh=water*52/16000,sh=steam*52/16000;
  g.fill(x+29,y+48,x+35,y+102,0xFF101719);g.fill(x+29,y+102-wh,x+35,y+102,0xFF3999C9);
  g.fill(x+163,y+48,x+169,y+102,0xFF101719);g.fill(x+163,y+102-sh,x+169,y+102,0xFFD7E0DF);
  int cover=(100-burn)*18/100;if(cover>0)g.fill(x+96,y+61,x+114,y+61+cover,0xFF252B2C);
 }
 @Override protected void renderLabels(GuiGraphics g,int mouseX,int mouseY){
  g.drawString(font,"WATER",20,32,0xFFB9D9E5,false);g.drawString(font,"BURNER",87,32,0xFFFFC27D,false);g.drawString(font,"STEAM",151,32,0xFFE4E8E4,false);
  String status=switch(menu.getData(0)){case 2->"RUNNING";case 1->"FORMED - IDLE";default->"STRUCTURE INCOMPLETE";};
  g.drawString(font,status,14,113,0xFFE7E2D5,false);if(menu.getData(6)!=0)g.drawString(font,"SUPERHEATED",132,113,0xFFFFA65C,false);
 }
 @Override public void render(GuiGraphics g,int mouseX,int mouseY,float partialTick){renderBackground(g);super.render(g,mouseX,mouseY,partialTick);renderTooltip(g,mouseX,mouseY);}
}
