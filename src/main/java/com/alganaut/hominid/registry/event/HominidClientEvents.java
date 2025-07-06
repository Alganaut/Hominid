package com.alganaut.hominid.registry.event;

import com.alganaut.hominid.registry.effect.HominidEffects;
import com.alganaut.hominid.registry.effect.renderer.ParanoiaOverlayRenderer;
import com.alganaut.hominid.registry.entity.HominidEntityCreator;
import com.alganaut.hominid.registry.entity.custom.*;
import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HominidClientEvents {
    private static final Map<UUID, Float> lastYawMap = new HashMap<>();
    private static final Map<UUID, Float> lastPitchMap = new HashMap<>();
    private static final Map<UUID, Integer> soundTimerMap = new HashMap<>();

    public static void register() {
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, HominidClientEvents::onPlayerDrinkHoney);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, HominidClientEvents::onExplosionDetonate);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, HominidClientEvents::onEntityJoinWorld);
        //NeoForge.EVENT_BUS.addListener(EventPriority.LOW, HominidClientEvents::onClientTick);
    }

    @SubscribeEvent
    public static void onPlayerDrinkHoney(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack itemStack = event.getItem();
            Level world = player.level();

            if (!world.isClientSide && itemStack.getItem() == Items.HONEY_BOTTLE) {
                player.addEffect(new MobEffectInstance(HominidEffects.HONEYED, 400, 0));
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

                        ItemStack droppedItem = new ItemStack(HominidItems.MUSIC_DISC_CHARRED.get(), 1);
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
        if (event.getEntity().getClass() == Zombie.class && Math.random() < 0.1) {
            Zombie wolf = (Zombie) event.getEntity();
            event.getEntity().discard();

            Juggernaut customMob = new Juggernaut(HominidEntityCreator.JUGGERNAUT.get(), wolf.level());
            customMob.setPos(wolf.position().x, wolf.position().y, wolf.position().z);
            wolf.level().addFreshEntity(customMob);
        }
        if (event.getEntity() != null && event.getEntity() instanceof AbstractIllager) {
            AbstractIllager illager = (AbstractIllager) event.getEntity();
            illager.targetSelector.addGoal(3, new AvoidEntityGoal<>(illager, Vampire.class, 6.0F, 1.0D, 1.2D));
        }
    }

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiLayerEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (player != null && player.hasEffect(HominidEffects.PARANOIA)) {
            ParanoiaOverlayRenderer.renderTextureOverlay(event.getGuiGraphics(), ParanoiaOverlayRenderer.PARANOIA_OVERLAY, 0.5F);
        }
    }

//    @SubscribeEvent
//    public static void onClientTick(ClientTickEvent.Pre event) {
//        Minecraft mc = Minecraft.getInstance();
//        if (mc.player == null || mc.level == null) return;
//
//        Player player = mc.player;
//
//        if (!player.hasEffect(HominidEffects.PARANOIA)) return;
//
//        UUID uuid = player.getUUID();
//        float currentYaw = player.getYRot();
//        float currentPitch = player.getXRot();
//
//        float lastYaw = lastYawMap.getOrDefault(uuid, currentYaw);
//        float lastPitch = lastPitchMap.getOrDefault(uuid, currentPitch);
//
//        boolean isCameraStill = Math.abs(currentYaw - lastYaw) < 0.1F && Math.abs(currentPitch - lastPitch) < 0.1F;
//
//        int timer = soundTimerMap.getOrDefault(uuid, 0);
//
//        if (isCameraStill) {
//            if (timer <= 0) {
//                player.playSound(SoundEvents.GRASS_STEP, 0.5F, 1.0F);
//                timer = 30;
//            } else {
//                timer--;
//            }
//        } else {
//            timer = 0;
//        }
//
//        lastYawMap.put(uuid, currentYaw);
//        lastPitchMap.put(uuid, currentPitch);
//        soundTimerMap.put(uuid, timer);
//    }

}
