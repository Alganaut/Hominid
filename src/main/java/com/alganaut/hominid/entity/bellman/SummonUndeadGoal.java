package com.alganaut.hominid.entity.bellman;

import com.alganaut.hominid.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

class SummonUndeadGoal extends Goal {
    private final Bellman bellman;

    public SummonUndeadGoal(Bellman bellman) {
        this.bellman = bellman;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return bellman.summonCooldown <= 0 && bellman.getTarget() != null;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        if (!(bellman.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        EntityType<?>[] entityPool = Config.getBellmanSummons();
        if (entityPool.length == 0) {
            return;
        }

        RandomSource random = bellman.getRandom();
        EntityType<?> entityType = entityPool[random.nextInt(entityPool.length)];
        Entity entity = entityType.create(serverLevel);
        if (entity == null) {
            return;
        }

        BlockPos summonPos = bellman.blockPosition().offset(
                random.nextInt(5) - 2,
                0,
                random.nextInt(5) - 2
        );
        entity.moveTo(
                summonPos.getX(), summonPos.getY(), summonPos.getZ(),
                bellman.getYRot(), 0
        );

        if (entity instanceof Mob mob) {
            mob.finalizeSpawn(
                    serverLevel,
                    serverLevel.getCurrentDifficultyAt(summonPos),
                    MobSpawnType.MOB_SUMMONED,
                    null
            );
            mob.getPersistentData().putBoolean("BellmanSummon", true);
        }

        serverLevel.addFreshEntity(entity);
        bellman.playSound(SoundEvents.BELL_BLOCK);
        serverLevel.broadcastEntityEvent(bellman, Bellman.ATTACK_ANIMATION_EVENT);
        bellman.summonCooldown = 600;
    }
}
