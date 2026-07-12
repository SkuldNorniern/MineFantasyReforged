package minefantasy.mfr.util;

import minefantasy.mfr.api.archery.IAmmo;
import minefantasy.mfr.api.archery.IArrowRetrieve;
import minefantasy.mfr.api.archery.IFirearm;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class ArrowUtils {
	public static boolean canAcceptArrow(ItemStack ammo, ItemStack weapon) {
		String ammoType = "null";
		if (!ammo.isEmpty() && ammo.getItem() instanceof IAmmo) {
			ammoType = ((IAmmo) ammo.getItem()).getAmmoType(ammo);
		}

		if (isVanillaArrow(ammo)) {
			ammoType = "arrow";
		}

		if (!weapon.isEmpty() && weapon.getItem() instanceof IFirearm) {
			return ((IFirearm) weapon.getItem()).canAcceptAmmo(weapon, ammoType);
		}

		return ammoType.equalsIgnoreCase("arrow");
	}

	public static boolean isVanillaArrow(ItemStack ammo) {
		// ItemArrow class no longer exists; check by registered tag or class hierarchy.
		// TODO: update when ItemArrowMFR is ported — use instanceof check against MFR arrow item class.
		return ammo.getItem() == Items.ARROW || ammo.getItem() == Items.TIPPED_ARROW
				|| ammo.getItem() == Items.SPECTRAL_ARROW;
	}

	public static void stickArrowIn(Entity hit, Entity projectile) {
		stickArrowIn(hit, getDroppedArrow(projectile), projectile);
	}

	public static void stickArrowIn(Entity hit, ItemStack arrow, Entity projectile) {
		if (!shouldArrowStick(projectile)) {
			return;
		}
		CompoundTag nbt = hit.getPersistentData();

		int slot = 0;
		if (nbt.contains("stuckArrowCountMF")) {
			slot = nbt.getInt("stuckArrowCountMF").orElse(0);
		}

		// TODO: ItemStack serialization requires HolderLookup.Provider in MC 26.x — stub stores item id only
		CompoundTag stuckArrow = new CompoundTag();
		stuckArrow.putString("id", arrow.getItem().builtInRegistryHolder().key().identifier().toString());
		stuckArrow.putInt("count", arrow.getCount());

		nbt.put("StuckArrowMF" + slot, stuckArrow);
		nbt.putInt("stuckArrowCountMF", slot + 1);
	}

	public static ItemStack getDroppedArrow(Entity arrow) {
		if (arrow instanceof IArrowRetrieve) {
			return ((IArrowRetrieve) arrow).getDroppedItem();
		}
		if (arrow.getPersistentData().contains("MF_ArrowItem")) {
			// TODO: deserialize ItemStack from persistent data when HolderLookup.Provider is available
			return new ItemStack(Items.ARROW);
		}
		return new ItemStack(Items.ARROW);
	}

	public static boolean shouldArrowStick(Entity projectile) {
		if (projectile instanceof IArrowRetrieve) {
			AbstractArrow.Pickup pickupStatus = ((IArrowRetrieve) projectile).canBePickedUp();
			return pickupStatus != AbstractArrow.Pickup.DISALLOWED;
		}
		if (projectile instanceof AbstractArrow arrow) {
			return arrow.pickup != AbstractArrow.Pickup.DISALLOWED;
		}
		return true;
	}

	public static List<ItemStack> getStuckArrows(Entity entity) {
		List<ItemStack> arrows = new ArrayList<>();
		CompoundTag nbt = entity.getPersistentData();

		int arrowCount = 0;
		if (nbt.contains("stuckArrowCountMF")) {
			arrowCount = nbt.getInt("stuckArrowCountMF").orElse(0);
		}

		if (arrowCount > 0) {
			for (int a = 0; a < arrowCount; a++) {
				// TODO: deserialize ItemStack from persistent data when HolderLookup.Provider is available
				arrows.add(new ItemStack(Items.ARROW));
			}
		}
		return arrows;
	}
}
