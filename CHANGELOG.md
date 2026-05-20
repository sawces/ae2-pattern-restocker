# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.1] - 2026-05-20

### Fixed
- Blank pattern extraction now correctly respects ME network security permissions; the action is attributed to the player operating the terminal rather than being anonymous
- Game no longer crashes on startup if an AE2 update causes mixin incompatibility; the mod silently disables itself instead

## [1.0.0] - Initial Release

### Added
- Automatically refills the blank pattern slot in the Pattern Encoding Terminal from blank patterns stored in the ME network when the slot is empty and Encode is clicked
- No configuration, special items, cards, or upgrades required
