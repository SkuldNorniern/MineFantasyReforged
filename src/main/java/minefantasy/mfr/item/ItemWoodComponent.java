package minefantasy.mfr.item;

import minefantasy.mfr.registry.material.CustomMaterial;
import minefantasy.mfr.registry.material.CustomMaterialRegistry;
import minefantasy.mfr.registry.material.types.CustomMaterialType;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/** Ported from legacy {@code item/ItemWoodComponent.java} — wood-flavoured {@link ItemComponentMFR} (timber, ...). */
public class ItemWoodComponent extends ItemComponentMFR {

	public ItemWoodComponent(Properties properties) {
		super(properties, CustomMaterialType.WOOD_MATERIAL);
	}

	/** All registered wood materials, as a stack of this component item — for creative tab browsing. */
	public List<ItemStack> createAllVariants() {
		ArrayList<CustomMaterial> woods = CustomMaterialRegistry.getList(CustomMaterialType.WOOD_MATERIAL);
		List<ItemStack> stacks = new ArrayList<>(woods.size());
		for (CustomMaterial wood : woods) {
			stacks.add(createComponentItemStack(wood.getName()));
		}
		return stacks;
	}

	public ItemStack createComponentItemStack(String base) {
		ItemStack item = new ItemStack(this, 1);
		CustomMaterialRegistry.addMaterial(item, CustomToolHelper.slot_main, base);
		return item;
	}
}
