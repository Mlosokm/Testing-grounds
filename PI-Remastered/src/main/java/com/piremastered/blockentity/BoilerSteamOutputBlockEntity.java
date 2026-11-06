package com.piremastered.blockentity;
import com.piremastered.registry.ModBlockEntities;
import com.piremastered.registry.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
public class BoilerSteamOutputBlockEntity extends BoilerFluidHatchBlockEntity {
 public BoilerSteamOutputBlockEntity(BlockPos pos,BlockState state){super(ModBlockEntities.BOILER_STEAM_OUTPUT.get(),pos,state,fluid->fluid==ModFluids.STEAM.get());}
}
