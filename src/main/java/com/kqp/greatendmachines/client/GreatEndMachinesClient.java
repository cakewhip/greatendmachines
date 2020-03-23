package com.kqp.greatendmachines.client;

import com.kqp.greatendmachines.GreatEndMachines;
import com.kqp.greatendmachines.client.container.ShulkerFurnaceContainer;
import com.kqp.greatendmachines.client.screen.ShulkerFurnaceScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

public class GreatEndMachinesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenProviderRegistry.INSTANCE.<ShulkerFurnaceContainer>registerFactory(GreatEndMachines.SHULKER_FURNACE_IDENTIFIER, (container) -> new ShulkerFurnaceScreen(container, MinecraftClient.getInstance().player.inventory, new TranslatableText(GreatEndMachines.SHULKER_FURNACE_TRANSLATION_KEY)));
    }
}
