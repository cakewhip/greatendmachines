package com.kqp.greatendmachines.block.entity;

import com.kqp.greatendmachines.GreatEndMachines;
import com.kqp.greatendmachines.inventory.ProximityHopperInventory;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

import java.util.List;
import java.util.Random;

public class ProximityIntakeBlockEntity extends BlockEntity implements ProximityHopperInventory, SidedInventory, Tickable {
    private static final Random RANDOM = new Random();
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(5, ItemStack.EMPTY);

    public ProximityIntakeBlockEntity() {
        super(GreatEndMachines.PROXIMITY_INTAKE_BLOCK_ENTITY);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        Inventories.fromTag(tag, items);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag, items);
        return super.toTag(tag);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public int[] getInvAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return new int[] { 0, 1, 2, 3, 4 };
        }

        return new int[0];
    }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir) {
        return dir == Direction.DOWN;
    }

    private boolean insertStack(ItemStack itemStack) {
        for (int i = 0; i < items.size(); i++) {
            ItemStack stackInSlot = items.get(i);

            if (stackInSlot.isEmpty()) {
                items.set(i, itemStack.copy());
                itemStack.setCount(0);
                return true;
            } else if (areItemsEqual(itemStack, stackInSlot)) {
                if (stackInSlot.getCount() < stackInSlot.getMaxCount()) {
                    int needed = stackInSlot.getMaxCount() - stackInSlot.getCount();

                    if (itemStack.getCount() > needed) {
                        itemStack.decrement(needed);
                        stackInSlot.increment(needed);

                        return insertStack(itemStack);
                    } else {
                        stackInSlot.increment(itemStack.getCount());
                        itemStack.setCount(0);

                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean areItemsEqual(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && ItemStack.areTagsEqual(stack1, stack2);
    }

    @Override
    public void tick() {
        Box box = new Box(pos.add(-1, 0, -1), pos.add(2, 2, 2));
        List<ItemEntity> itemEnts = world.<ItemEntity>getEntities(ItemEntity.class, box, null);

        int max = GreatEndMachines.config.proxIntakeCount;
        int count = 0;

        for (int i = 0; i < itemEnts.size(); i++) {
            if (takeItem(itemEnts.get(i))) {
                count++;

                if (count >= max) {
                    break;
                }
            }
        }
    }

    private boolean takeItem(ItemEntity itemEnt) {
        ItemStack itemStack = itemEnt.getStack();
        int initialSize = itemStack.getCount();

        if (insertStack(itemStack) || itemStack.getCount() < initialSize) {
            boolean remove = itemStack.isEmpty();

            this.world.playSound(null, itemEnt.getX(), itemEnt.getY(), itemEnt.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.BLOCKS, RANDOM.nextFloat() * (remove ? 0.5F : 0.15F), 1.0F);

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(ParticleTypes.PORTAL, itemEnt.getX(), itemEnt.getY() + RANDOM.nextDouble() * 2.0D, itemEnt.getZ(), RANDOM.nextGaussian(), 0.0D, RANDOM.nextGaussian());
            }

            if (remove) {
                itemEnt.remove();
            }

            return true;
        }
        
        return false;
    }
}
