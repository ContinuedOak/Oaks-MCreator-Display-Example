
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.oakmods.example.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import java.util.function.Function;

import com.oakmods.example.ExampleMod;

public class ExampleModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ExampleMod.MODID);
	public static final DeferredItem<Item> HANGING_FLOWER_POT = block(ExampleModBlocks.HANGING_FLOWER_POT);
	public static final DeferredItem<Item> HANGING_CHAIN = block(ExampleModBlocks.HANGING_CHAIN);

	// Start of user code block custom items
	// End of user code block custom items
	private static <I extends Item> DeferredItem<I> register(String name, Function<Item.Properties, ? extends I> supplier) {
		return REGISTRY.registerItem(name, supplier, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return REGISTRY.registerItem(block.getId().getPath(), properties -> new BlockItem(block.get(), properties), new Item.Properties());
	}
}
