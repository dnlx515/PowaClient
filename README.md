# PowaClient

A lightweight **client-side Minecraft 1.8.9 client** built on **Forge**, with a modern soft-dark UI, a draggable HUD and a Lunar-style crosshair editor.

> Client-side only. Not affiliated with Mojang or Microsoft. OptiFine is **not** bundled.

## Features

- **Custom main menu** — sunset background, glass panels and rounded buttons.
- **Mod menu** — categories (HUD / Player / Render / Misc), two-column layout, toggle switches.
- **Draggable HUD editor** — move every HUD element with the mouse; positions are saved.
- **HUD modules** — FPS, CPS, Keystrokes, Coordinates, Clock, Direction, Memory, Speed, Server, Day, Potion Effects, Armor.
- **Player / render modules** — Fullbright, Toggle Sprint, Toggle Sneak, Crosshair, Freelook, Zoom, Menu Blur.
- **Crosshair editor** — a 16x16 pixel grid you can draw on, presets, colour and scale, saved to disk.
- **Menu Blur** — real post-processing blur of the world behind any GUI.
- Module states and HUD positions **persist** between sessions.

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
