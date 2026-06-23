<div align="center">
  <h1>KoTH</h1>
  <img alt="License" src="https://img.shields.io/github/license/xasciii/Koth">
  <img alt="GitHub Release" src="https://img.shields.io/github/release/xasciii/Koth.svg">
  <br><br>
  <a href="https://github.com/xasciii/Tourneys/releases/latest"><img alt="Download" src="https://img.shields.io/badge/-Download-blue?style=for-the-badge&logo=github"></a>
  <a href="https://discord.gg/ZmdNWv8vW6"><img alt="Discord" src="https://img.shields.io/badge/-Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white"></a>
</div>

A simple King of the Hill plugin for minecraft serverr. King of The Hill (KoTH) is an event that accours every x amount of time which gives the player who captures it a reward.

<img width="960" height="540" alt="image" src="https://github.com/user-attachments/assets/97fd8d22-e4d0-4d79-b9ef-4462f905f7d3" />


## Requirements

- Paper 1.20+
- Java 17+
- WorldGuard
- PlaceholderAPI, optional


## Commands

```text
/kothadmin forcestart
/kothadmin forceend
/kothadmin reload
```

Permission:

```text
koth.command.kothadmin
```

## Config

```yaml
tick-rate: 20
frequency: 5
cap-time: 5
cap-region: "kothcap"
reward-command: "/crate give {player} koth"
```

## PlaceholderAPI

The plugin registers placeholders under the `KOTH` identifier.

```text
%KOTH_countdown% - Time until the next KOTH starts
%KOTH_isactive% - Whether KOTH is currently active
%KOTH_end% - Time until the current cap finishes
%KOTH_capper% - Current capper name, or None
%KOTH_hologram% - Formatted status text for holograms
```

## Credits

**Maintainer: [asc](https://github.com/xasciii)**


## License

This project is licensed under the [GPL3 License](LICENSE).
