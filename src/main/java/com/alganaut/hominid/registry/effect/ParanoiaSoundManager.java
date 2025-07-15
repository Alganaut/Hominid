package com.alganaut.hominid.registry.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ParanoiaSoundManager {
    private static final Set<UUID> activeParanoiaPlayers = new HashSet<>();

    public static void tryPlayParanoiaSound(Player player) {
        UUID uuid = player.getUUID();

        if (!activeParanoiaPlayers.contains(uuid)) {
            Minecraft.getInstance().getSoundManager().play(new ParanoiaLoopSound(player));
            activeParanoiaPlayers.add(uuid);
        }
    }

    public static void unregister(Player player) {
        activeParanoiaPlayers.remove(player.getUUID());
    }
}
