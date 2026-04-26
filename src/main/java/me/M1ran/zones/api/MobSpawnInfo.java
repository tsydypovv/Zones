package me.M1ran.zones.api;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public record MobSpawnInfo(
        String zoneId,
        String id,
        Location location,
        EntityType entityType,
        String customMobId,
        String displayName,
        MobAggroKind aggroMode,
        boolean enabled,
        int respawnDelaySeconds,
        double maxHealth,
        double healthDisplayHeight,
        double aggroRadius,
        double homeRadius,
        double chaseRadius,
        double rewardMin,
        double rewardMax
) {
}
