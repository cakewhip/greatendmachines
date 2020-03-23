package com.kqp.greatendmachines.block;

import com.kqp.greatendmachines.block.entity.ProximityIntakeBlockEntity;
import com.kqp.greatendmachines.inventory.ProximityHopperInventory;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ProximityIntakeBlock extends Block implements BlockEntityProvider {
    protected static final VoxelShape SHAPE;

    public ProximityIntakeBlock() {
        super(FabricBlockSettings.of(Material.STONE).strength(1.5F, 6.0F).build());
    }

    @Override
    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            ItemScatterer.spawn(world, pos, (ProximityHopperInventory) world.getBlockEntity(pos));
            world.updateHorizontalAdjacent(pos, this);

            super.onBlockRemoved(state, world, pos, newState, moved);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return SHAPE;
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new ProximityIntakeBlockEntity();
    }

    static {
        SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    }
}
