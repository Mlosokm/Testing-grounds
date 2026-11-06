# PI Remastered 0.3.1

Minecraft Java 1.20.1 · Forge 47.4.10 · Java 17

## Boiler (current)

The boiler uses a connected casing shell from 3×3×3 to 15×15×15.
- Corners and frame edges must be **Boiler Casing**.
- Flat faces may be Casing or **Structural Glass**.
- Place one **Solid-Fuel Burner** inside the chamber.
- Insert two **Multipurpose Valves** into the shell.
  - Put water in one valve (becomes the inlet).
  - The other becomes the steam outlet.
- Right-click the **Industrial Boiler** panel and press **IGNITE**.

If the structure is invalid, the panel will tell you the reason on right-click.

Valves expose Forge fluid capability so pipes can connect. Steam carries temperature metadata.

The GUI shows Water / Fuel / Temperature / Steam meters and an IGNITE button. The background is drawn in code (no external texture required).

## Industrial Engineer's Manual

Use the manual item for step-by-step build instructions in chat.

## Turbines

Turbine components are present as visual placeholders. Full turbine mechanics are deferred to other mods for now.

## Build

```bash
gradle --no-daemon clean build
```

A successful CI build only proves compilation. In-game testing is still needed.
