# Folkplay TV (independent reconstruction)

An independent Android TV / Amazon Fire TV starter app based on inspection of the supplied Folkplay 1.0.0 split-APK package. This repository does **not** contain Folkplay's original source code and is not an official build.

## Current scope

- Native Android application, package `com.zbinmy.folkplaytv` (does not overwrite the original app).
- TV launcher entry (`LEANBACK_LAUNCHER`), landscape layout, touchscreen not required.
- D-pad navigation and OK/Enter selection; Back exits a game; Menu resets a game.
- Playable local two-player Tic Tac Toe and Connect Four.
- Other catalogue entries are explicitly marked as not implemented. The catalogue is a small starter sample, not a full recreation of the original app's route inventory.

## Build

GitHub Actions builds a debug APK on pushes to `main` and uploads it as `folkplay-tv-debug-apk`. Open the repository's Actions tab, select the latest successful run, and download the artifact.

Local build prerequisites: JDK 17, Android SDK Platform 35, Build Tools 35.0.0, and Gradle 8.9.

```bash
gradle --no-daemon assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`.

## Fire TV notes

Install the debug APK for testing with ADB. This is not yet hardware-certified. Validate focus movement, remote key mapping, overscan/safe area, suspend/resume, and playback behaviour on the target Fire TV model before release. The app requests no network permissions and currently has no online services.

## Reference package

The supplied `Folkplay_v1.0.0(10).apks` archive identifies the original package as `com.mcs2k8.folkplay`, version 1.0.0 (version code 10), with split APKs. A compiled APK bundle is not equivalent to the original editable project: original JS/TS source, build configuration, signing keys, and service credentials are not included.
