package com.oakmods.example.procedures; // replace example with your modID (can be found by going to workspace menu on the top then workspace settings

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;
import net.minecraft.network.chat.Component;

public class FlowerDisplayOnBreak {
    public static void execute(LevelAccessor world, double x, double y, double z) 
    {
        if (world instanceof ServerLevel _level) 
        {
            _level.getServer().getCommands().performPrefixedCommand(
                new CommandSourceStack(
                    CommandSource.NULL,
                    new Vec3(x, y, z),
                    Vec2.ZERO,
                    _level,
                    4,
                    "",
                    Component.literal(""),
                    _level.getServer(),
                    null
                ).withSuppressedOutput(),
                "kill @e[type=minecraft:block_display,distance=..0.3,limit=1]"
            );
        }
    }
}