# PI Remastered

Industrial machinery for Minecraft Java 1.20.1, Forge 47.4.10, Java 17.

## Modular industrial boiler

The boiler is a hollow 3x3x3 shell. Place the controller at the centre of the front face; it faces outward. Build the rest of the shell behind it. Leave the exact centre block of the middle layer empty.

- Boiler casing fills the remaining shell.
- At least one firebox belongs on the bottom layer.
- At least one water-input hatch and one steam-output hatch belong on the top layer.
- At least one pressure gauge belongs on either side column.
- Right-click the controller to open the live status screen.

The water-input hatch accepts water through Forge fluid capabilities and stores 16,000 mB. The firebox accepts valid furnace fuels through Forge item capabilities (so hoppers and item pipes can load it). While the structure has water, output capacity and fuel, the controller converts water to PI steam at 1 mB per burn tick. The steam-output hatch stores 16,000 mB and exposes the registered piremastered:steam fluid to Forge fluid pipes.

All six blocks are registered with block items, recipes, drops, models, names, and entries in the PI Remastered creative tab. The steam engine is intentionally outside this milestone.

## Build

Use Java 17 and run gradle --no-daemon clean build. The reobfuscated JAR is generated in build/libs/.
