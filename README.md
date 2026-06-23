# KOTH

A simple King of the Hill plugin for Paper servers.

## Requirements

- Paper 1.20+
- Java 17+
- WorldGuard
- PlaceholderAPI, optional

## Build

```sh
mvn package
```

The built plugin jar is created at:

```text
target/koth-1.0.0.jar
```

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
