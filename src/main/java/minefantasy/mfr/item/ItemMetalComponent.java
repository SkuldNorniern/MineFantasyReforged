package minefantasy.mfr.item;

import minefantasy.mfr.registry.material.CustomMaterial;
import minefantasy.mfr.registry.material.CustomMaterialRegistry;
import minefantasy.mfr.registry.material.types.CustomMaterialType;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

/**
 * Ported from legacy {@code item/ItemMetalComponent.java} — metal-flavoured
 * {@link ItemComponentMFR} (bar, plate, metal hunk, ...). Dynamic per-material durability
 * ({@code setCanDamage}) is not ported: neither of the two component items using this class in
 * legacy ({@code bar}, {@code plate}) actually enabled it, and it needs a custom
 * {@code DataComponents.MAX_DAMAGE} override to work in MC 26 rather than the simple
 * {@code Item#getMaxDamage(ItemStack)} override legacy used.
 */
public class ItemMetalComponent extends ItemComponentMFR {

	private final float mass;

	public ItemMetalComponent(Properties properties, float mass) {
		super(properties, CustomMaterialType.METAL_MATERIAL);
		this.mass = mass;
	}

	public float getWeightInKg(ItemStack tool) {
		CustomMaterial base = getBase(tool);
		if (base != CustomMaterialRegistry.NONE) {
			return base.getDensity() * mass;
		}
		return mass;
	}

	public CustomMaterial getBase(ItemStack component) {
		return CustomToolHelper.getCustomPrimaryMaterial(component);
	}

	/** All registered metal materials, as a stack of this component item — for creative tab browsing. */
	public java.util.List<ItemStack> createAllVariants() {
		ArrayList<CustomMaterial> metals = CustomMaterialRegistry.getList(CustomMaterialType.METAL_MATERIAL);
		java.util.List<ItemStack> stacks = new ArrayList<>(metals.size());
		for (CustomMaterial metal : metals) {
			stacks.add(createComponentItemStack(metal.getName()));
		}
		return stacks;
	}

	public ItemStack createComponentItemStack(String base) {
		return createComponentItemStack(base, 1);
	}

	public ItemStack createComponentItemStack(String base, int count) {
		ItemStack item = new ItemStack(this, count);
		CustomMaterialRegistry.addMaterial(item, CustomToolHelper.slot_main, base);
		return item;
	}
}
