package com.alganaut.hominid.registry.event;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.HominidEntityCreator;
import com.alganaut.hominid.registry.entity.custom.Fossilised;
import com.alganaut.hominid.registry.entity.custom.Incendiary;
import com.alganaut.hominid.registry.misc.HominidBuiltinPacks;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;

import java.util.List;

import static com.alganaut.hominid.Hominid.MODID;

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
        event.register(HominidEntityCreator.FOSSILISED.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING,
                Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(HominidEntityCreator.VAMPIRE.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING,
                Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        PackRepository repository = Minecraft.getInstance().getResourcePackRepository();

        repository.addPackFinder(new ModPackFinder(MODID));
    }

    @SubscribeEvent
    public static void addBuiltinPacks(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) { // Resource Packs
            HominidBuiltinPacks.rpHominidRetextures(event);
            HominidBuiltinPacks.rpHominidZombieVariation(event);
        }
        if (event.getPackType() == PackType.SERVER_DATA) { // Datapacks

        }
    }
}
