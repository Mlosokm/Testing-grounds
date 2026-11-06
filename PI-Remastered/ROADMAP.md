# PI Remastered — Master Bucket List

A checked item means implemented in source, not necessarily verified in a live Minecraft client.

## Foundation
- [x] Forge 1.20.1, Java 17, mod ID piremastered.
- [x] Dedicated creative tab and reproducible GitHub Actions build.
- [x] Industrial Engineer's Manual item.
- [x] Replace first-pass boiler textures and GUI with a coordinated steel/copper pixel-art set.
- [ ] In-game client and Android-launcher verification.

## Boiler
- [x] Connected shell validator with 3×3×3 minimum and 15×15×15 maximum bounding dimensions.
- [x] Boiler Casing required on every frame edge and corner.
- [x] Structural Glass allowed on flat face panels.
- [x] Standard Burner, High-Tier Burner and Boiler Superheater.
- [x] High-Tier Burner restricted to the high_tier_fuels item tag.
- [x] High-Tier Burner produces distinct Superheated Steam.
- [x] Horizontal Water → Burner → Steam screen with vertical meters and visual flame recession; no burn-tick or pressure percentage text.
- [x] Redstone signal from the current access/controller block when valid.
- [ ] Controller-free operation and right-click access from any component.
- [ ] Optional temporary tank blocks and dynamic capacity.
- [ ] In-game multiblock, fuel, pipe and save/reload verification.

## Turbines
- [x] In-house turbine implementation cancelled; turbine visuals and mechanics are delegated to established turbine mods.
- [ ] Consider optional compatibility/integration only if a concrete use case emerges.

## Later progressions
- [ ] Industrial Mining (bounded 3×3 to 15×15 sizing, FE scaling, configured bedrock loot and nearest vein mode).
- [ ] Pneumatic Power: compressed-air generation, storage, distribution, pressure and machinery.
- [ ] Oil Industries: finite deposits, fracking gas, modular engines, modular boiler and modular/preassembled cannon options.
- [ ] Clean Nuclear Power and planned railcannon.
- [ ] Dark Matter Reactor.

## Release quality
- [ ] In-game placement, breaking/drop, chunk reload and save/reload tests.
- [ ] Fluid and energy pipe compatibility tests.
- [ ] Performance test on target Android hardware.
- [ ] Keep untested features clearly marked as untested.
