package com.alganaut.hominid.registry.event;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.HominidEntities;
import com.alganaut.hominid.registry.entity.custom.Mellified;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = Hominid.MODID, bus = EventBusSubscriber.Bus.MOD)
public class HominidEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(HominidEntities.MELLIFIED.get(), Mellified.createAttributes().build());
    }
}

