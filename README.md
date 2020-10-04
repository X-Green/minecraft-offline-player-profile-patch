# Offline Player Profile patch

## Setup (for dev)

```
./gradlew gensources
./gradlew idea
```

## Why This

- If a server is setup under offline mode, no skins will be shown to players, to which the default Offline Profile contributes.
- Servers under this patch will try to get the online profile from Mojang even if not in online mode.
- It solves the UUID changes under on/offline mode, which is quite annoying because player data can't be correctly read if the server swaps between on/offline mode.

## Which Version

All known fabric versions (1.14, 1.15, 1.16 with fabric loader) of Minecraft are supported.