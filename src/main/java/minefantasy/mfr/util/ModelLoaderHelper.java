package minefantasy.mfr.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

// TODO: Model registration API changed completely in MC 1.18+. The old ForgeModelLoader/CodeChickenLib
//       pattern no longer applies. Reimplement using NeoForge client extension providers and
//       IGeometryLoader when model registration is needed.
@OnlyIn(Dist.CLIENT)
public class ModelLoaderHelper {

	private ModelLoaderHelper() {}

	public static void registerItem(Item item) {}

	public static void registerItem(Item item, String prefix) {}

	public static void registerItem(Item item, String prefix, String variant) {}

	public static void registerItem(Block block, String prefix, String variant) {}

	public static void registerItem(Item item, String prefix, boolean metaSuffix, String variant) {}
}
