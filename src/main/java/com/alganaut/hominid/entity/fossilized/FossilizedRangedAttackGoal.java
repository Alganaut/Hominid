package com.alganaut.hominid.entity.fossilized;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

class FossilizedRangedAttackGoal extends Goal {
    private final Fossilized fossilized;
    private int attackCooldown;
    private int animationTimer;

    public FossilizedRangedAttackGoal(Fossilized fossilized) {
        this.fossilized = fossilized;
        this.attackCooldown = 0;
        this.animationTimer = 0;
        this.fossilized.attackState = Fossilized.AttackState.IDLE;
    }

    @Override
    public boolean canUse() {
        return fossilized.getTarget() != null;
    }

    @Override
    public void tick() {
        LivingEntity target = fossilized.getTarget();
        if (target == null) {
            return;
        }
        fossilized.getLookControl().setLookAt(target, 30.0F, 30.0F);

        if(tickAttackTimer()) {
            return;
        }

        switch (fossilized.attackState) {
            case IDLE:
                idle();
                break;

            case PREPARING:
                prepare(target);
                break;

            case ANIMATING:
                animate(target);
                break;
        }
    }


    private boolean tickAttackTimer() {
        if (attackCooldown <= 0) {
           return false;
        }

        attackCooldown--;
        return true;
    }

    private boolean tickAnimationTimer() {
        if (animationTimer <= 0) {
            return false;
        }

        animationTimer--;
        return true;
    }

    private void idle() {
        fossilized.attackState = Fossilized.AttackState.PREPARING;
        animationTimer = 18;
    }

    private void prepare(LivingEntity target) {
        if (tickAnimationTimer()) {
            return;
        }
        fossilized.attackState = Fossilized.AttackState.ANIMATING;
        animationTimer = 18;
        if (!fossilized.level().isClientSide) {
            fossilized.level().broadcastEntityEvent(fossilized, Fossilized.THROW_ANIMATION_EVENT);
        }
        fossilized.getNavigation().stop();
        fossilized.getLookControl().setLookAt(target, 30.0F, 30.0F);
    }

    private void animate(LivingEntity target) {
        if (tickAnimationTimer()) {
            return;
        }
        fossilized.attackState = Fossilized.AttackState.THROWING;
        fossilized.getLookControl().setLookAt(target, 30.0F, 30.0F);
        fossilized.getNavigation().stop();

        if (!fossilized.level().isClientSide) {
            fossilized.level().broadcastEntityEvent(fossilized, Fossilized.STOP_THROW_ANIMATION_EVENT);
        }

        throwRock(target);
        attackCooldown = 20;
        fossilized.attackState = Fossilized.AttackState.IDLE;
    }

    private void throwRock(LivingEntity target) {
        Level level = fossilized.level();
        FossilizedRock rock = new FossilizedRock(fossilized.level(), fossilized);

        double dx = target.getX() - fossilized.getX();
        double dy = target.getY() - fossilized.getEyeY() + 0.5;
        double dz = target.getZ() - fossilized.getZ();

        rock.setPos(fossilized.getX(), fossilized.getEyeY(), fossilized.getZ());
        rock.setOwner(fossilized);
        rock.shoot(dx, dy, dz, 2.5F, 0.2F);

        rock.setInvisible(false);

        if (!level.isClientSide()) {
            level.addFreshEntity(rock);
        }
    }
}
