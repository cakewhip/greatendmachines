package com.kqp.greatendmachines.block.entity;

import com.kqp.greatendmachines.GreatEndMachines;
import com.kqp.greatendmachines.client.container.ShulkerFurnaceContainer;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ShulkerFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    public ShulkerFurnaceBlockEntity() {
        super(GreatEndMachines.SHULKER_FURNACE_BLOCK_ENTITY, RecipeType.SMELTING);
    }

    @Override
    protected int getCookTime() {
        return (int) (super.getCookTime() * GreatEndMachines.config.shulkFurnCookTimeMult);
    }

    @Override
    protected int getFuelTime(ItemStack itemStack) {
        return (int) (super.getFuelTime(itemStack) * GreatEndMachines.config.shulkFurnFuelTimeMult);
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.shulker_furnace");
    }

    @Override
    protected Container createContainer(int i, PlayerInventory playerInventory) {
        return new ShulkerFurnaceContainer(i, playerInventory, this, this.propertyDelegate);
    }
}