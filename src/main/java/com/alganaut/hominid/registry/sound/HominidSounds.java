package com.alganaut.hominid.registry.sound;

import com.alganaut.hominid.Hominid;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class HominidSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Hominid.MODID);

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Hominid.MODID, name);
        return SOUND_EVENTS.register(name, () ->SoundEvent.createVariableRangeEvent(id));
    }

    public static final Supplier<SoundEvent> CHARRED = registerSoundEvent("charred");
    public static final ResourceKey<JukeboxSong> CHARRED_KEY = createSong("charred");

    public static final Supplier<SoundEvent> VAMPIRE_IDLE = registerSoundEvent("vampire_idle");
    public static final Supplier<SoundEvent> VAMPIRE_HURT = registerSoundEvent("vampire_hurt");
    public static final Supplier<SoundEvent> VAMPIRE_DEATH = registerSoundEvent("vampire_death");

    private static ResourceKey<JukeboxSong> createSong (String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(Hominid.MODID, name));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

    public static DeferredHolder<SoundEvent, SoundEvent> createSound(String name) {
        return SOUND_EVENTS.register(
                name,
                () -> SoundEvent.createVariableRangeEvent(Hominid.id(name))
        );
    }
}
