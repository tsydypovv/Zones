package me.M1ran.zones.api;

import java.util.UUID;

public record PlotInfo(
        String zoneId,
        String id,
        PlotKind type,
        Bounds bounds,
        UUID ownerUuid,
        String guildOwnerName,
        String guildWorldName,
        Bounds guildPortalBounds,
        Bounds guildWorldPortalBounds,
        boolean owned,
        boolean forSale,
        double price,
        double guildTreasuryBalance,
        int guildWorldBorderLevel,
        GuildRoleInfo guildManageRole
) {
    public boolean isGuildOwned() {
        return guildOwnerName != null && !guildOwnerName.isBlank();
    }
}
