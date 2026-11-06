# PI Remastered — Master Bucket List

This is the living feature backlog for **PI Remastered**. It gathers the ideas and requirements discussed so far. Items are plans unless marked otherwise; a checklist is not a claim that a feature already exists.

## Project foundation

- [ ] Fresh restart under the name **PI Remastered** (not the old Java Tea project identity).
- [ ] Target **Minecraft Java 1.20.1**, **Forge 47.4.10**, **Java 17**.
- [ ] Mod ID: `piremastered`.
- [ ] Every real machine must have working behavior, not just a renamed vanilla block or decorative placeholder.
- [ ] Add all PI blocks/items to a dedicated, visible creative-mode tab.
- [ ] Keep machines usable on the user's Android/low-power setup: avoid needless ticking, heavy rendering, and avoidable memory use.
- [ ] Build reproducibly with Gradle/GitHub Actions; distinguish a successful compile from an in-game tested feature.

## Immediate milestone: working boiler

- [ ] A placeable, functional boiler machine with its own block identity and block entity.
- [ ] Water input through Forge fluid pipes using Forge fluid capabilities (`IFluidHandler`).
- [ ] Fuel inserted by right-clicking the boiler with valid furnace fuel (coal is the initial expected fuel).
- [ ] Steam output through Forge fluid pipes and an exposed fluid capability.
- [ ] Empty-hand right-click opens a traditional Minecraft-style GUI.
- [ ] GUI clearly presents **Water → heating/fire → Steam**, plus the **Coal/fuel** slot or fuel state.
- [ ] Server-authoritative processing; GUI values synchronize correctly between server and client.
- [ ] Machine state and stored fluids/fuel survive saving and reloading.
- [ ] Initial design target discussed: 4,000 mB water and 4,000 mB steam storage; 20 mB water converted to 20 mB steam per 20 ticks. Treat these as starting balance values, subject to tuning.
- [ ] Register a PI steam fluid (planned ID: `piremastered:steam`) so pipes can transport the output.
- [ ] Validate actual compatibility with Forge fluid pipes in-game; compilation alone is not proof.

## Steam Age / steam power

- [ ] Make the steam engine a real working machine, not a placeholder block.
- [ ] Boiler-to-engine steam supply and meaningful energy/mechanical output.
- [ ] A modular boiler setup is a later expansion: multiple dedicated boiler parts/blocks assembled into a configurable boiler installation.
- [ ] Industrial progression around producing, transporting, and consuming steam.

## Industrial mining

- [ ] Industrial mining multiblock.
- [ ] 3×3 starting footprint, expandable up to a configurable hard maximum around 15×15.
- [ ] Validate the assembled structure and report missing/invalid parts in its GUI.
- [ ] FE/energy use scales with machine size and work.
- [ ] Bedrock-loot operating mode configurable through JSON.
- [ ] Nearest-ore-vein operating mode, with behavior that remains useful/controlled in superflat worlds.
- [ ] Keep the multiblock's search and ticking work bounded to protect performance.

## Pneumatic Power

This is a **compressed-air system**, not a steam-whistle feature.

- [ ] Compressed-air generation.
- [ ] Air storage/reservoirs.
- [ ] Distribution through a pipe/network system.
- [ ] Pressure tracking and useful pressure behavior.
- [ ] Pneumatic machinery powered by compressed air.

## Oil Industries

- [ ] Oil deposits generated in underground patches.
- [ ] Deposits are finite and can run out.
- [ ] Bedrock-level fallback source, also finite/eventually exhausted.
- [ ] Fracking produces natural gas.
- [ ] Modular engines with configurable/replaceable components.
- [ ] Modular boiler setup as part of the later oil/industrial machinery expansion.
- [ ] Gunpowder cannons built from modular parts.
- [ ] Also provide a preassembled Vulcan-style cannon core/barrel arrangement.
- [ ] Cannon aiming/direction control without introducing a large performance cost.

## Clean Nuclear Power

- [ ] A distinct nuclear-power progression/system.
- [ ] Railcannon as a planned high-tier machine/weapon concept.
- [ ] Keep its implementation and balance separate from ordinary steam/oil machinery.

## Dark Matter Reactor (DMR)

- [ ] Separate dark-matter-reactor progression/subsystem.
- [ ] Treat it as a later, high-tier project rather than an early-game boiler feature.

## Planned modular/add-on structure

The following names were discussed as separate installable expansions/submods or distinct progressions. Keep their identities clear rather than collapsing them into one generic machine set:

- [ ] Steam Age
- [ ] Pneumatic Power
- [ ] Oil Industries
- [ ] Clean Nuclear Power
- [ ] DMR (Dark Matter Reactor)

## Visual direction

- [ ] Pixel-art industrial look based on the user's reference: copper and gray metal panels, vents/grilles, dark industrial surfaces, orange glowing fire/port details, gauges, and indicator lights.
- [ ] Machine blocks should look purpose-built and visually communicate their function.
- [ ] Avoid relying on vanilla copper/iron block textures as the final machine appearance.

## Quality and release checklist

- [ ] Each machine has a defined input, processing rule, output, GUI/feedback, and persistence behavior.
- [ ] Add recipes, tags, loot, language entries, blockstates, models, and textures for every implemented feature.
- [ ] Test placement, breaking, drops, chunk unload/reload, save/reload, GUI interaction, and pipe input/output in-game.
- [ ] Test with the intended Forge version and Java runtime.
- [ ] Record what is implemented, what compiles, and what has actually been tested; never label an untested feature as finished.

## Scope notes / decisions to preserve

- The boiler is the first concrete machine target and must keep the water-in / hand-fed-fuel / steam-out interaction.
- The GUI must be a real interactive machine screen, not just a decorative texture.
- The dedicated creative tab is required so the user can find and place the mod's blocks.
- Pneumatic Power means compressed air generation, storage, distribution, pressure, and pneumatic machinery — **not** a renamed steam whistle.
- Oil deposits are intended to be exhaustible; do not make the patch or bedrock fallback an infinite source.
- Cannon direction/aiming should not require expensive constant updates.
- Do not call placeholder registrations a completed machine.
