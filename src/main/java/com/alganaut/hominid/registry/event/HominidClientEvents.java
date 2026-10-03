package com.alganaut.hominid.registry.event;

import com.alganaut.hominid.entity.bellman.Bellman;
import com.alganaut.hominid.entity.juggernaut.Juggernaut;
import com.alganaut.hominid.entity.vampire.Vampire;
import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

public class HominidClientEvents {
    private HominidClientEvents() {}

    public static void register() {
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, HominidClientEvents::onEntityJoinWorld);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, HominidClientEvents::onEntityDie);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, HominidClientEvents::onLivingDrops);
    }

    private static final SimpleWeightedRandomList<String> REPLACEMENTS =
            SimpleWeightedRandomList.<String>builder()
                    .add("zombie", 80)
                    .add("juggernaut", 10)
                    .add("bellman", 10)
                    .build();


    @SubscribeEvent
    public static void onEntityJoinWorld(FinalizeSpawnEvent event) {
        if (event.getEntity() instanceof AbstractIllager illager) {
            illager.targetSelector.addGoal(3, new AvoidEntityGoal<>(illager, Vampire.class, 6.0F, 1.0D, 1.2D));
        }
        if (event.isSpawnCancelled() || event.getEntity().getClass() != Zombie.class) {
            return;
        }
        var level = event.getLevel().getLevel();
        var random = event.getEntity().getRandom();
        // No I did not overcomplicate this
        REPLACEMENTS.getRandomValue(random).ifPresent(choice -> {
            switch (choice) {
                // leave the zombie alone
                case "juggernaut" -> spawn(event, new Juggernaut(level));
                case "bellman" -> spawn(event, new Bellman(level));
            }
        });
    }

    private static void spawn(FinalizeSpawnEvent event, Mob spawn) {
        Mob mob = event.getEntity();
        event.getEntity().discard();

        spawn.setPos(mob.position().x, mob.position().y, mob.position().z);
        mob.level().addFreshEntity(spawn);

    }

    private static void onLivingDrops(LivingDropsEvent event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }

        if (event.getEntity().getPersistentData().getBoolean("BellmanSummon")) {
            event.getDrops().clear();
        }
    }

    @SubscribeEvent
    public static void onEntityDie(LivingDeathEvent event){
        if (event.getEntity().level().isClientSide) {
            return;
        }

        LivingEntity deadEntity = event.getEntity();

        if (!(deadEntity instanceof Creeper creeper)) {
            return;
        }

        LivingEntity killer = deadEntity.getLastAttacker();

        if (killer instanceof Vampire) {

            Level level = deadEntity.level();

            ItemStack drop = new ItemStack(HominidItems.MUSIC_DISC_HEMATOMA.get());

            ItemEntity itemEntity = new ItemEntity(
                    level,
                    creeper.getX(),
                    creeper.getY(),
                    creeper.getZ(),
                    drop
            );

            itemEntity.setDefaultPickUpDelay();
            level.addFreshEntity(itemEntity);
        }
    }

}
