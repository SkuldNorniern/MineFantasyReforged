package minefantasy.mfr.registry.material;

import minefantasy.mfr.constants.MFRRarity;
import minefantasy.mfr.registry.material.types.CustomMaterialType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class CustomMaterialRegistry {

	private static final HashMap<String, CustomMaterial> CUSTOM_MATERIALS = new HashMap<>();

	public static HashMap<CustomMaterialType, ArrayList<CustomMaterial>> TYPE_LIST = new HashMap<>();

	public static final CustomMaterial NONE = new CustomMaterial("none", CustomMaterialType.NONE,
			Ingredient.EMPTY, new int[] {237, 237, 237},
			0F, 0F, 0F, 0F, 0F, 0F, 0, MFRRarity.COMMON,
			0, 0, null, null, null, null, false);

	private static final String NBT_BASE = "mf_custom_materials";
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

	public static final CustomMaterialRegistry INSTANCE = new CustomMaterialRegistry();

	public static void init() {
		// triggers static initialization at the right time
	}

	public static void addMaterial(CustomMaterial material) {
		String key = material.getName().toLowerCase();
		if (!CUSTOM_MATERIALS.containsKey(key)) {
			CUSTOM_MATERIALS.put(key, material);
			getList(material.getType()).add(material);
		}
	}

	public static Collection<CustomMaterial> getValues() {
		return CUSTOM_MATERIALS.values();
	}

	public static CustomMaterial getMaterial(String name) {
		if (name == null) return NONE;
		CustomMaterial result = CUSTOM_MATERIALS.get(name.toLowerCase());
		return result != null ? result : NONE;
	}

	public static ArrayList<CustomMaterial> getList(CustomMaterialType type) {
		TYPE_LIST.computeIfAbsent(type, k -> new ArrayList<>());
		return TYPE_LIST.get(type);
	}

	public static void addMaterial(ItemStack item, String slot, String material) {
		if (material == null || material.isEmpty()) return;
		CompoundTag root = readRoot(item);
		CompoundTag base = root.contains(NBT_BASE) ? root.getCompound(NBT_BASE) : new CompoundTag();
		base.putString(slot, material);
		root.put(NBT_BASE, base);
		item.set(DataComponents.CUSTOM_DATA, CustomData.of(root));
	}

	public static void addMaterial(ItemStack item, String slot, CustomMaterial material) {
		if (material == null || material.getName().isEmpty()) return;
		addMaterial(item, slot, material.getName());
	}

	public static CustomMaterial getMaterialFor(ItemStack item, String slot) {
		CompoundTag base = readBase(item);
		if (base != null && base.contains(slot)) {
			return getMaterial(base.getString(slot));
		}
		return NONE;
	}

	/** Returns a read-only copy of the base NBT compound, or null if absent. */
	@Nullable
	public static CompoundTag getNBT(ItemStack item) {
		return readBase(item);
	}

	private static CompoundTag readRoot(ItemStack item) {
		CustomData data = item.get(DataComponents.CUSTOM_DATA);
		return data != null ? data.copyTag() : new CompoundTag();
	}

	@Nullable
	private static CompoundTag readBase(ItemStack item) {
		CustomData data = item.get(DataComponents.CUSTOM_DATA);
		if (data == null) return null;
		CompoundTag root = data.copyTag();
		return root.contains(NBT_BASE) ? root.getCompound(NBT_BASE) : null;
	}

	@OnlyIn(Dist.CLIENT)
	public static String getWeightString(float mass) {
		String key = "attribute.weightKg.name";
		if (mass > 0 && mass < 1.0F) {
			key = "attribute.weightg.name";
			mass = (int) (mass * 1000F);
		} else if (mass > 1000) {
			key = "attribute.weightt.name";
			mass = (int) (mass / 1000F);
		}
		return Component.translatable(key, DECIMAL_FORMAT.format(mass)).getString();
	}
}
