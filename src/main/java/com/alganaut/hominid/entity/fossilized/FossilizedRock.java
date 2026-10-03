package com.alganaut.hominid.entity.fossilized;

import com.alganaut.hominid.registry.HominidEntityCreator;
import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class FossilizedRock extends ThrowableItemProjectile {
    private int lifetime;
    public FossilizedRock(EntityType<? extends FossilizedRock> entityType, Level level) {
        super(entityType, level);
        this.lifetime = 0;
    }

    public FossilizedRock(Level level, LivingEntity shooter) {
        super(HominidEntityCreator.ROCK.get(), shooter, level);
        lifetime = 0;
    }

    public FossilizedRock(Level level, double x, double y, double z) {
        super(HominidEntityCreator.ROCK.get(), x, y, z, level);
        lifetime = 0;
    }

    protected Item getDefaultItem() {
        return HominidItems.SLAB.get();
    }

    @Override
    public void tick() {
        super.tick();

        setNoGravity(true);
        setDeltaMovement(getDeltaMovement());

        lifetime++;
        if (lifetime >= 180) {
            discard();
        }
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.hurt(damageSources().thrown(this, getOwner()), 9);
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!level().isClientSide) {
            discard();
        }

    }
}
