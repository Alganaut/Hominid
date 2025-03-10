package com.alganaut.hominid.registry.entity.custom;
import net.minecraft.advancements.critereon.TameAnimalTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.level.NoteBlockEvent;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class Famished extends Monster {
    public final AnimationState eatingAnimationState = new AnimationState();
    private boolean isEating = false;
    public final AnimationState idleAnimationState = new AnimationState();
    private int cooldownTicks = 0;

    public Famished(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.MAX_HEALTH, 8.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 5.0);
    }

    public void aiStep() {
        if (this.isAlive()) {
            boolean flag = this.isSunSensitive() && this.isSunBurnTick();
            if (flag) {
                ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
                if (!itemstack.isEmpty()) {
                    if (itemstack.isDamageableItem()) {
                        Item item = itemstack.getItem();
                        itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
                        if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                            this.onEquippedItemBroken(item, EquipmentSlot.HEAD);
                            this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }

                    flag = false;
                }

                if (flag) {
                    this.igniteForSeconds(8.0F);
                }
            }
        }

        super.aiStep();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new HuntAnimalGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        if (cooldownTicks > 0) {
            cooldownTicks--;
        }
        if (isEating) {
            if (!eatingAnimationState.isStarted()) {
                eatingAnimationState.start(this.tickCount);
            }
        }
        if(this.getTarget() != null){
            if(this.getTarget() instanceof Player){
                if(this.getTarget().getMainHandItem() != null && this.getTarget().getMainHandItem().is(ItemTags.MEAT)){
                    this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.27);
                }else{
                    this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2);
                }
            }
        }
    }

    protected boolean isSunSensitive() {
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.HUSK_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.HUSK_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.HUSK_DEATH;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
        fallDistance = 0;
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    public boolean isInvertedHealAndHarm() {
        return true;
    }

    public void startEating(LivingEntity target) {
        if (!isEating && cooldownTicks == 0) {
            isEating = true;
            cooldownTicks = 50;
            eatingAnimationState.start(this.tickCount);
            this.level().broadcastEntityEvent(this, (byte) 10);
            if (!this.level().isClientSide()) {
                ServerLevel serverLevel = (ServerLevel) this.level();

                serverLevel.getServer().execute(() -> {
                    if (target.isAlive() && !(target instanceof SkeletonHorse)) {
                        ItemParticleOption particleType;
                        if (target instanceof Horse) {
                            transformEntity(target, EntityType.ZOMBIE_HORSE);
                            particleType = new ItemParticleOption(ParticleTypes.ITEM, Items.BEEF.getDefaultInstance());
                        } else if (target instanceof ZombieHorse) {
                            transformEntity(target, EntityType.SKELETON_HORSE);
                            particleType = new ItemParticleOption(ParticleTypes.ITEM, Items.ROTTEN_FLESH.getDefaultInstance());
                        } else {
                            particleType = new ItemParticleOption(ParticleTypes.ITEM, Items.BEEF.getDefaultInstance());
                        }
                        this.playSound(SoundEvents.GENERIC_EAT);
                        spawnParticles(target, particleType);
                        target.discard();
                    }
                    isEating = false;
                });
            }
        }
    }

    private void transformEntity(LivingEntity oldEntity, EntityType<? extends LivingEntity> newType) {
        ServerLevel serverLevel = (ServerLevel) oldEntity.level();
        LivingEntity newEntity = newType.create(serverLevel);

        if (newEntity != null) {
            newEntity.moveTo(oldEntity.getX(), oldEntity.getY(), oldEntity.getZ(), oldEntity.getYRot(), oldEntity.getXRot());
            newEntity.setCustomName(oldEntity.getCustomName());
            newEntity.setHealth(oldEntity.getHealth());
            serverLevel.addFreshEntity(newEntity);
            if(oldEntity != null){
                oldEntity.discard();
            }
        }
    }

    private void spawnParticles(LivingEntity entity, ItemParticleOption particleType) {
        ServerLevel serverLevel = (ServerLevel) entity.level();

        for (int i = 0; i < 40; i++) {
            double offsetX = (this.random.nextDouble() - 0.5) * 0.8;
            double offsetY = this.random.nextDouble() * 0.5 + 0.5;
            double offsetZ = (this.random.nextDouble() - 0.5) * 0.8;
            double velocityX = (this.random.nextDouble() - 0.5) * 0.2;
            double velocityY = this.random.nextDouble() * 0.3 + 0.2;
            double velocityZ = (this.random.nextDouble() - 0.5) * 0.2;

            serverLevel.sendParticles(particleType,
                    entity.getX() + offsetX, entity.getY() + offsetY, entity.getZ() + offsetZ,
                    1, velocityX, velocityY, velocityZ, 0);
        }
    }

    public static class HuntAnimalGoal extends Goal {
        private final Famished famished;
        private LivingEntity target;

        public HuntAnimalGoal(Famished famished) {
            this.famished = famished;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (famished.cooldownTicks > 0) return false;

            List<LivingEntity> animals = this.famished.level().getEntitiesOfClass(LivingEntity.class,
                    this.famished.getBoundingBox().inflate(30), entity -> entity instanceof Animal);

            animals.removeIf(entity -> entity instanceof SkeletonHorse);
            animals.removeIf(entity -> entity instanceof TamableAnimal && ((TamableAnimal) entity).isTame());

            if (!animals.isEmpty()) {
                animals.sort(Comparator.comparingDouble(animal -> this.famished.distanceToSqr(animal)));
                target = animals.get(0);
                return true;
            }
            return false;
        }

        @Override
        public void start() {
            if (target != null) {
                this.famished.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.27);
                this.famished.getNavigation().moveTo(target, this.famished.getAttributeValue(Attributes.MOVEMENT_SPEED));
            }
        }

        @Override
        public boolean canContinueToUse() {
            return target != null && target.isAlive() && this.famished.distanceToSqr(target) > 1.5;
        }

        @Override
        public void tick() {
            if (target != null) {
                this.famished.getNavigation().moveTo(target, 1.5);
                if (this.famished.distanceToSqr(target) <= 1.5) {
                    this.famished.startEating(target);
                }
            }
        }

        @Override
        public void stop() {
            this.famished.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2);
            target = null;
        }
    }
}