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
            player.sendSystemMessage(Component.literal("=== PI REMASTERED: INDUSTRIAL ENGINEER'S MANUAL ==="));
            player.sendSystemMessage(Component.literal("BOILER: build a sealed shell, at least 3x3x3 and at most 15x15x15."));
            player.sendSystemMessage(Component.literal("Use Boiler Casing on every corner and frame edge; flat faces may use Structural Glass."));
            player.sendSystemMessage(Component.literal("Place one Solid-Fuel Burner inside the chamber and feed it combustible items through item automation."));
            player.sendSystemMessage(Component.literal("Open the boiler panel and press IGNITE to start the loaded fuel. The burner does not light itself."));
            player.sendSystemMessage(Component.literal("Place two Multipurpose Valves in the shell. The valve holding water is detected as the inlet; the other is the steam outlet."));
            player.sendSystemMessage(Component.literal("Multipurpose Valves expose Forge fluid capability for connected fluid pipes. Steam leaving a valve carries its temperature metadata."));
            player.sendSystemMessage(Component.literal("The boiler temperature is simulated from the active fuel profile and cools toward ambient when heat stops."));
            player.sendSystemMessage(Component.literal("Closed-loop heating, exotic coolants, and additional burner types are planned for later development."));
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}