package minefantasy.mfr.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;

public class InventoryUtils {

	public static void dropItemsInWorld(Level world, IItemHandler handler, BlockPos pos) {
		for (int slot = 0; slot < handler.getSlots(); slot++) {
			dropItemInWorld(world, handler.getStackInSlot(slot), pos);
		}
	}

	public static void dropItemInWorld(Level world, ItemStack item, BlockPos pos) {
		dropItemInWorld(world, item, pos.getX(), pos.getY(), pos.getZ());
	}

	public static void dropItemInWorld(Level world, ItemStack item, double x, double y, double z) {
		if (world.isClientSide()) {
			return;
		}
		if (item.isEmpty()) {
			return;
		}
		world.addFreshEntity(new ItemEntity(world, x, y, z, item));
	}
}
