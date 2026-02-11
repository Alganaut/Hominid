package com.alganaut.hominid.registry.entity.custom;

import com.alganaut.hominid.registry.misc.HominidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Bellman extends Monster {
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;

    private int summonCooldown;

    public Bellman(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.ATTACK_DAMAGE, 6.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SummonUndeadGoal(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new Bellman.ZombieAttackTurtleEggGoal(this, 1.0, 3));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
    }
    protected boolean isSunSensitive() {
        return true;
    }

    private void setupAnimationStates() {
        if (this.getDeltaMovement().horizontalDistance() <= 0.001F) {
            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = 250;
                this.idleAnimationState.start(this.tickCount);
            } else {
                --this.idleAnimationTimeout;
            }
        } else {
            this.idleAnimationTimeout = 0;
            this.idleAnimationState.stop();
        }
    }
    @Override
    public void tick() {
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
        if(summonCooldown >= -100){
            summonCooldown--;
        }
        super.tick();
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


    static class SummonUndeadGoal extends Goal {
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
            if (!bellman.level().isClientSide) {
                bellman.level().broadcastEntityEvent(bellman, (byte) 60);
                ServerLevel serverLevel = (ServerLevel) bellman.level();
                RandomSource random = bellman.getRandom();
                var tagOptional = BuiltInRegistries.ENTITY_TYPE.getTag(HominidTags.EntityType.BELLMAN_SPAWNABLE);
                net.minecraft.world.entity.EntityType<?>[] entityPool;
                if (tagOptional.isPresent()) {
                    entityPool = tagOptional.get().stream()
                            .map(Holder::value)
                            .toArray(net.minecraft.world.entity.EntityType<?>[]::new);
                } else {
                    entityPool = new net.minecraft.world.entity.EntityType<?>[]{
                            EntityType.ZOMBIE,
                            EntityType.HUSK,
                            EntityType.DROWNED,
                            EntityType.SKELETON,
                            EntityType.STRAY,
                            EntityType.BOGGED,
                            BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath("hominid", "incendiary")),
                            BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath("hominid", "mellified")),
                            BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath("hominid", "famished")),
                            BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath("hominid", "juggernaut")),
                            BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath("hominid", "fossilized")),
                            BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath("hominid", "vampire")),
                            BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath("galosphere", "preserved"))
                    };
                }

                for (int i = 0; i < 3; i++) {
                    int rand = random.nextInt(entityPool.length);
                    net.minecraft.world.entity.EntityType<?> entityToSpawn = entityPool[rand];
                    Entity entity = entityToSpawn.create(serverLevel);
                    BlockPos summonPos = bellman.blockPosition().offset(random.nextInt(5) - 2, 0, random.nextInt(5) - 2);
                    entity.moveTo(summonPos.getX(), summonPos.getY(), summonPos.getZ(), bellman.getYRot(), 0);
                    if (entity instanceof Mob mob) {
                        mob.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(summonPos),
                                MobSpawnType.MOB_SUMMONED, null);
                    }

                    serverLevel.addFreshEntity(entity);
                }

                bellman.summonCooldown = 600;
            }
        }
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }
    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    public boolean isInvertedHealAndHarm() {
        return true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
    }

    @Override
    public void handleEntityEvent(byte state) {
        if (state == 60){
            this.attackAnimationState.stop();
            this.attackAnimationState.startIfStopped(this.tickCount);
        }
        else super.handleEntityEvent(state);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if(!level().isClientSide){
            this.level().broadcastEntityEvent(this, (byte) 60);
        }
        return super.doHurtTarget(entity);
    }
    class ZombieAttackTurtleEggGoal extends RemoveBlockGoal {
        ZombieAttackTurtleEggGoal(PathfinderMob mob, double speedModifier, int verticalSearchRange) {
            super(Blocks.TURTLE_EGG, mob, speedModifier, verticalSearchRange);
        }

        @Override
        public void playDestroyProgressSound(LevelAccessor level, BlockPos pos) {
            level.playSound(null, pos, SoundEvents.ZOMBIE_DESTROY_EGG, SoundSource.HOSTILE, 0.5F, 0.9F + this.mob.getRandom().nextFloat() * 0.2F);
        }

        @Override
        public void playBreakSound(Level level, BlockPos pos) {
            level.playSound(null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
        }

        @Override
        public double acceptedDistance() {
            return 1.14;
        }
    }
}