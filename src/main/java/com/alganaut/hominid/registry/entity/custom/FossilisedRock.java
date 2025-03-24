package com.alganaut.hominid.registry.entity.custom;

import com.alganaut.hominid.registry.entity.HominidEntityCreator;
import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

public class FossilisedRock extends ThrowableItemProjectile {
    public FossilisedRock(EntityType<? extends FossilisedRock> entityType, Level level) {
        super(entityType, level);
    }

    public FossilisedRock(Level level, LivingEntity shooter) {
        super(HominidEntityCreator.ROCK.get(), shooter, level);
    }

    public FossilisedRock(Level level, double x, double y, double z) {
        super(HominidEntityCreator.ROCK.get(), x, y, z, level);
    }

    protected Item getDefaultItem() {
        return HominidItems.SLAB.get();
    }

    public void tick(){
        super.tick();
        this.setNoGravity(true);
        this.setDeltaMovement(this.getDeltaMovement().x, 0, this.getDeltaMovement().z);
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        int i = 8;
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), (float)i);
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }
}
