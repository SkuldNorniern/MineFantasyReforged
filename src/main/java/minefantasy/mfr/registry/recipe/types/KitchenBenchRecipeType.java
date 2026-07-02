package minefantasy.mfr.registry.recipe.types;

import net.minecraft.util.StringRepresentable;

public enum KitchenBenchRecipeType implements StringRepresentable, IRecipeMFRType {
	KITCHEN_BENCH_SHAPED_RECIPE,
	KITCHEN_BENCH_SHAPELESS_RECIPE,
	NONE;

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase();
	}

	public static KitchenBenchRecipeType deserialize(String name) {
		for (KitchenBenchRecipeType type : values()) {
			if (type.getSerializedName().equals(name)) return type;
		}
		return NONE;
	}

	@Override
	public KitchenBenchRecipeType getByNameWithModId(String name, String modId) {
		for (KitchenBenchRecipeType type : values()) {
			if ((modId + ":" + type.getSerializedName()).equals(name)) return type;
		}
		return NONE;
	}
}
