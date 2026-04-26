package me.M1ran.zones.api;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface ManagedMobAbility {
    default void onSpawn(ManagedMobHandle handle) {
    }

    default void onTick(ManagedMobHandle handle) {
    }

    default void onDamagedByPlayer(ManagedMobHandle handle, Player player, double damage) {
    }

    default void onAttack(ManagedMobHandle handle, Entity target, double damage) {
    }

    default void onTargetAcquired(ManagedMobHandle handle, Player player) {
    }

    default void onTargetCleared(ManagedMobHandle handle) {
    }

    default void onReturnStart(ManagedMobHandle handle) {
    }

    default void onReturnFinish(ManagedMobHandle handle) {
    }

    default void onDeath(ManagedMobHandle handle, Player killer) {
    }
}
