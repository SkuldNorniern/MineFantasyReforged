package minefantasy.mfr.item;

import minefantasy.mfr.api.crafting.IMaterialSingleComponent;
import minefantasy.mfr.constants.MFRRarity;
import minefantasy.mfr.registry.material.types.CustomMaterialType;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.world.item.ItemStack;

/**
 * Ported from legacy {@code item/ItemComponentMFR.java} — a generic material-carrying "commodity"
 * item (bar, plate, metal hunk, ...), as opposed to a fixed per-material item like
 * {@code copper_ingot}. The material is stored on the {@link ItemStack} via
 * {@link minefantasy.mfr.registry.material.CustomMaterialRegistry}, same NBT key as legacy.
 * <p>
 * Not yet ported: the in-world "place as a storage block" interaction ({@code onItemUseFirst} in
 * legacy, driven by {@code setStoragePlacement}) — depends on the unported {@code block/BlockComponent}
 * and {@code tile/TileEntityComponent}. Also not yet ported: material-substituted display names
 * (legacy's {@code getItemStackDisplayName}/{@code addInformation} used a client-only I18n helper
 * that can't safely back {@code Item#getName}, which must work server-side too) — items currently
 * show their raw (untranslated-arg) lang entry until that's revisited.
 */
public class ItemComponentMFR extends ItemBaseMFR implements IMaterialSingleComponent {

	private final CustomMaterialType materialType;
	private float unitCount = 1;
	private boolean isCustom = false;

	public ItemComponentMFR(Properties properties) {
		this(properties, MFRRarity.COMMON, CustomMaterialType.NONE);
	}

	public ItemComponentMFR(Properties properties, CustomMaterialType type) {
		this(properties, MFRRarity.COMMON, type);
	}

	public ItemComponentMFR(Properties properties, MFRRarity rarity, CustomMaterialType type) {
		super(properties, rarity);
		this.materialType = type;
	}

	public ItemComponentMFR setCustom(float units) {
		this.unitCount = units;
		this.isCustom = true;
		return this;
	}

	protected float getWeightModifier(ItemStack stack) {
		return CustomToolHelper.getWeightModifier(stack, 1.0F);
	}

	@Override
	public CustomMaterialType getMaterialType() {
		return materialType;
	}

	public boolean isCustom() {
		return isCustom;
	}

	public float getUnitCount() {
		return unitCount;
	}
}
