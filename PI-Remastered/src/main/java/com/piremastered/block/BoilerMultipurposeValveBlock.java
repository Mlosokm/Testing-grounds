package com.piremastered.block;

import com.piremastered.blockentity.BoilerMultipurposeValveBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BoilerMultipurposeValveBlock extends BaseEntityBlock {
    public BoilerMultipurposeValveBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BoilerMultipurposeValveBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && isWrench(stack) && level.getBlockEntity(pos) instanceof BoilerMultipurposeValveBlockEntity valve) {
            valve.cycleMode();
            player.sendSystemMessage(Component.literal("Valve mode: " + valve.getMode().name()));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private static boolean isWrench(ItemStack stack) {
        String name = stack.getItem().getDescriptionId().toLowerCase();
        return name.contains("wrench") || name.contains("configurator") || name.contains("config");
    }
}
