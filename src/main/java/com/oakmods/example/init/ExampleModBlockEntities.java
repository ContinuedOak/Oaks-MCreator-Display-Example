
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.oakmods.example.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;

import com.oakmods.example.block.entity.HangingFlowerPotBlockEntity;
import com.oakmods.example.ExampleMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ExampleModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ExampleMod.MODID);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> HANGING_FLOWER_POT = register("hanging_flower_pot", ExampleModBlocks.HANGING_FLOWER_POT, HangingFlowerPotBlockEntity::new);

	// Start of user code block custom block entities
	// End of user code block custom block entities
	private static DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> new BlockEntityType(supplier, block.get()));
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, HANGING_FLOWER_POT.get(), (blockEntity, side) -> ((HangingFlowerPotBlockEntity) blockEntity).getItemHandler());
	}
}
