package minefantasy.mfr.api.crafting;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ISalvageDrop {
	boolean canSalvage(Player user, ItemStack item);
}
