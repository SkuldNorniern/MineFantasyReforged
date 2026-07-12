package minefantasy.mfr.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class PlayerUtils {

	private PlayerUtils() {}

	public static void giveStackToPlayer(Player player, ItemStack itemToGive) {
		if (!player.getInventory().add(itemToGive)) {
			player.drop(itemToGive, false);
		}
	}

	public static int getSlotFor(Player player, ItemStack stack) {
		List<ItemStack> main = player.getInventory().getNonEquipmentItems();
		for (int i = 0; i < main.size(); i++) {
			if (!main.get(i).isEmpty() && areStackSameIgnoreNBT(stack, main.get(i))) {
				return i;
			}
		}
		return -1;
	}

	public static boolean playerInventoryHasIngredient(Inventory playerInventory, Ingredient ingredient) {
		for (ItemStack stack : playerInventory.getNonEquipmentItems()) {
			if (ingredient.test(stack)) return true;
		}
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (ingredient.test(playerInventory.player.getItemBySlot(slot))) return true;
		}
		return false;
	}

	private static boolean areStackSameIgnoreNBT(ItemStack stack1, ItemStack stack2) {
		return ItemStack.isSameItem(stack1, stack2);
	}

	// TODO: requires a custom UseAnim registered via NeoForge extensible enum
	public static boolean shouldItemStackBlock(ItemStack stack, ItemStack offhand) {
		return false;
	}
}
