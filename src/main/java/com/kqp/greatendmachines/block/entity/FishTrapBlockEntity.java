package com.kqp.greatendmachines.block.entity;

import com.kqp.greatendmachines.GreatEndMachines;
import com.kqp.greatendmachines.inventory.FishTrapInventory;
import net.minecraft.advancement.criterion.Criterions;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class FishTrapBlockEntity extends BlockEntity implements FishTrapInventory, SidedInventory, Tickable {
    private static final Random RANDOM = new Random();

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(2, ItemStack.EMPTY);

    public int waitTime;

    public FishTrapBlockEntity() {
        super(GreatEndMachines.FISH_TRAP_BLOCK_ENTITY);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        Inventories.fromTag(tag, items);
        waitTime = tag.getInt("WaitTime");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag, items);
        tag.putInt("WaitTime", waitTime);
        return super.toTag(tag);
    }

    @Override
    public int[] getInvAvailableSlots(Direction dir) {
        switch (dir) {
            case DOWN:
                return new int[] { 0 };
            default:
                return new int[] { 1 };
        }
    }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir) {
        return slot == 1 && dir != Direction.DOWN && stack.getItem() == Items.WHEAT_SEEDS;
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir) {
        return slot == 0 && dir == Direction.DOWN;
    }

    @Override
    public void tick() {
        if (!this.getWorld().isClient) {
            ItemStack fish = getInvStack(0);
            ItemStack feed = getInvStack(1);

            if (waitTime <= 0) {
                rollWaitTime();

                if (getInvStack(0).isEmpty()) {

                    if ((GreatEndMachines.config.fishTrapReqSeeds || !feed.isEmpty()) && feed.getItem() == Items.WHEAT_SEEDS) {
                        ItemStack fishingRod = new ItemStack(Items.FISHING_ROD);
                        LootContext.Builder builder = (new LootContext.Builder((ServerWorld) this.world)).put(LootContextParameters.POSITION, new BlockPos(this.pos))
                                .put(LootContextParameters.TOOL, fishingRod)
                                .setRandom(RANDOM)
                                .setLuck(1.5F + (RANDOM.nextFloat() * 1.5F));
                        LootTable lootTable = this.world.getServer().getLootManager().getSupplier(LootTables.FISHING_GAMEPLAY);
                        List<ItemStack> list = lootTable.getDrops(builder.build(LootContextTypes.FISHING));

                        setInvStack(0, list.get(0));

                        if (GreatEndMachines.config.fishTrapReqSeeds) {
                            feed.decrement(1);
                        }
                    }
                }
            } else {
                if (canFish()) {
                    waitTime--;
                } else {
                    rollWaitTime();
                }
            }
        }
    }

    private void rollWaitTime() {
        waitTime = (int) ((5 + RANDOM.nextInt(10)) * (1 - (getWaterQuality() / 26.0)) * 20);
        waitTime = Math.max(20, waitTime);
    }

    public boolean canFish() {
        ItemStack fish = getInvStack(0);
        ItemStack feed = getInvStack(1);

        return fish.isEmpty() && (GreatEndMachines.config.fishTrapReqSeeds || !feed.isEmpty()) && getWaterQuality() > 0;
    }

    public double getWaterQuality() {
        double quality = 0;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = -1; k < 2; k++) {
                    BlockPos wPos = new BlockPos(pos.getX() + i, pos.getY() + j, pos.getZ() + k);
                    BlockState b = world.getBlockState(wPos);
                    if (b.getBlock() == Blocks.WATER) {
                        quality += 0.5D;

                        if (world.isSkyVisible(wPos)) {
                            quality += 0.5D;
                        }

                        if (world.hasRain(wPos)) {
                            quality += 0.2;
                        }
                    }
                }
            }
        }

        return quality;
    }
}
