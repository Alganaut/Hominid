package com.alganaut.hominid.registry.effect;

import com.alganaut.hominid.registry.sound.HominidSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.sounds.SoundSource;

public class ParanoiaLoopSound extends AbstractTickableSoundInstance {
    private final Player player;

    public ParanoiaLoopSound(Player player) {
        super(HominidSounds.PARANOIA.get(), SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
        this.player = player;
        this.looping = true;
        this.volume = 1F;
        this.pitch = 1.0F;
        this.delay = 0;
        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }

    @Override
    public void tick() {
        if (!player.isAlive() || !player.hasEffect(HominidEffects.PARANOIA)) {
            this.stop();
            ParanoiaSoundManager.unregister(player);
        } else {
            this.x = player.getX();
            this.y = player.getY();
            this.z = player.getZ();
        }
    }
}
