package minefantasy.mfr.api.mining;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Random;

public class RandomDigs {
	public static ArrayList<RandomDigs> drops = new ArrayList<>();

	private final ItemStack loot;
	private final float chanceToDrop;
	private final Block block;
	private final int harvestLvl;
	private final int minHeight;
	private final int maxHeight;
	private final boolean doesSilktouchDisable;

	public RandomDigs(ItemStack drop, float chance, Block base, int harvestLevel, int min, int max, boolean silkDisable) {
		doesSilktouchDisable = silkDisable;
		minHeight = min;
		maxHeight = max;
		loot = drop;
		chanceToDrop = chance;
		harvestLvl = harvestLevel;
		block = base;
	}

	public static void addOre(ItemStack drop, float chance, Block block, int harvestLevel, int min, int max, boolean silkDisable) {
		drops.add(new RandomDigs(drop, chance / 100F, block, harvestLevel, min, max, silkDisable));
	}

	// TODO: addOre(ItemStack, float, Block, int meta, ...) — block metadata removed in modern MC
	// Use addOre(ItemStack, float, Block, int, int, int, boolean) instead

	public static ArrayList<ItemStack> getDroppedItems(Block base, int harvest, int fortune, boolean silktouch, int y) {
		ArrayList<ItemStack> loot = new ArrayList<>();

		if (!drops.isEmpty()) {
			for (RandomDigs ore : drops) {
				if (matchesOre(ore, base, harvest, fortune / 2F + 1F, silktouch, y)) {
					loot.add(ore.loot);
				}
			}
		}

		return loot;
	}

	private static boolean matchesOre(RandomDigs ore, Block base, int harvest, float multiplier, boolean silktouch, int y) {
		Random random = new Random();

		if (ore.doesSilktouchDisable && silktouch) {
			return false;
		}

		if (!(ore.minHeight == -1 && ore.maxHeight == -1)) {
			if (y < ore.minHeight || y > ore.maxHeight) {
				return false;
			}
		}
		if (ore.block != base) {
			return false;
		}
		if (ore.harvestLvl > harvest) {
			return false;
		}
		return random.nextFloat() < (ore.chanceToDrop * multiplier);
	}
}
