package com.alganaut.hominid.registry.event;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.HominidEntityCreator;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = Hominid.MODID, bus = EventBusSubscriber.Bus.MOD)
public class HominidModEventBusEvents {
    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(HominidEntityCreator.MELLIFIED.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(HominidEntityCreator.INCENDIARY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(HominidEntityCreator.FAMISHED.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
    }
}
