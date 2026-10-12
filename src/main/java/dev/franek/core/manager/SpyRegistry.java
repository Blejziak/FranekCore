package dev.franek.core.manager;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lista graczy z wlaczonym podgledem wiadomosci prywatnych (socialspy).
 */
public final class SpyRegistry {

    private static final Set<UUID> WATCHERS = ConcurrentHashMap.newKeySet();

    private SpyRegistry() {
    }

    /** Wlacza/wylacza podglad dla gracza. */
    public static void toggle(UUID playerId) {
        if (!WATCHERS.add(playerId)) {
            WATCHERS.remove(playerId);
        }
    }

    public static boolean isWatching(UUID playerId) {
        return WATCHERS.contains(playerId);
    }
}
