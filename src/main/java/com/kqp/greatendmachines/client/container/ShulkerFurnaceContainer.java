package com.kqp.greatendmachines.client.container;

import net.minecraft.container.FurnaceContainer;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;

public class ShulkerFurnaceContainer extends FurnaceContainer {
    public ShulkerFurnaceContainer(int syncId, PlayerInventory playerInventory) {
        super(syncId, playerInventory);
    }

    public ShulkerFurnaceContainer(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(syncId, playerInventory, inventory, propertyDelegate);
    }
}
