package com.kqp.greatendmachines.block;

import com.kqp.greatendmachines.GreatEndMachines;
import com.kqp.greatendmachines.block.entity.ShulkerFurnaceBlockEntity;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ShulkerFurnaceBlock extends AbstractFurnaceBlock {
    public ShulkerFurnaceBlock() {
        super(FabricBlockSettings.of(Material.STONE).strength(1.5F, 6.0F).build());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new ShulkerFurnaceBlockEntity();
    }

    @Override
    protected void openContainer(World world, BlockPos pos, PlayerEntity player) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof ShulkerFurnaceBlockEntity) {
                final BlockPos fPos = pos;
                ContainerProviderRegistry.INSTANCE.openContainer(
                        GreatEndMachines.SHULKER_FURNACE_IDENTIFIER, player, buf -> buf.writeBlockPos(pos)
                );
            }
        }
    }

    @Override
    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ShulkerFurnaceBlockEntity) {
                ItemScatterer.spawn(world, pos, (ShulkerFurnaceBlockEntity) blockEntity);
                world.updateHorizontalAdjacent(pos, this);
            }
            super.onBlockRemoved(state, world, pos, newState, moved);
        }
    }
}
