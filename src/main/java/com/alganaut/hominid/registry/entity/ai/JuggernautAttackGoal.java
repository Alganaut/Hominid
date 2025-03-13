package com.alganaut.hominid.registry.entity.ai;

import com.alganaut.hominid.registry.entity.custom.Juggernaut;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class JuggernautAttackGoal extends MeleeAttackGoal {
    private final Juggernaut entity;
    private int attackDelay = 35;
    private int ticksUntilNextAttack = 35;
    private boolean shouldCountTillNextAttack = false;

    public JuggernautAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        entity = ((Juggernaut) pMob);
    }

    @Override
    public void start() {
        super.start();
        attackDelay = 30;
        ticksUntilNextAttack = 35;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy) {
        if (this.mob.isWithinMeleeAttackRange(entity)) {
            shouldCountTillNextAttack = true;

            if(isTimeToStartAttackAnimation()) {
                entity.setAttacking(true);
            }

            if(isTimeToAttack()) {
                this.mob.getLookControl().setLookAt(pEnemy.getX(), pEnemy.getEyeY(), pEnemy.getZ());
                performAttack(pEnemy);
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationTimeout = 0;
        }
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(attackDelay * 2);
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected boolean isTimeToStartAttackAnimation() {
        return this.ticksUntilNextAttack <= attackDelay;
    }

    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }


    protected void performAttack(LivingEntity pEnemy) {
        this.resetAttackCooldown();
        this.mob.swing(InteractionHand.MAIN_HAND);

        double attackRange = 2.0D;
        List<LivingEntity> targets = this.mob.level().getEntitiesOfClass(
                LivingEntity.class,
                this.mob.getBoundingBox().inflate(attackRange),
                entity -> entity != this.mob && entity.isAlive()
        );

        for (LivingEntity target : targets) {
            if(target instanceof Player){
                this.mob.doHurtTarget(target);
            }
        }
    }
    @Override
    public void tick() {
        super.tick();
        if(shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }
    }


    @Override
    public void stop() {
        entity.setAttacking(false);
        super.stop();
    }


}
