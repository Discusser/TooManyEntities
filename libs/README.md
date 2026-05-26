# Architectury (TnTBass build)

Fabric builds use [architectury-19.0.1-fabric.jar](https://github.com/TnTBass/architectury-api/releases/tag/v19.0.1-26.1.1) from the TnTBass fork (Architectury API 19.0.1 for Minecraft 26.1+, from [architectury#708](https://github.com/architectury/architectury-api/pull/708)).

NeoForge uses `dev.architectury:architectury-neoforge:19.0.1` from Maven.

To refresh the Fabric jar:

```bash
curl -fsSL -o architectury-19.0.1-fabric.jar \
  https://github.com/TnTBass/architectury-api/releases/download/v19.0.1-26.1.1/architectury-19.0.1-fabric.jar
```
