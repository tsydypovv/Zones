package me.M1ran.zones.api;

public record ZoneInfo(String id, ZoneKind type, Bounds bounds, boolean pvpEnabled) {
}
