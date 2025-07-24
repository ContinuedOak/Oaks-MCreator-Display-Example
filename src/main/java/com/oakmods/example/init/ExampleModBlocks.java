
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.oakmods.example.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

import com.oakmods.example.block.HangingFlowerPotBlock;
import com.oakmods.example.block.HangingChainBlock;
import com.oakmods.example.ExampleMod;

public class ExampleModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(ExampleMod.MODID);
	public static final DeferredBlock<Block> HANGING_FLOWER_POT = register("hanging_flower_pot", HangingFlowerPotBlock::new);
	public static final DeferredBlock<Block> HANGING_CHAIN = register("hanging_chain", HangingChainBlock::new);

	// Start of user code block custom blocks
	// End of user code block custom blocks
	private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
		return REGISTRY.registerBlock(name, supplier, BlockBehaviour.Properties.of());
	}
}
