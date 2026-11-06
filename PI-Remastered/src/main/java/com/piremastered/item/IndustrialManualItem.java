package com.piremastered.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IndustrialManualItem extends Item {
    public IndustrialManualItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            player.sendSystemMessage(Component.literal("§6§l=== PI REMASTERED: INDUSTRIAL ENGINEER'S MANUAL ==="));
            player.sendSystemMessage(Component.literal("§e1. Build a sealed shell (3x3x3 to 15x15x15)."));
            player.sendSystemMessage(Component.literal("§e   - Corners & edges: Boiler Casing"));
            player.sendSystemMessage(Component.literal("§e   - Flat faces: Casing or Structural Glass"));
            player.sendSystemMessage(Component.literal("§e2. Place one Solid-Fuel Burner inside the chamber."));
            player.sendSystemMessage(Component.literal("§e3. Insert two Multipurpose Valves into the shell."));
            player.sendSystemMessage(Component.literal("§e   - Put water in one valve (inlet)"));
            player.sendSystemMessage(Component.literal("§e   - The other becomes the steam outlet"));
            player.sendSystemMessage(Component.literal("§e4. Feed fuel into the burner (hoppers/pipes work)."));
            player.sendSystemMessage(Component.literal("§e5. Right-click the Industrial Boiler panel and press IGNITE."));
            player.sendSystemMessage(Component.literal("§aValves expose Forge fluid capability for pipes."));
            player.sendSystemMessage(Component.literal("§aSteam carries temperature metadata."));
            player.sendSystemMessage(Component.literal("§7If the structure is invalid, the panel will tell you why."));
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
