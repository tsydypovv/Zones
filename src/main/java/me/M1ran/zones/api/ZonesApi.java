package me.M1ran.zones.api;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;

public interface ZonesApi {
    Collection<ZoneInfo> getZones();

    Optional<ZoneInfo> getZone(String zoneId);

    Optional<ZoneInfo> findZone(Location location);

    Collection<PlotInfo> getPlots(String zoneId);

    Optional<PlotInfo> findPlot(Location location);

    Optional<PlotInfo> findGuildWorldPlot(Location location);

    Optional<MobSpawnInfo> getMobSpawn(String zoneId, String spawnId);

    Collection<MobSpawnInfo> getMobSpawns(String zoneId);

    boolean canEnter(Player player, Location location);

    boolean canBuild(Player player, Location location);

    boolean canInteract(Player player, Location location);

    boolean canUseContainers(Player player, Location location);

    boolean canPvp(Player player, Location location);

    boolean isManagedMob(Entity entity);

    Optional<ManagedMobInfo> getManagedMob(Entity entity);

    Optional<ManagedMobHandle> getManagedMobHandle(Entity entity);

    boolean registerManagedMobAbility(NamespacedKey key, ManagedMobAbility ability);

    void unregisterManagedMobAbility(NamespacedKey key);

    boolean attachManagedMobAbility(String zoneId, String spawnId, NamespacedKey key);

    boolean detachManagedMobAbility(String zoneId, String spawnId, NamespacedKey key);

    Collection<NamespacedKey> getAttachedManagedMobAbilities(String zoneId, String spawnId);
}
