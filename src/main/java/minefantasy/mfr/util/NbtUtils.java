package minefantasy.mfr.util;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class NbtUtils {

	private NbtUtils() {
		throw new IllegalStateException("Util class cannot be instantiated");
	}

	/**
	 * Returns a mutable copy of the item's CUSTOM_DATA compound, or a new empty one.
	 * Callers MUST write the result back via {@link #saveNBT(ItemStack, CompoundTag)}.
	 */
	public static CompoundTag getOrCreateNBT(ItemStack stack) {
		CustomData data = stack.get(DataComponents.CUSTOM_DATA);
		return data != null ? data.copyTag() : new CompoundTag();
	}

	public static void saveNBT(ItemStack stack, CompoundTag tag) {
		stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
	}

	/**
	 * Stores tag safely, guarding against circular references.
	 */
	public static void storeTagSafely(CompoundTag compound, String key, Tag tag) {
		if (compound == tag || deepContains(tag, compound)) {
			MineFantasyReforged.LOG.error(
					"Cannot store tag of type {} under key '{}' as it would result in a circular reference!",
					tag.getClass().getSimpleName(), key);
		} else {
			compound.put(key, tag);
		}
	}

	/**
	 * Recursively searches within the first NBT tag for the second NBT tag.
	 */
	public static boolean deepContains(Tag toSearch, Tag searchFor) {
		if (toSearch instanceof CompoundTag compound) {
			for (String subKey : compound.keySet()) {
				Tag subTag = compound.get(subKey);
				if (subTag == searchFor || deepContains(subTag, searchFor)) return true;
			}
		} else if (toSearch instanceof ListTag list) {
			for (Tag subTag : list) {
				if (subTag == searchFor || deepContains(subTag, searchFor)) return true;
			}
		}
		return false;
	}
}
