package com.kqp.greatendmachines.client.screen;

import com.kqp.greatendmachines.client.container.ShulkerFurnaceContainer;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ShulkerFurnaceScreen extends AbstractFurnaceScreen<ShulkerFurnaceContainer> {
    private static final Identifier BG_TEX = new Identifier("greatendmachines:textures/gui/container/shulker_furnace.png");

    public ShulkerFurnaceScreen(ShulkerFurnaceContainer container, PlayerInventory inventory, Text title) {
        super(container, new ShulkerFurnaceRecipeBookScreen(), inventory, title, BG_TEX);
    }
}
