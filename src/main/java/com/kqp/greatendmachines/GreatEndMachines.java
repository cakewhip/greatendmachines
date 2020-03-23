package com.kqp.greatendmachines;

import com.kqp.greatendmachines.block.ProximityIntakeBlock;
import com.kqp.greatendmachines.block.FishTrapBlock;

import com.kqp.greatendmachines.block.ShulkerFurnaceBlock;
import com.kqp.greatendmachines.block.entity.ProximityIntakeBlockEntity;
import com.kqp.greatendmachines.block.entity.FishTrapBlockEntity;
import com.kqp.greatendmachines.block.entity.ShulkerFurnaceBlockEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class GreatEndMachines implements ModInitializer {
	public static final FishTrapBlock FISH_TRAP = new FishTrapBlock();
	public static BlockEntityType<FishTrapBlockEntity> FISH_TRAP_BLOCK_ENTITY;

	public static final ShulkerFurnaceBlock SHULKER_FURNACE = new ShulkerFurnaceBlock();
	public static BlockEntityType<ShulkerFurnaceBlockEntity> SHULKER_FURNACE_BLOCK_ENTITY;
	public static final Identifier SHULKER_FURNACE_IDENTIFIER = new Identifier("greatendmachines", "shulker_furnace");

	public static final ProximityIntakeBlock PROXIMITY_INTAKE = new ProximityIntakeBlock();
	public static BlockEntityType<ProximityIntakeBlockEntity> PROXIMITY_INTAKE_BLOCK_ENTITY;

	public static final String SHULKER_FURNACE_TRANSLATION_KEY = Util.createTranslationKey("container", SHULKER_FURNACE_IDENTIFIER);

	public static GreatEndMachinesConfig config;

	@Override
	public void onInitialize() {
		System.out.println("Initializing Great End Machines");

		config = GreatEndMachinesConfig.load();

		FISH_TRAP_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "greatendmachines:fish_trap", BlockEntityType.Builder.create(FishTrapBlockEntity::new, FISH_TRAP).build(null));
		SHULKER_FURNACE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "greatendmachines:shulker_furnace", BlockEntityType.Builder.create(ShulkerFurnaceBlockEntity::new, SHULKER_FURNACE).build(null));
		PROXIMITY_INTAKE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "greatendmachines:proximity_intake", BlockEntityType.Builder.create(ProximityIntakeBlockEntity::new, PROXIMITY_INTAKE).build(null));

		Registry.register(Registry.BLOCK, new Identifier("greatendmachines", "fish_trap"), FISH_TRAP);
		Registry.register(Registry.ITEM, new Identifier("greatendmachines", "fish_trap"), new BlockItem(FISH_TRAP, new Item.Settings().group(ItemGroup.DECORATIONS)));
		Registry.register(Registry.BLOCK, new Identifier("greatendmachines", "shulker_furnace"), SHULKER_FURNACE);
		Registry.register(Registry.ITEM, new Identifier("greatendmachines", "shulker_furnace"), new BlockItem(SHULKER_FURNACE, new Item.Settings().group(ItemGroup.DECORATIONS)));
		Registry.register(Registry.BLOCK, new Identifier("greatendmachines", "proximity_intake"), PROXIMITY_INTAKE);
		Registry.register(Registry.ITEM, new Identifier("greatendmachines", "proximity_intake"), new BlockItem(PROXIMITY_INTAKE, new Item.Settings().group(ItemGroup.DECORATIONS)));

		ContainerProviderRegistry.INSTANCE.registerFactory(SHULKER_FURNACE_IDENTIFIER, (syncId, identifier, player, buf) -> {
			final World world = player.world;
			final BlockPos pos = buf.readBlockPos();

			return world.getBlockState(pos).createContainerFactory(player.world, pos).createMenu(syncId, player.inventory, player);
		});
	}
}
