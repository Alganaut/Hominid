package com.alganaut.hominid.registry.entity.custom;

import com.alganaut.hominid.registry.entity.HominidEntityCreator;
import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

public class FossilisedRock extends ThrowableItemProjectile {
    private int lifetime;
    public FossilisedRock(EntityType<? extends FossilisedRock> entityType, Level level) {
        super(entityType, level);
        this.lifetime = 0;
    }

    public FossilisedRock(Level level, LivingEntity shooter) {
        super(HominidEntityCreator.ROCK.get(), shooter, level);
        this.lifetime = 0;
    }

    public FossilisedRock(Level level, double x, double y, double z) {
        super(HominidEntityCreator.ROCK.get(), x, y, z, level);
        this.lifetime = 0;
    }

    protected Item getDefaultItem() {
        return HominidItems.SLAB.get();
    }

    @Override
    public void tick() {
        super.tick();

        this.setNoGravity(true);
        this.setDeltaMovement(this.getDeltaMovement());

        this.lifetime++;
        if (this.lifetime >= 180) {
            this.discard();
        }
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), 9);
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.discard();
        }

    }
}