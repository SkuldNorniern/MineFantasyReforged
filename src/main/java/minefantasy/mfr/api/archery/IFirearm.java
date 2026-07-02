package minefantasy.mfr.api.archery;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IFirearm {
	boolean canAcceptAmmo(ItemStack weapon, String ammo);

	void reloadFirearm(Player player);
}
