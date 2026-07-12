package minefantasy.mfr.api.weapon;

import net.minecraft.world.item.ItemStack;

public interface IRackItem {

	float getScale(ItemStack itemstack);

	float getOffsetX(ItemStack itemstack);

	float getOffsetY(ItemStack itemstack);

	float getOffsetZ(ItemStack itemstack);

	float getRotationOffset(ItemStack itemstack);

	// TODO: parameter type TileEntityRack not yet ported — using Object as placeholder
	boolean canHang(Object rack, ItemStack item, int slot);

	boolean flip(ItemStack itemStack);
}
