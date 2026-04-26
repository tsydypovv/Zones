package me.M1ran.zones.api;

import org.bukkit.entity.LivingEntity;

import java.util.UUID;

public record ManagedMobInfo(
        LivingEntity entity,
        String zoneId,
        String spawnId,
        boolean returning,
        UUID currentAggroPlayerId
) {
}
