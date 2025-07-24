package com.oakmods.example.procedures; // replace example with your modID (can be found by going to workspace menu on the top then workspace settings

import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;
import net.minecraft.world.InteractionHand;

import javax.annotation.Nullable;

import com.oakmods.example.init.ExampleModBlocks; // replace 'example' with ur modID and 'ExampleModBlocks' with your modID + ModBlocks, dont forget to capitalise the first letter for 'ExampleModBlocks'

@EventBusSubscriber
public class AddFlowerToInventory { // this should share the exact name as the elemet you used
	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if (event.getHand() != event.getEntity().getUsedItemHand())
			return;

		// Cancel default block placement behavior if it's your hanging_pot
		if (event.getLevel().getBlockState(event.getPos()).is(BlockTags.create(ResourceLocation.parse("example:hanging_pot")))) // This is what allows the logic to work when it comes to interacting
		{
			execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getEntity());
			event.setCanceled(true); // Prevents actual block placement
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;

		BlockPos pos = BlockPos.containing(x, y, z);
		   if ((world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(ResourceLocation.parse("example:hanging_pot")))) // This is what allows the logic to work when it comes to interacting
		   {


			int itemCount = new Object() {
				public int getAmount(LevelAccessor world, BlockPos pos, int slotid) {
					if (world instanceof ILevelExtension _ext) {
						IItemHandler _itemHandler = _ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
						if (_itemHandler != null)
							return _itemHandler.getStackInSlot(slotid).getCount();
					}
					return 0;
				}
			}.getAmount(world, pos, 0);

			if (itemCount == 1) {
				// Despawn block_display first
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(
						new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						"kill @e[type=minecraft:block_display,distance=..0.5,limit=1]"
					);

				// Give item to player
				if (entity instanceof Player _player) 
				{
					ItemStack _setstack = new Object() {
						public ItemStack getItemStack(LevelAccessor world, BlockPos pos, int slotid) {
							if (world instanceof ILevelExtension _ext) {
								IItemHandler _itemHandler = _ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
								if (_itemHandler != null)
									return _itemHandler.getStackInSlot(slotid).copy();
							}
							return ItemStack.EMPTY;
						}
						
					}.getItemStack(world, pos, 0);
					_setstack.setCount(1);

					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
					_player.swing(net.minecraft.world.InteractionHand.MAIN_HAND, true);
				}

				// Remove item from display slot
				if (world instanceof ILevelExtension _ext &&
						_ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
					ItemStack _stk = _itemHandlerModifiable.getStackInSlot(0).copy();
					_stk.shrink(1);
					_itemHandlerModifiable.setStackInSlot(0, _stk);
				}
			}
			// If the player is holding a small_flower item, insert it and display it
			else {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)
				        .is(ItemTags.create(ResourceLocation.parse("minecraft:small_flowers"))) ||
 				  (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)
  				      .is(ItemTags.create(ResourceLocation.parse("minecraft:saplings")))) 
  				      {

  				      	// above is an OR statment that checks if the player is holding an item with the following tags

					// Insert into inventory and remove from hand
					if (world instanceof ILevelExtension _ext &&
							_ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null) instanceof IItemHandlerModifiable _itemHandlerModifiable) {
						ItemStack _setstack = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
						_setstack.setCount(1);
						_itemHandlerModifiable.setStackInSlot(0, _setstack);

						// Remove item from player's hand
						if (entity instanceof LivingEntity _livEnt) {
							_livEnt.getMainHandItem().shrink(1);
						}
					}

					// Fetch the inserted item AFTER insertion
					ItemStack insertedItem = ItemStack.EMPTY;
					if (world instanceof ILevelExtension _ext) {
						IItemHandler handler = _ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
						if (handler != null) {
							insertedItem = handler.getStackInSlot(0).copy();
						}

						if (entity instanceof Player _player)
						{
							_player.swing(net.minecraft.world.InteractionHand.MAIN_HAND, true);

							_player.awardStat(Stats.POT_FLOWER); // Increase the players Flowers Planted Stat
						}	
					}

					// Get item registry name
					String regname = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(insertedItem.getItem()).toString();

					// Summon block_display entity
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(
							new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							("summon block_display ~0.10 ~0.25 ~0.10 {transformation:{left_rotation:[0f,0f,0f,1f],right_rotation:[0f,0f,0f,1f],scale:[0.8f,0.8f,0.8f],translation:[0f,0f,0f]},block_state:{Name:\"" + regname + "\"}}")
						);
				}
			}
		}
	}
}