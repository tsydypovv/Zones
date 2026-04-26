package me.M1ran.zones.api;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;

public interface ManagedMobHandle {
    LivingEntity entity();

    String zoneId();

    String spawnId();

    boolean isReturning();

    Optional<Player> currentTarget();

    void forceReturn();

    void clearTarget();

    boolean setTarget(Player player);

    void suppressDefaultBehavior(long ticks);

    boolean isDefaultBehaviorSuppressed();

    void setCooldown(NamespacedKey key, long ticks);

    boolean hasCooldown(NamespacedKey key);

    long getCooldownRemainingTicks(NamespacedKey key);

    boolean tryUseCooldown(NamespacedKey key, long ticks);

    Collection<NamespacedKey> attachedAbilities();
}
