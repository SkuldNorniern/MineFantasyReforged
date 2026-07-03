package minefantasy.mfr.api.mining;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.Random;

public class RandomOre {
	public static ArrayList<RandomOre> drops = new ArrayList<>();

	private final ItemStack loot;
	private final float chanceToDrop;
	private final Block block;
	private final int harvestLvl;
	private final int minHeight;
	private final int maxHeight;
	private final boolean doesSilktouchDisable;
	private final String research;

	public RandomOre(ItemStack drop, float chance, Block base, int harvestLevel, int min, int max, boolean silkDisable) {
		this(drop, chance, base, harvestLevel, min, max, silkDisable, null);
	}

	public RandomOre(ItemStack drop, float chance, Block base, int harvestLevel, int min, int max, boolean silkDisable, String research) {
		doesSilktouchDisable = silkDisable;
		minHeight = min;
		maxHeight = max;
		loot = drop;
		chanceToDrop = chance;
		harvestLvl = harvestLevel;
		block = base;
		this.research = research;
	}

	// TODO: addOre(ItemStack, float, String oreDict, ...) — OreDict removed; use block tags instead

	public static void addOre(ItemStack drop, float chance, Block block, int harvestLevel, int min, int max, boolean silkDisable) {
		drops.add(new RandomOre(drop, chance / 100F, block, harvestLevel, min, max, silkDisable));
	}

	public static void addOre(ItemStack drop, float chance, Block block, int harvestLevel, int min, int max, boolean silkDisable, String research) {
		drops.add(new RandomOre(drop, chance / 100F, block, harvestLevel, min, max, silkDisable, research));
	}

	public static ArrayList<ItemStack> getDroppedItems(LivingEntity user, Block base, int harvest, int fortune, boolean silktouch, int y) {
		ArrayList<ItemStack> loot = new ArrayList<>();

		if (!drops.isEmpty()) {
			for (RandomOre ore : drops) {
				if (matchesOre(user, ore, base, harvest, fortune / 2F + 1F, silktouch, y)) {
					loot.add(ore.loot);
				}
			}
		}

		return loot;
	}

	private static boolean matchesOre(LivingEntity user, RandomOre ore, Block base, int harvest, float multiplier, boolean silktouch, int y) {
		Random random = new Random();

		if (ore.doesSilktouchDisable && silktouch) {
			return false;
		}
		if (user instanceof Player && ore.research != null) {
			// TODO: ResearchLogic.hasInfoUnlocked not yet ported — skipping research check
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
