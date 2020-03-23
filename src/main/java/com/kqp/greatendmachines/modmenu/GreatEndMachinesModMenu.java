package com.kqp.greatendmachines.modmenu;

import java.util.function.Function;

import com.kqp.greatendmachines.GreatEndMachines;
import com.kqp.greatendmachines.GreatEndMachinesConfig;

import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.gui.screen.Screen;

public class GreatEndMachinesModMenu implements ModMenuApi {
    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory() {
		return screen -> {
            ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(screen)
                .setTitle("title.greatendmachines.config");

            GreatEndMachinesConfig temp = new GreatEndMachinesConfig();

            ConfigCategory general = builder.getOrCreateCategory("category.greatendmachines.general");
            general.addEntry(builder.entryBuilder().startBooleanToggle("option.greatendmachines.fish_trap_require_seeds", GreatEndMachines.config.fishTrapReqSeeds)
                    .setDefaultValue(true)
                    .setTooltip("Whether the Fish Trap should require seeds")
                    .setSaveConsumer(newValue -> temp.fishTrapReqSeeds = newValue)
                    .build()
            );
            general.addEntry(builder.entryBuilder().startFloatField("option.greatendmachines.shulk_furn_cooktime_mult", GreatEndMachines.config.shulkFurnCookTimeMult)
                    .setDefaultValue(0.125F)
                    .setTooltip("Multiplier for Shulker Furnace cook time")
                    .setSaveConsumer(newValue -> temp.shulkFurnCookTimeMult = newValue)
                    .build()
            );
            general.addEntry(builder.entryBuilder().startFloatField("option.greatendmachines.shulk_furn_fueltime_mult", GreatEndMachines.config.shulkFurnFuelTimeMult)
                    .setDefaultValue(0.5F)
                    .setTooltip("Multiplier for Shulker Furnace fuel time")
                    .setSaveConsumer(newValue -> temp.shulkFurnFuelTimeMult = newValue)
                    .build()
            );
            general.addEntry(builder.entryBuilder().startIntField("option.greatendmachines.prox_intake_count", GreatEndMachines.config.proxIntakeCount)
                .setDefaultValue(2)
                .setTooltip("Item stacks picked up per tick")
                .setSaveConsumer(newValue -> temp.proxIntakeCount = newValue)
                .build()
            );

            builder.setSavingRunnable(() -> {
                GreatEndMachines.config = temp;
                GreatEndMachinesConfig.save(temp);
            });

            return builder.build();
        };
    }
    
    @Override
    public String getModId() {
        return "greatendmachines";
    }
}