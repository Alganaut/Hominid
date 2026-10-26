package com.alganaut.hominid.entity.famished;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

final class PreySelection {
    private PreySelection() {}

    @Nullable
    static LivingEntity findNearest(Mob mob) {
        List<LivingEntity> animals = mob.level().getEntitiesOfClass(LivingEntity.class,
                mob.getBoundingBox().inflate(30), Animal.class::isInstance);

        animals.removeIf(entity -> entity instanceof SkeletonHorse);
        animals.removeIf(entity -> entity instanceof TamableAnimal animal && (animal.isTame()));
        animals.removeIf(entity -> entity instanceof OwnableEntity && entity.isBaby());
        animals.removeIf(entity -> entity instanceof OwnableEntity ownable && ownable.getOwner() != null);

        if (animals.isEmpty()) {
            return null;
        }
        animals.sort(Comparator.comparingDouble(mob::distanceToSqr));
        return animals.get(0);
    }
}
