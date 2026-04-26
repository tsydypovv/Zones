# ZonesAPI

Public API module for the `Zones` plugin.

This repository is intentionally limited to the API surface for third-party plugins.
It does not contain the private implementation of the main `Zones` plugin.

## What This API Covers

`ZonesAPI` gives other plugins access to:
- zone lookup and zone metadata
- plot lookup and plot metadata
- guild world plot lookup
- permission checks for build, interact, containers, enter, and pvp
- mob spawn definitions
- managed mob runtime lookup
- managed mob ability registration and attachment

## Build

```bash
mvn -q -DskipTests package
```

## Dependency Setup

At the moment the simplest public integration path is `JitPack`.

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.tsydypovv</groupId>
        <artifactId>Zones</artifactId>
        <version>master-SNAPSHOT</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

### Gradle

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compileOnly 'com.github.tsydypovv:Zones:master-SNAPSHOT'
}
```

### Recommended Versioning

For real plugin releases it is better to use Git tags instead of `master-SNAPSHOT`.

Example:

```xml
<dependency>
    <groupId>com.github.tsydypovv</groupId>
    <artifactId>Zones</artifactId>
    <version>v0.1.0</version>
    <scope>provided</scope>
</dependency>
```

## Getting The API

Use Bukkit `ServicesManager`:

```java
import me.M1ran.zones.api.ZonesApi;
import org.bukkit.Bukkit;

