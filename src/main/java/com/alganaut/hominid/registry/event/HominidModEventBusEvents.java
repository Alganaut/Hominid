package com.alganaut.hominid.registry.event;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.misc.HominidBuiltinPacks;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@EventBusSubscriber(modid = Hominid.MODID)
public class HominidModEventBusEvents {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        PackRepository repository = Minecraft.getInstance().getResourcePackRepository();

        repository.addPackFinder(new ModPackFinder());
    }

    @SubscribeEvent
    public static void addBuiltinPacks(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) { // Resource Packs
            HominidBuiltinPacks.rpHominidRetextures(event);
        }
    }
}
