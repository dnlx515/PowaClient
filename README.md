# PowaClient

A lightweight **Minecraft 1.8.9 client** built on **Forge**.

> Not affiliated with Mojang or Microsoft. Just a small project.

## Keys

| Key | Action |
| --- | --- |
| Right Shift | Open the PowaClient menu |
| H | Open the HUD editor |
| C | Zoom (hold) |
| V | Freelook (hold) |

All bindings can be changed in *Options → Controls*.

## Requirements

- Minecraft **1.8.9**
- **Forge 1.8.9** (build `11.15.1.2318`)
- **Java 8** (required by Forge 1.8.9)

## Install (players)

1. Install Forge 1.8.9 in your launcher.
2. Drop `PowaClient-<version>.jar` into your `mods` folder.
3. Launch the Forge 1.8.9 profile.

## Build from source (developers)

Requires JDK 8.

```bash
export JAVA_HOME=/path/to/jdk8
./gradlew build
```

The mod jar is written to `build/libs/PowaClient-<version>.jar`.

## License

MIT — see [LICENSE](LICENSE). "Minecraft" and "Forge" are trademarks of their respective owners.
