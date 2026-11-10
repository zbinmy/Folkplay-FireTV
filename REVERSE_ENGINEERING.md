# Reverse-engineering notes

## Input examined

`Folkplay_v1.0.0(10).apks`, supplied in this conversation. It is an APK-set ZIP containing `base.apk`, ABI/language/density split APKs, and `manifest.jsonan.

## Confirmed package metadata

- Application ID: `com.mcs2k8.folkplay`
- Version: `1.0.0`, version code `10`
- Compiled application bundle; Expo / React Native runtime indicators are present.

## What this does and does not establish

The archive permits inspection of the compiled manifest, packaged assets, and compiled application bundle. It does not provide the original project source tree, editable TypeScript/JavaScript modules, dependency lockfile, original signing key, or backend credentials. Rebuilding the original app byte-for-byte or claiming full feature parity is therefore not supported by the supplied archive alone.

This repository is an independent native Android TV implementation. Its current playable scope is Tic Tac Toe. Additional games and original app flows require separate implementation and testing; catalogue labels are not evidence of implemented functionality.
