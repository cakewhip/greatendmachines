package com.kqp.greatendmachines.block;

import com.kqp.greatendmachines.block.entity.FishTrapBlockEntity;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class FishTrapBlock extends Block implements BlockEntityProvider {
    public FishTrapBlock() {
        super(FabricBlockSettings.of(Material.STONE).strength(1.5F, 6.0F).build());
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        if (!world.isClient) {

            FishTrapBlockEntity blockEntity = (FishTrapBlockEntity) world.getBlockEntity(blockPos);
            ItemStack held = player.getStackInHand(hand);

            if (!held.isEmpty() && held.getItem() == Items.WHEAT_SEEDS) {
                ItemStack in = blockEntity.getInvStack(1);

                if (in.isEmpty()) {
                    blockEntity.setInvStack(1, held);
                    player.setStackInHand(hand, ItemStack.EMPTY);
                } else if (in.getCount() < in.getMaxCount()) {
                    int tillMax = in.getMaxCount() - in.getCount();

                    if (held.getCount() >= tillMax) {
                        held.decrement(tillMax);
                        in.setCount(in.getMaxCount());
                    } else {
                        in.increment(held.getCount());
                        player.setStackInHand(hand, ItemStack.EMPTY);
                    }
                }
            } else {
                if (!blockEntity.getInvStack(0).isEmpty()) {
                    if (!player.inventory.insertStack(blockEntity.getInvStack(0))) {
                        Block.dropStack(world, blockPos, blockEntity.getInvStack(0));
                    }

                    blockEntity.setInvStack(0, ItemStack.EMPTY);
                }
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            ItemScatterer.spawn(world, pos, (FishTrapBlockEntity) world.getBlockEntity(pos));
            world.updateHorizontalAdjacent(pos, this);

            super.onBlockRemoved(state, world, pos, newState, moved);
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new FishTrapBlockEntity();
    }
}