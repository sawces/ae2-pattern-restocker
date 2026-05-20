# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Initial release for Minecraft 26.1.2 / NeoForge 26.1.x / AE2 26.1.x

### Fixed
- Blank pattern extraction correctly respects ME network security permissions; the action is attributed to the player operating the terminal rather than being anonymous
- Game no longer crashes on startup if an AE2 update causes mixin incompatibility; the mod silently disables itself instead
