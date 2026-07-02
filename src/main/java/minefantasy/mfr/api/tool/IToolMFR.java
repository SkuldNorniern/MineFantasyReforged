package minefantasy.mfr.api.tool;

import minefantasy.mfr.constants.Tool;
import net.minecraft.world.item.ItemStack;

public interface IToolMFR {
	float getEfficiency(ItemStack item);

	int getTier(ItemStack item);

	Tool getToolType(ItemStack stack);
}
