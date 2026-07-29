package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

/**
 * Placeholder tool tiers for materials without a vanilla {@link ToolMaterial} equivalent.
 * Stats are reasonable approximations (copper &lt; bronze &lt; steel, roughly stone/iron-ish),
 * not yet backed by MFR's real material-tier balance data (that lives in the unported
 * registry/material system) — revisit once that's ported.
 */
public class ModToolMaterials {

	public static final TagKey<Item> BRONZE_TOOL_MATERIALS = itemTag("bronze_tool_materials");
	public static final TagKey<Item> STEEL_TOOL_MATERIALS = itemTag("steel_tool_materials");

	public static final ToolMaterial BRONZE = new ToolMaterial(
			BlockTags.INCORRECT_FOR_STONE_TOOL, 200, 5.5F, 1.5F, 8, BRONZE_TOOL_MATERIALS);

	public static final ToolMaterial STEEL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_IRON_TOOL, 350, 7.0F, 2.5F, 12, STEEL_TOOL_MATERIALS);

	private static TagKey<Item> itemTag(String path) {
		return TagKey.create(net.minecraft.core.registries.Registries.ITEM,
				Identifier.fromNamespaceAndPath(MineFantasyReforged.MOD_ID, path));
	}
}
