package minefantasy.mfr.api.heating;

import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.util.CustomToolHelper;
import minefantasy.mfr.util.NbtUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class Heatable {
	public static final int FORGE_MAXIMUM_METAL_HEAT = 5000;
	public static final String NBT_Item = "mf_heatable_item_save";
	public static final String NBT_ShouldDisplay = "mf_heatable_display_temperature";
	public static final String NBT_CurrentTemp = "mf_heatable_temperature";
	public static final String NBT_WorkableTemp = "mf_heatable_work_temp";
	public static final String NBT_UnstableTemp = "mf_heatable_unstable_temp";

	public static boolean requiresHeating = true;
	public static HashMap<String, Heatable> registerList = new HashMap<>();

	private final int minTemperature;
	private final int unstableTemperature;
	private final int maxTemperature;
	protected ItemStack object;

	public Heatable(ItemStack item, int min, int unstable, int max) {
		this.object = item;
		this.minTemperature = min;
		this.unstableTemperature = unstable;
		this.maxTemperature = max;
	}

	public static void addItem(ItemStack item, int min, int unstable, int max) {
		registerList.put(getRegistrationForItem(item), new Heatable(item, min, unstable, max));
	}

	public static boolean canHeatItem(ItemStack item) {
		return loadStats(item) != null;
	}

	public static Heatable loadStats(ItemStack item) {
		if (item.isEmpty() || registerList.isEmpty()) return null;
		Heatable stats = findRegister(item);
		if (stats != null && stats.object.getItem() == item.getItem()) {
			return stats;
		}
		return null;
	}

	public static byte getHeatableStage(ItemStack item) {
		if (item.isEmpty() || !(item.getItem() instanceof IHotItem)) return 0;
		CompoundTag tag = NbtUtils.getOrCreateNBT(item);
		if (!tag.isEmpty()) {
			int temp = tag.getInt(NBT_CurrentTemp).orElse(0);
			int work = tag.getInt(NBT_WorkableTemp).orElse(0);
			int unstable = tag.getInt(NBT_UnstableTemp).orElse(0);
			if (temp > unstable) return (byte) 2;
			if (temp > work) return (byte) 1;
		}
		return (byte) 0;
	}

	public static int getWorkTemp(ItemStack item) {
		if (item.isEmpty() || !(item.getItem() instanceof IHotItem)) return 0;
		CompoundTag tag = NbtUtils.getOrCreateNBT(item);
		return tag.getInt(NBT_WorkableTemp).orElse(0);
	}

	public static int getUnstableTemp(ItemStack item) {
		if (item.isEmpty() || !(item.getItem() instanceof IHotItem)) return 0;
		CompoundTag tag = NbtUtils.getOrCreateNBT(item);
		return tag.getInt(NBT_UnstableTemp).orElse(0);
	}

	public static int getTemp(ItemStack item) {
		if (item.isEmpty() || !(item.getItem() instanceof IHotItem)) return 0;
		CompoundTag tag = NbtUtils.getOrCreateNBT(item);
		return tag.getInt(NBT_CurrentTemp).orElse(0);
	}

	public static ItemStack getQuenchedItem(ItemStack item, float hazard) {
		ItemStack cold = Heatable.getItemStack(item);
		// ConfigHardcore.HCCquenchRuin moved here from the legacy static field
		if (ConfigHardcore.HCCquenchRuin && cold.isDamageableItem() && hazard > 0) {
			cold.setDamageValue((int) (cold.getMaxDamage() * hazard / 100F));
		}
		return cold;
	}

	public static ItemStack getItemStack(ItemStack item) {
		if (item.isEmpty() || !(item.getItem() instanceof IHotItem)) return ItemStack.EMPTY;
		CompoundTag tag = NbtUtils.getOrCreateNBT(item);
		if (tag.contains(NBT_Item)) {
			// TODO: ItemStack.parseOptional() requires HolderLookup.Provider in MC 26
			// CompoundTag saved = tag.getCompound(NBT_Item).orElseGet(CompoundTag::new);
			// return ItemStack.parseOptional(provider, saved).orElse(ItemStack.EMPTY);
		}
		return ItemStack.EMPTY;
	}

	public static boolean isWorkable(ItemStack inputItem) {
		if (inputItem.isEmpty() || !(inputItem.getItem() instanceof IHotItem)) return true;
		return getHeatableStage(inputItem) == 1;
	}

	private static Heatable findRegister(ItemStack item) {
		String key = BuiltInRegistries.ITEM.getKey(item.getItem()).toString();
		Heatable specific = registerList.get(key + "_" + item.getDamageValue());
		if (specific != null) return specific;
		return registerList.get(key + "_any");
	}

	public static String getRegistrationForItem(ItemStack item) {
		String key = BuiltInRegistries.ITEM.getKey(item.getItem()).toString();
		return key + "_any";
	}

	public int getWorkableStat(ItemStack item) {
		if (this.minTemperature == -1) {
			// TODO: CustomMaterial / CustomMaterialRegistry not yet ported
			// CustomMaterial material = CustomToolHelper.getCustomPrimaryMaterial(item);
			// if (material != CustomMaterialRegistry.NONE) return material.getHeatableStats()[0];
		}
		return this.minTemperature;
	}

	public int getUnstableStat(ItemStack item) {
		if (this.unstableTemperature == -1) {
			// TODO: CustomMaterial / CustomMaterialRegistry not yet ported
		}
		return this.unstableTemperature;
	}
}
