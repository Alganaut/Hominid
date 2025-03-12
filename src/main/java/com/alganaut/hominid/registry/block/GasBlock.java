package com.alganaut.hominid.registry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import net.minecraft.core.Direction;

public class GasBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    private static final VoxelShape FLOOR_SHAPE = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, 0.1D, 1.0D);
    private static final VoxelShape CEILING_SHAPE = Shapes.box(0.0D, 0.9D, 0.0D, 1.0D, 1.0D, 1.0D);
    private static final VoxelShape WALL_SHAPE_NORTH = Shapes.box(0.0D, 0.0D, 0.9D, 1.0D, 1.0D, 1.0D);
    private static final VoxelShape WALL_SHAPE_SOUTH = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1D);
    private static final VoxelShape WALL_SHAPE_EAST = Shapes.box(0.0D, 0.0D, 0.0D, 0.1D, 1.0D, 1.0D);
    private static final VoxelShape WALL_SHAPE_WEST = Shapes.box(0.9D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

    public GasBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getClickedFace();
        return this.defaultBlockState().setValue(FACING, facing);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);

        switch (facing) {
            case UP: return FLOOR_SHAPE;
            case DOWN: return CEILING_SHAPE;
            case NORTH: return WALL_SHAPE_NORTH;
            case SOUTH: return WALL_SHAPE_SOUTH;
            case EAST: return WALL_SHAPE_EAST;
            case WEST: return WALL_SHAPE_WEST;
            default: return FLOOR_SHAPE;
        }
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 30;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!world.isClientSide && state != null && state.getBlock() != Blocks.AIR) {
            if (state.hasProperty(BlockStateProperties.FACING)){
                Direction facing = state.getValue(FACING);
                BlockPos attachedPos = pos.relative(facing.getOpposite());
                BlockState attachedState = world.getBlockState(attachedPos);

                if (attachedState.isAir() || !attachedState.isSolidRender(world, attachedPos) || attachedState.getBlock() == GasBlock.this) {
                    world.removeBlock(pos, false);
                }

                if (isFireNearby(world, pos)) {
                    igniteBlock(world, pos);
                }
            }

        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isClientSide && isFireNearby(level, pos)) {
            igniteBlock(level, pos);
        }
    }

    private void igniteBlock(Level world, BlockPos pos) {
        if (world.getBlockState(pos.above()).isAir()) {
            world.setBlock(pos.above(), Blocks.FIRE.defaultBlockState(), 3);
        }

        BlockState state = world.getBlockState(pos);
        Direction facing = state.getValue(FACING);

        BlockPos attachedPos = pos.relative(facing.getOpposite());
        BlockState attachedState = world.getBlockState(attachedPos);

        BlockPos[] adjacentBlocks = { pos.north(), pos.south(), pos.east(), pos.west(), pos.above(), pos.below() };
        for (BlockPos neighbor : adjacentBlocks) {
            BlockState neighborState = world.getBlockState(neighbor);

            if (neighborState.getBlock() instanceof GasBlock) {
                world.scheduleTick(neighbor, this, 10);
            }
            if (attachedState.getBlock() != Blocks.OBSIDIAN && attachedState.getBlock() != Blocks.BEDROCK) {
                world.scheduleTick(neighbor, this, 40);
            }
        }
    }

    private boolean isFireNearby(Level world, BlockPos pos) {
        BlockPos[] fireCheckPositions = {
                pos.north(), pos.south(), pos.east(), pos.west(), pos.above(), pos.below()
        };

        for (BlockPos firePos : fireCheckPositions) {
            if (world.getBlockState(firePos).is(Blocks.FIRE)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(state, world, pos, random);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (world.isClientSide()) {
            return;
        }

        if (state.is(Blocks.FIRE)) {
            world.removeBlock(pos, false);
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }
}
