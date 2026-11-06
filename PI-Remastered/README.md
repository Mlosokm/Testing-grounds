# PI Remastered

Fresh Minecraft Java mod project.

- Minecraft: 1.20.1
- Forge: 47.4.10
- Java: 17
- Mod ID: `piremastered`
- Version: 0.1.2

## Current baseline

This first build milestone contains registered, placeable Industrial Boiler and Boiler Casing blocks, block items, models, language names, and a dedicated PI Remastered creative tab.

The functional water/fuel/steam machine, fluid capabilities, and GUI are not implemented in this baseline yet.

## Build

Run with JDK 17 and Gradle 8.8:

```bash
gradle --no-daemon build
```

The reobfuscated mod JAR is generated in `build/libs/`.
