package com.alganaut.hominid.entity.fossilized;

import com.alganaut.hominid.entity.animation.IdleAnimationController;
import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;

public class Fossilized extends Monster {
    static final byte THROW_ANIMATION_EVENT = 70;
    static final byte STOP_THROW_ANIMATION_EVENT = 90;
    public enum AttackState {
        IDLE,
        PREPARING,
        ANIMATING,
        THROWING
    }

    private final IdleAnimationController idleAnimationController = new IdleAnimationController(120);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState throwAnimationState = new AnimationState();
    public AttackState attackState = AttackState.IDLE;
    private static final EntityDataAccessor<Boolean> HAS_BEEN_BRUSHED = SynchedEntityData.defineId(Fossilized.class, EntityDataSerializers.BOOLEAN);

    public Fossilized(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 35.0)
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 4.0);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.STONE_PLACE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.STONE_FALL;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.STONE_BREAK;
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new FossilizedRangedAttackGoal(this));
        goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        if (level().isClientSide()) {
            idleAnimationController.tick(this, idleAnimationState);
        }
        super.tick();
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    public boolean isInvertedHealAndHarm() {
        return true;
    }

    @Override
    public void handleEntityEvent(byte state) {
        if (state == THROW_ANIMATION_EVENT) {
            throwAnimationState.stop();
            idleAnimationState.stop();
            throwAnimationState.startIfStopped(tickCount);
        }
        if (state == STOP_THROW_ANIMATION_EVENT) {
            throwAnimationState.stop();
        }
        super.handleEntityEvent(state);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HAS_BEEN_BRUSHED, false);
    }
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!itemstack.canPerformAction(ItemAbilities.BRUSH_BRUSH)) {
            return super.mobInteract(player, hand);
        }
        if (entityData.get(HAS_BEEN_BRUSHED)) {
            return InteractionResult.PASS;
        }
        brushOffScute(itemstack, player, hand);
        return InteractionResult.sidedSuccess(level().isClientSide);


    }

    private void brushOffScute(ItemStack itemstack, Player player, InteractionHand hand) {
        spawnAtLocation(new ItemStack(HominidItems.REMAINS_SMITHING_TEMPLATE.get()));
        gameEvent(GameEvent.ENTITY_INTERACT);
        playSound(SoundEvents.BRUSH_GENERIC);
        itemstack.hurtAndBreak(32, player, getSlotForHand(hand));
        entityData.set(HAS_BEEN_BRUSHED, true);
    }

}
