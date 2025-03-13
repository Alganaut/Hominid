package com.alganaut.hominid.registry.event;

import com.alganaut.hominid.registry.effect.HominidEffects;
import com.alganaut.hominid.registry.entity.HominidEntityCreator;
import com.alganaut.hominid.registry.entity.custom.Incendiary;
import com.alganaut.hominid.registry.entity.custom.Juggernaut;
import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;

import java.util.List;

import static com.alganaut.hominid.Hominid.MODID;

public class HominidClientEvents {
    public static void register() {
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, HominidClientEvents::onPlayerDrinkHoney);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, HominidClientEvents::onExplosionDetonate);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, HominidClientEvents::onEntityJoinWorld);
    }

    @SubscribeEvent
    public static void onPlayerDrinkHoney(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack itemStack = event.getItem();
            Level world = player.level();

            if (!world.isClientSide && itemStack.getItem() == Items.HONEY_BOTTLE) {
                player.addEffect(new MobEffectInstance(Holder.direct(HominidEffects.HONEYED.get()), 400, 0));
            }
        }
    }

    @SubscribeEvent
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        if (event.getExplosion().getDirectSourceEntity() instanceof Creeper creeper) {
            Level world = event.getExplosion().getDirectSourceEntity().level();
            Vec3 explosionPos = event.getExplosion().getDirectSourceEntity().position();

            if (!world.isClientSide) {
                for (Incendiary incendiary : world.getEntitiesOfClass(Incendiary.class, creeper.getBoundingBox().inflate(30))) {
                    if (incendiary.ignitedCreepers.contains(creeper.getUUID())) {
                        incendiary.ignitedCreepers.remove(creeper.getUUID());

                        ItemStack droppedItem = new ItemStack(HominidItems.CHARRED_MUSIC_DISC.get(), 1);
                        ItemEntity itemEntity = new ItemEntity((ServerLevel) world, explosionPos.x, explosionPos.y, explosionPos.z, droppedItem);
                        world.addFreshEntity(itemEntity);
                        break;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(FinalizeSpawnEvent event) {
        if (event.getEntity() instanceof Zombie && Math.random() < 0.1) {
            Zombie wolf = (Zombie) event.getEntity();
            event.getEntity().discard();

            Juggernaut customMob = new Juggernaut(HominidEntityCreator.JUGGERNAUT.get(), wolf.level());
            customMob.setPos(wolf.position().x, wolf.position().y, wolf.position().z);
            wolf.level().addFreshEntity(customMob);
        }
    }
}
