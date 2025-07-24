package com.oakmods.example.procedures; // replace example with your modID (can be found by going to workspace menu on the top then workspace settings

import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

import com.oakmods.example.init.ExampleModBlocks; // replace 'example' with ur modID and 'ExampleModBlocks' with your modID + ModBlocks, dont forget to capitalise the first letter for 'ExampleModBlocks'

@EventBusSubscriber
public class BreakFlowerDisplay {
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        execute(event);
    }

    public static void execute() {
        execute(null);
    }

    private static void execute(@Nullable BlockEvent.BreakEvent event) {
        if (event == null) return;

        LevelAccessor world = event.getLevel();
        BlockPos pos = event.getPos();

        if (world.getBlockState(pos).is(BlockTags.create(ResourceLocation.parse("example:hanging_pot")))) { // This checks  if what the block is and will run its function
            FlowerDisplayOnBreak.execute((Level) world, pos.getX(), pos.getY(), pos.getZ());
        }
    }
}