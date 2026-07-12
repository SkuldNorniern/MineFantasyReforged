package minefantasy.mfr.api.archery;

import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;

public interface IArrowRetrieve {
	AbstractArrow.Pickup canBePickedUp();

	ItemStack getDroppedItem();
}
