package minefantasy.mfr.registry.recipe.types;

import net.minecraft.util.StringRepresentable;

public enum RecipeType implements StringRepresentable {
	ALLOY_RECIPES,
	ANVIL_RECIPES,
	BIG_FURNACE_RECIPES,
	BLAST_FURNACE_RECIPES,
	BLOOMERY_RECIPES,
	CARPENTER_RECIPES,
	KITCHEN_BENCH_RECIPES,
	QUERN_RECIPES,
	ROAST_RECIPES,
	SALVAGE_RECIPES,
	SPECIAL_RECIPES,
	TANNER_RECIPES,
	TRANSFORMATION_RECIPES,
	NONE;

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase();
	}

	public static RecipeType deserialize(String name) {
		for (RecipeType type : values()) {
			if (type.getSerializedName().equals(name)) return type;
		}
		return NONE;
	}

	public RecipeType getByNameWithModId(String name, String modId) {
		for (RecipeType type : values()) {
			if ((modId + ":" + type.getSerializedName()).equals(name)) return type;
		}
		return NONE;
	}
}
