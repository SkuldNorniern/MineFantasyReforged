package minefantasy.mfr.item;

import minefantasy.mfr.constants.MFRRarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemBaseMFR extends Item {

	private final MFRRarity mfrRarity;

	public ItemBaseMFR(Properties properties) {
		this(properties, MFRRarity.COMMON);
	}

	public ItemBaseMFR(Properties properties, MFRRarity rarity) {
		super(properties.rarity(rarity.toMCRarity()));
		this.mfrRarity = rarity;
	}

	public MFRRarity getMFRRarity() {
		return mfrRarity;
	}
}
