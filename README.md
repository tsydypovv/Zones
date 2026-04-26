# ZonesAPI

Public API module for the `Zones` plugin.

This repository is meant to contain only the API surface used by third-party plugins:
- zone lookups
- plot lookups and permission checks
- guild world plot lookups
- mob spawn definitions
- managed mob runtime handles
- managed mob ability registration and attachment

## Build

```bash
mvn -q -DskipTests package
```

## Usage

```java
import me.M1ran.zones.api.ZonesApi;
import org.bukkit.Bukkit;

ZonesApi api = Bukkit.getServicesManager().load(ZonesApi.class);
if (api == null) {
    return;
}
```

## Managed Mob Abilities

```java
NamespacedKey key = new NamespacedKey(myPlugin, "fire_burst");
api.registerManagedMobAbility(key, new ManagedMobAbility() {
    @Override
    public void onTick(ManagedMobHandle handle) {
        handle.currentTarget().ifPresent(target -> {
            if (handle.tryUseCooldown(key, 60L)) {
                target.setFireTicks(60);
            }
        });
    }
});

api.attachManagedMobAbility("wild_zone", "boss_spawn", key);
```

## Export From Main Plugin Repository

Use `export-api.ps1` from the main `Zones` workspace to refresh this folder from the current API sources.
