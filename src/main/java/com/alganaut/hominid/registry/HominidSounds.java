package com.alganaut.hominid.registry;

import com.alganaut.hominid.Hominid;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class HominidSounds {

    public static final RegistryEntry<SoundEvent,?> HEMATOMA_SOUND = registerSound("hematoma");
    public static final RegistryEntry<SoundEvent,?> VAMPIRE_IDLE = registerSound("vampire_idle");
    public static final RegistryEntry<SoundEvent,?> VAMPIRE_HURT = registerSound("vampire_hurt");
    public static final RegistryEntry<SoundEvent,?> VAMPIRE_DEATH = registerSound("vampire_death");
    public static final RegistryEntry<SoundEvent,?> VAMPIRE_SCREAM = registerSound("vampire_scream");
    public static final RegistryEntry<SoundEvent,?> PARANOIA = registerSound("paranoia");

    public static final ResourceKey<JukeboxSong> HEMATOMA_KEY =  ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "hematoma"));

    private static RegistryEntry<SoundEvent,?> registerSound(String name) {
        return Hominid.REGISTRATE.get().simple(name, Registries.SOUND_EVENT, () -> SoundEvent.createVariableRangeEvent(Hominid.id(name)));
    }

    // Loads this class so we can registrate it.
    public static void register() {
    }
}
