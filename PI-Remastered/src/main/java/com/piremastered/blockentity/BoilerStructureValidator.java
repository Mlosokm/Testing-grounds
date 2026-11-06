package com.piremastered.blockentity;

import com.piremastered.registry.ModBlocks;
import com.piremastered.registry.ModFluids;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

public final class BoilerStructureValidator {
    public record Result(
            boolean valid,
            BlockPos solidFuelBurner,
            BlockPos waterValve,
            BlockPos steamValve,
            String error) {
        private static Result invalid(String message) {
            return new Result(false, null, null, null, message);
        }
    }

    private BoilerStructureValidator() {}

    public static Result validate(Level level, BlockPos controllerPos) {
        if (!isShell(level.getBlockState(controllerPos))) {
            return Result.invalid("The access panel must be connected to the casing shell");
        }

        Set<BlockPos> shell = new HashSet<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        shell.add(controllerPos.immutable());
        queue.add(controllerPos.immutable());

        while (!queue.isEmpty() && shell.size() <= 5_000) {
            BlockPos current = queue.removeFirst();
            for (Direction direction : Direction.values()) {
                BlockPos neighbor = current.relative(direction);
                if (!shell.contains(neighbor) && isShell(level.getBlockState(neighbor))) {
                    shell.add(neighbor);
                    queue.addLast(neighbor);
                }
            }
        }

        if (!queue.isEmpty()) {
            return Result.invalid("The casing shell exceeds the validation limit");
        }

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int maxZ = Integer.MIN_VALUE;

        for (BlockPos pos : shell) {
            minX = Math.min(minX, pos.getX());
            minY = Math.min(minY, pos.getY());
            minZ = Math.min(minZ, pos.getZ());
            maxX = Math.max(maxX, pos.getX());
            maxY = Math.max(maxY, pos.getY());
            maxZ = Math.max(maxZ, pos.getZ());
        }

        int width = maxX - minX + 1;
        int height = maxY - minY + 1;
        int depth = maxZ - minZ + 1;
        if (width < 3 || height < 3 || depth < 3
                || width > 15 || height > 15 || depth > 15) {
            return Result.invalid("Each boiler dimension must be between 3 and 15 blocks");
        }

        BlockPos burner = null;
        List<BlockPos> valves = new ArrayList<>();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = level.getBlockState(pos);
                    boolean edgeX = x == minX || x == maxX;
                    boolean edgeY = y == minY || y == maxY;
                    boolean edgeZ = z == minZ || z == maxZ;
                    boolean boundary = edgeX || edgeY || edgeZ;

                    if (boundary) {
                        if (!shell.contains(pos) || !isAllowedFace(state)) {
                            return Result.invalid("The shell has a gap or an unsupported face block");
                        }
                        int edgeCount = (edgeX ? 1 : 0) + (edgeY ? 1 : 0) + (edgeZ ? 1 : 0);
                        if (edgeCount >= 2 && !state.is(ModBlocks.BOILER_CASING.get())) {
                            return Result.invalid("All corners and frame edges must use Boiler Casing");
                        }
                        if (state.is(ModBlocks.BOILER_MULTIPURPOSE_VALVE.get())
                                && level.getBlockEntity(pos) instanceof BoilerMultipurposeValveBlockEntity) {
                            valves.add(pos.immutable());
                        }
                    } else if (!state.isAir()) {
                        if (state.is(ModBlocks.BOILER_SOLID_FUEL_BURNER.get())) {
                            if (burner == null) {
                                burner = pos.immutable();
                            }
                        } else if (!state.is(ModBlocks.BOILER_ELECTRIC_SUPERHEATER.get())) {
                            return Result.invalid("An unsupported block is inside the boiler chamber");
                        }
                    }
                }
            }
        }

        if (burner == null) {
            return Result.invalid("At least one Solid-Fuel Burner is required");
        }
        if (valves.size() < 2) {
            return Result.invalid("Two Multipurpose Valves are required: one for water and one for steam");
        }

        valves.sort(Comparator.comparingInt((BlockPos p) -> p.getX())
                .thenComparingInt((BlockPos p) -> p.getY())
                .thenComparingInt((BlockPos p) -> p.getZ()));

        BlockPos waterValve = valves.stream()
                .filter(pos -> fluidAt(level, pos) == Fluids.WATER)
                .findFirst().orElse(null);
        BlockPos steamValve = valves.stream()
                .filter(pos -> fluidAt(level, pos) == ModFluids.STEAM.get())
                .findFirst().orElse(null);

        if (waterValve == null) {
            BlockPos knownSteamValve = steamValve;
            waterValve = valves.stream()
                    .filter(pos -> !pos.equals(knownSteamValve))
                    .findFirst().orElse(valves.get(0));
        }
        if (steamValve == null) {
            BlockPos selectedWaterValve = waterValve;
            steamValve = valves.stream()
                    .filter(pos -> !pos.equals(selectedWaterValve))
                    .findFirst().orElse(null);
        }

        if (waterValve == null || steamValve == null || waterValve.equals(steamValve)) {
            return Result.invalid("The boiler needs two separate Multipurpose Valves");
        }

        return new Result(true, burner, waterValve, steamValve, "");
    }

    private static net.minecraft.world.level.material.Fluid fluidAt(Level level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof BoilerMultipurposeValveBlockEntity valve) {
            FluidStack fluid = valve.getTank().getFluid();
            return fluid.isEmpty() ? Fluids.EMPTY : fluid.getFluid();
        }
        return Fluids.EMPTY;
    }

    private static boolean isShell(BlockState state) {
        return state.is(ModBlocks.BOILER_CASING.get())
                || state.is(ModBlocks.BOILER_STRUCTURAL_GLASS.get())
                || state.is(ModBlocks.INDUSTRIAL_BOILER.get())
                || state.is(ModBlocks.BOILER_MULTIPURPOSE_VALVE.get());
    }

    private static boolean isAllowedFace(BlockState state) {
        return state.is(ModBlocks.BOILER_CASING.get())
                || state.is(ModBlocks.BOILER_STRUCTURAL_GLASS.get())
                || state.is(ModBlocks.INDUSTRIAL_BOILER.get())
                || state.is(ModBlocks.BOILER_MULTIPURPOSE_VALVE.get());
    }
}