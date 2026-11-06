# PI Remastered 0.3.0

Minecraft Java 1.20.1 · Forge 47.4.10 · Java 17

## Boiler construction update

The boiler uses a connected casing shell with a rectangular bounding size from 3×3×3 through 15×15×15. All twelve frame edges and corners must be Boiler Casing. Flat face panels may be Boiler Casing or Boiler Structural Glass. The shell must be complete.

Inside the shell, place at least one Standard Burner (or High-Tier Burner), a Boiler Water Input Hatch and a Boiler Steam Output Hatch. A High-Tier Burner additionally requires at least one Boiler Superheater. The existing Industrial Boiler Access Panel remains the current multiblock validation and ticking anchor in 0.3.0; controller-free right-click access is not implemented yet.

The Standard Burner accepts ordinary furnace fuels. The High-Tier Burner accepts burnable items listed in the piremastered:high_tier_fuels item tag (initially blaze rods, lava buckets and coal blocks). With a High-Tier Burner and a Boiler Superheater, the boiler produces piremastered:superheated_steam; otherwise it produces piremastered:steam. Both fluids are exposed by the steam output hatch's Forge fluid capability.

Use the in-game Industrial Engineer's Manual for the current build notes. The boiler screen now follows a horizontal Water → Burner → Steam layout with vertical meters and a shrinking flame icon. It no longer displays burn ticks or a pressure percentage.

## Turbine construction set

Version 0.3.0 adds Turbine Casing, Structural Glass, Valve, Rotor Shaft, Rotation Complex and five blade materials (copper, gold, iron, diamond and netherite) to the creative tab with a coordinated texture set. Live rotor animation, the full structure validator, induction scanning and steam-to-FE generation are not yet implemented. The manual lists planned induction materials, but no FE values are claimed in this release.

Planned induction materials are Lapis Lazuli, Copper, Iron, Gold, Diamond and Netherite blocks. Higher rarity is intended to produce more FE. Blade tiers are intended to trade pressure tolerance against rotational speed.

## Build and test status

Run gradle --no-daemon clean build with Java 17. A successful CI build proves compilation and packaging only; it does not replace in-game multiblock, fluid-pipe, GUI, save/reload or performance testing.
