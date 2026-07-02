package minefantasy.mfr.api.tool;

import net.minecraft.world.item.ItemStack;

public interface IHuntingItem {
	boolean canRetrieveDrops(ItemStack item);
}
