package minefantasy.mfr.api.heating;

import net.minecraft.world.item.ItemStack;

public interface IHotItem {
	boolean isHot(ItemStack item);

	boolean isCoolable(ItemStack item);
}
