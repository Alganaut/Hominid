package com.alganaut.hominid.registry;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.client.entity.bellman.BellmanRenderer;
import com.alganaut.hominid.client.entity.famished.FamishedRenderer;
import com.alganaut.hominid.client.entity.fossilized.FossilizedRenderer;
import com.alganaut.hominid.client.entity.incendiary.IncendiaryRenderer;
import com.alganaut.hominid.client.entity.juggernaut.JuggernautRenderer;
import com.alganaut.hominid.client.entity.mellified.MellifiedRenderer;
import com.alganaut.hominid.client.entity.vampire.VampireRenderer;
import com.alganaut.hominid.entity.bellman.Bellman;
import com.alganaut.hominid.entity.famished.Famished;
import com.alganaut.hominid.entity.fossilized.Fossilized;
import com.alganaut.hominid.entity.fossilized.FossilizedRock;
import com.alganaut.hominid.entity.incendiary.Incendiary;
import com.alganaut.hominid.entity.juggernaut.Juggernaut;
import com.alganaut.hominid.entity.mellified.Mellified;
import com.alganaut.hominid.entity.vampire.Vampire;
import com.alganaut.hominid.registry.misc.HominidEntityFactory;
import com.tterrag.registrate.util.entry.EntityEntry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

public class HominidEntityCreator {

    public static final EntityEntry<Mellified> MELLIFIED = Hominid.REGISTRATE.get()
            .entity("mellified", HominidEntityFactory.make(Mellified.class), Mellified.CATEGORY)
            .properties(Mellified::properties)
            .attributes(Mellified::createAttributes)
            .renderer(() -> MellifiedRenderer::new)
            .spawnPlacement(SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                    Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE)
            .spawnEgg(0xab781e, 0xfdefd0).removeTab(CreativeModeTabs.SPAWN_EGGS).build()
            .register();

    public static final EntityEntry<Incendiary> INCENDIARY = Hominid.REGISTRATE.get()
            .entity("incendiary", HominidEntityFactory.make(Incendiary.class), Incendiary.CATEGORY)
            .properties(Incendiary::properties)
            .attributes(Incendiary::createAttributes)
            .renderer(() -> IncendiaryRenderer::new)
            .spawnPlacement(SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                    Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND)
            .spawnEgg(0x645633, 0x130407).removeTab(CreativeModeTabs.SPAWN_EGGS).build()
            .register();

    public static final EntityEntry<Famished> FAMISHED = Hominid.REGISTRATE.get()
            .entity("famished", HominidEntityFactory.make(Famished.class), Famished.CATEGORY)
            .properties(Famished::properties)
            .attributes(Famished::createAttributes)
            .renderer(() -> FamishedRenderer::new)
            .spawnPlacement(SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                    Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND)
            .spawnEgg(0x88381b, 0xc9a681).removeTab(CreativeModeTabs.SPAWN_EGGS).build()
            .register();

    public static final EntityEntry<Juggernaut> JUGGERNAUT = Hominid.REGISTRATE.get()
            .entity("juggernaut", HominidEntityFactory.make(Juggernaut.class), Juggernaut.CATEGORY)
            .properties(Juggernaut::properties)
            .attributes(Juggernaut::createAttributes)
            .renderer(() -> JuggernautRenderer::new)
            .spawnPlacement(SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                    Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND)
            .spawnEgg(0x673737, 0xb0b4a3).removeTab(CreativeModeTabs.SPAWN_EGGS).build()
            .register();

    public static final EntityEntry<Bellman> BELLMAN = Hominid.REGISTRATE.get()
            .entity("bellman", HominidEntityFactory.make(Bellman.class), Bellman.CATEGORY)
            .properties(Bellman::properties)
            .attributes(Bellman::createAttributes)
            .renderer(() -> BellmanRenderer::new)
            .spawnPlacement(SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                    Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND)
            .spawnEgg(0x554739, 0xb0b4a3).removeTab(CreativeModeTabs.SPAWN_EGGS).build()
            .register();

    public static final EntityEntry<Fossilized> FOSSILIZED = Hominid.REGISTRATE.get()
            .entity("fossilized", HominidEntityFactory.make(Fossilized.class), Fossilized.CATEGORY)
            .properties(Fossilized::properties)
            .attributes(Fossilized::createAttributes)
            .renderer(() -> FossilizedRenderer::new)
            .spawnPlacement(SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING,
                    Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND)
            .spawnEgg(0x7e7a56, 0x5b5344).removeTab(CreativeModeTabs.SPAWN_EGGS).build()
            .register();

    public static final EntityEntry<FossilizedRock> ROCK = Hominid.REGISTRATE.get()
            .<FossilizedRock>entity("rock", FossilizedRock::new, FossilizedRock.CATEGORY)
            .properties(FossilizedRock::properties)
            .renderer(() -> ThrownItemRenderer::new)
            .register();

    public static final EntityEntry<Vampire> VAMPIRE = Hominid.REGISTRATE.get()
            .entity("vampire", HominidEntityFactory.make(Vampire.class), Vampire.CATEGORY)
            .properties(Vampire::properties)
            .attributes(Vampire::createAttributes)
            .renderer(() -> VampireRenderer::new)
            .spawnPlacement(SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING,
                    Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND)
            .spawnEgg(0xccc5ae, 0xdd563f).removeTab(CreativeModeTabs.SPAWN_EGGS).build()
            .register();

    // Loads this class so we can registrate it.
    public static void register() {
    }
}
