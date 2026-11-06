package com.piremastered.item;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
public class IndustrialManualItem extends Item {
 public IndustrialManualItem(Properties properties){super(properties.stacksTo(1));}
 @Override public InteractionResultHolder<ItemStack> use(Level level,Player player,InteractionHand hand){
  ItemStack stack=player.getItemInHand(hand);
  if(!level.isClientSide){
   player.sendSystemMessage(Component.literal("=== PI REMASTERED: INDUSTRIAL ENGINEER'S MANUAL ==="));
   player.sendSystemMessage(Component.literal("BOILER: sealed shell, 3x3x3 minimum and 15x15x15 maximum."));
   player.sendSystemMessage(Component.literal("Corners and frame edges: Boiler Casing. Flat face panels: Casing or Structural Glass."));
   player.sendSystemMessage(Component.literal("Inside: Water Input Hatch, Steam Output Hatch and at least one Burner."));
   player.sendSystemMessage(Component.literal("High-Tier Burner requires a Boiler Superheater and a fuel in the high_tier_fuels item tag."));
   player.sendSystemMessage(Component.literal("TURBINE: Turbine Casing frame, Structural Glass side panels, Turbine Valves, Rotor Shaft and Rotation Complex."));
   player.sendSystemMessage(Component.literal("Five blade materials: Copper, Gold, Iron, Diamond, Netherite. Rotor animation and FE generation are not active in this build."));
   player.sendSystemMessage(Component.literal("INDUCTION MATERIALS PLANNED: Lapis Lazuli, Copper, Iron, Gold, Diamond and Netherite blocks."));
   player.sendSystemMessage(Component.literal("Rarer induction blocks are intended to generate more FE. Exact values are pending working generator balancing."));
  }
  return InteractionResultHolder.sidedSuccess(stack,level.isClientSide);
 }
}
