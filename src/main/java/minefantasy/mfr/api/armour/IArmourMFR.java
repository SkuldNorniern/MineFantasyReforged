package minefantasy.mfr.api.armour;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public interface IArmourMFR {
	float getPieceWeight(ItemStack item, EquipmentSlot slot);

	String getSuitWeightType(ItemStack item);
}
