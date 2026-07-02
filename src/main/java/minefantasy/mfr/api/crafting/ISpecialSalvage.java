package minefantasy.mfr.api.crafting;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface ISpecialSalvage {
	List<ItemStack> getSalvage(ItemStack item);
}
