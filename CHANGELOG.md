# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.1.0] - 2026-05-20

### Added
- Return-to-blank button on the Pattern Encoding Terminal: a small X badge on the encoded pattern slot converts the encoded pattern back to a blank pattern and returns it to the blank pattern slot, ME storage, or player inventory (in that priority order). Only visible when the slot contains an encoded pattern.
- Config file (`config/ae2_pattern_restocker-common.toml`) with toggles for both features (`autoRestock` and `returnButton`), both enabled by default.
- Initial release for Minecraft 26.1.2 / NeoForge 26.1.x / AE2 26.1.x

### Fixed
- Blank pattern extraction correctly respects ME network security permissions; the action is attributed to the player operating the terminal rather than being anonymous
- Game no longer crashes on startup if an AE2 update causes mixin incompatibility; the mod silently disables itself instead
- Updated client action registration to use the `ClientActionKey` API introduced in AE2 26.1.x; fixes crash on startup with ATM11 and other 26.1.x modpacks
