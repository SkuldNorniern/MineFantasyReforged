package minefantasy.mfr.api.archery;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;

public interface IArrowHandler {
	AbstractArrow onFireArrow(AbstractArrow entityArrow, ItemStack arrow, ItemStack bow, float charge, Player player);
}
