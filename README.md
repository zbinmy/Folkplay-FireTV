# Folkplay Arcade TV

An independent Android TV / Amazon Fire TV arcade project. It is not the original Folkplay source code or an official build.

## Current build
- 40-game catalogue with a TV-friendly scrollable menu.
- Landscape presentation; touchscreen is optional.
- D-pad navigation, OK/Enter action, Back to menu, Menu/Start to restart.
- Existing two-player Tic-Tac-Toe retained.
- Arcade prototypes include board, tile, reflex, memory, number and score-challenge modes. Some catalogue entries currently share prototype mechanics and are not yet complete faithful implementations of their named games.

## Build
GitHub Actions builds a debug APK on pushes to main and uploads `folkplay-tv-debug-apk`.

1. Open the repository Actions tab.
2. Select the latest successful Android TV build.
3. Download the `folkplay-tv-debug-apk` artifact.

Local build prerequisites: JDK 17, Android SDK Platform 35, Build Tools 35.0.0, Gradle 8.11.

```bash
gradle --no-daemon clean assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`.

## Fire TV validation
The project is remote-first and requests no network permissions. Test D-pad focus, game controls, Back/Menu behaviour, safe areas, suspend/resume and performance on the target Fire TV device. A successful CI build does not establish full gameplay correctness or hardware certification.