ZonesApi api = Bukkit.getServicesManager().load(ZonesApi.class);
if (api == null) {
    return;
}
```

## Main Entry Point

The main entry point is `ZonesApi`.

Important methods:
- `getZones()` returns all known zones.
- `getZone(zoneId)` returns zone metadata by id.
- `findZone(location)` finds the zone containing a location.
- `getPlots(zoneId)` returns plots inside a zone.
- `findPlot(location)` finds the plot containing a location.
- `findGuildWorldPlot(location)` finds the guild plot linked to a guild world.
- `getMobSpawn(zoneId, spawnId)` returns one mob spawn definition.
- `getMobSpawns(zoneId)` returns all mob spawns in a zone.
- `canEnter(player, location)` checks if the player can enter the plot at the location.
- `canBuild(player, location)` checks if the player can build there.
- `canInteract(player, location)` checks if the player can interact there.
- `canUseContainers(player, location)` checks if the player can use containers there.
- `canPvp(player, location)` checks pvp permission in the relevant plot or zone.
- `isManagedMob(entity)` checks whether an entity belongs to a managed `Zones` mob spawn.
- `getManagedMob(entity)` returns read-only runtime info about a managed mob.
- `getManagedMobHandle(entity)` returns a control handle for a managed mob.
- `registerManagedMobAbility(key, ability)` registers a managed mob ability.
- `unregisterManagedMobAbility(key)` removes a previously registered ability.
- `attachManagedMobAbility(zoneId, spawnId, key)` attaches an ability to a specific mob spawn.
- `detachManagedMobAbility(zoneId, spawnId, key)` removes an attachment.
- `getAttachedManagedMobAbilities(zoneId, spawnId)` lists the abilities attached to one spawn.

## Data Models

### `ZoneInfo`

Read-only snapshot of a zone.

Fields:
- `id`
- `type`
- `bounds`
- `pvpEnabled`

### `PlotInfo`

Read-only snapshot of a plot.

Useful fields:
- `zoneId`
- `id`
- `type`
- `bounds`
- `ownerUuid`
- `guildOwnerName`
- `guildWorldName`
- `guildPortalBounds`
- `guildWorldPortalBounds`
- `owned`
- `forSale`
- `price`
- `guildTreasuryBalance`
- `guildWorldBorderLevel`
- `guildManageRole`

### `MobSpawnInfo`

Read-only snapshot of a configured mob spawn.

Useful fields:
- `zoneId`
- `id`
- `location`
- `entityType`
- `customMobId`
- `displayName`
- `aggroMode`
- `enabled`
- `respawnDelaySeconds`
- `maxHealth`
- `aggroRadius`
- `homeRadius`
- `chaseRadius`
- `rewardMin`
- `rewardMax`

### `ManagedMobInfo`

Read-only runtime view for a live managed mob.

Fields:
- `entity`
- `zoneId`
- `spawnId`
- `returning`
- `currentAggroPlayerId`

## Managed Mob Control

For runtime control, use `ManagedMobHandle`.

### `ManagedMobHandle` Methods

- `entity()` returns the live `LivingEntity`.
- `zoneId()` returns the owning zone id.
- `spawnId()` returns the configured mob spawn id.
- `isReturning()` tells whether the mob is in forced return state.
- `currentTarget()` returns the current player target if there is one.
- `forceReturn()` sends the mob back to its home area.
- `clearTarget()` clears the current target and navigation.
- `setTarget(player)` sets a player target and starts aggro navigation.
- `suppressDefaultBehavior(ticks)` temporarily pauses the default `Zones` AI logic.
- `isDefaultBehaviorSuppressed()` checks whether default behavior is currently paused.
- `setCooldown(key, ticks)` stores a cooldown for your plugin ability.
- `hasCooldown(key)` checks whether a cooldown is active.
- `getCooldownRemainingTicks(key)` reads the remaining cooldown.
- `tryUseCooldown(key, ticks)` helper that only starts a cooldown if none is active.
- `attachedAbilities()` lists all currently attached ability keys for this spawn.

## Managed Mob Abilities

Use `ManagedMobAbility` to react to mob lifecycle and combat events.

Supported callbacks:
- `onSpawn`
- `onTick`
- `onDamagedByPlayer`
- `onAttack`
- `onTargetAcquired`
- `onTargetCleared`
- `onReturnStart`
- `onReturnFinish`
- `onDeath`

### Example: Simple Fire Ability

```java
import me.M1ran.zones.api.ManagedMobAbility;
import me.M1ran.zones.api.ManagedMobHandle;
import me.M1ran.zones.api.ZonesApi;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class DemoPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        ZonesApi api = Bukkit.getServicesManager().load(ZonesApi.class);
        if (api == null) {
            getLogger().warning("ZonesAPI not found.");
            return;
        }

        NamespacedKey key = new NamespacedKey(this, "fire_burst");
        api.registerManagedMobAbility(key, new ManagedMobAbility() {
            @Override
            public void onTick(ManagedMobHandle handle) {
                handle.currentTarget().ifPresent(target -> {
                    if (!handle.tryUseCooldown(key, 60L)) {
                        return;
                    }
                    target.setFireTicks(60);
                });
            }
        });

        api.attachManagedMobAbility("wild_zone", "boss_spawn", key);
    }
}
```

### Example: Force A Return When Low HP

```java
api.registerManagedMobAbility(key, new ManagedMobAbility() {
    @Override
    public void onTick(ManagedMobHandle handle) {
        double health = handle.entity().getHealth();
        double max = handle.entity().getMaxHealth();
        if (health <= max * 0.2) {
            handle.forceReturn();
            handle.suppressDefaultBehavior(40L);
        }
    }
});
```

### Example: Read-Only Mob Identification

```java
@EventHandler
public void onDamage(EntityDamageByEntityEvent event) {
    ZonesApi api = Bukkit.getServicesManager().load(ZonesApi.class);
    if (api == null) {
        return;
    }

    api.getManagedMob(event.getEntity()).ifPresent(info -> {
        Bukkit.getLogger().info("Managed mob from zone " + info.zoneId() + ", spawn " + info.spawnId());
    });
}
```

## Notes And Limits

- This API is designed to expose stable extension points, not the full private runtime.
- `ManagedMobAbility` extends behavior through hooks. It does not fully replace the internal AI by default.
- `suppressDefaultBehavior(ticks)` is the current safe way to temporarily take control during a custom action.
- The API does not expose internal collections, Citizens runtime internals, or private plugin services.
- Coordinates and live entities depend on the server world state and can become invalid if entities die or unload.

## Publishing

This module is intended to be published separately from the main private plugin implementation.

Recommended setup:
- public repository: API only
- private repository: full `Zones` plugin

## Export From Main Plugin Repository

If you maintain the main plugin and this public API side by side, refresh this folder with:

```powershell
powershell -ExecutionPolicy Bypass -File .\export-api.ps1
```
