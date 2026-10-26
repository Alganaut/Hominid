package com.alganaut.hominid.registry.misc;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.lang.reflect.InvocationTargetException;

public class HominidEntityFactory<T extends Entity> implements EntityType.EntityFactory<T> {
    private final Class<T> entityClass;

    private HominidEntityFactory(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public static <T extends Entity> HominidEntityFactory<T> make(Class<T> entityClass) {
        return new HominidEntityFactory<>(entityClass);
    }

    @Override
    public T create(EntityType<T> entityType, Level level) {
        try {
           return entityClass.getDeclaredConstructor(Level.class).newInstance(level);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }


    }
}
