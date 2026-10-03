package com.alganaut.hominid.entity.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RemoveBlockGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class AttackTurtleEggGoal extends RemoveBlockGoal {
    private Double acceptedDistance;

    public AttackTurtleEggGoal(PathfinderMob mob, double speedModifier, int verticalSearchRange) {
        super(Blocks.TURTLE_EGG, mob, speedModifier, verticalSearchRange);
    }

    public AttackTurtleEggGoal(PathfinderMob mob, double speedModifier, int verticalSearchRange, double acceptedDistance) {
        this(mob, speedModifier, verticalSearchRange);
        this.acceptedDistance = acceptedDistance;
    }

    @Override
    public void playDestroyProgressSound(LevelAccessor level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.ZOMBIE_DESTROY_EGG, SoundSource.HOSTILE, 0.5F, 0.9F + mob.getRandom().nextFloat() * 0.2F);
    }

    @Override
    public void playBreakSound(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
    }

    @Override
    public double acceptedDistance() {
        return acceptedDistance == null ? super.acceptedDistance() : acceptedDistance;
    }
}
