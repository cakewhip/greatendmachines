package com.kqp.greatendmachines.client.screen;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.client.gui.screen.recipebook.AbstractFurnaceRecipeBookScreen;
import net.minecraft.item.Item;

import java.util.Set;

public class ShulkerFurnaceRecipeBookScreen extends AbstractFurnaceRecipeBookScreen {
    public ShulkerFurnaceRecipeBookScreen() {
        super();
    }

    @Override
    protected boolean isFilteringCraftable() {
        return this.recipeBook.isFurnaceFilteringCraftable();
    }

    @Override
    protected void setFilteringCraftable(boolean filteringCraftable) {
        this.recipeBook.setFurnaceFilteringCraftable(filteringCraftable);
    }

    @Override
    protected boolean isGuiOpen() {
        return this.recipeBook.isFurnaceGuiOpen();
    }

    @Override
    protected void setGuiOpen(boolean opened) {
        this.recipeBook.setFurnaceGuiOpen(opened);
    }

    @Override
    protected String getToggleCraftableButtonText() {
        return "gui.recipebook.toggleRecipes.smeltable";
    }

    @Override
    protected Set<Item> getAllowedFuels() {
        return AbstractFurnaceBlockEntity.createFuelTimeMap().keySet();
    }
}