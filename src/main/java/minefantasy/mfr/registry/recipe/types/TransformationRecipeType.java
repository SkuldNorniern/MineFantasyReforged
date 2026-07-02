package minefantasy.mfr.registry.recipe.types;

import net.minecraft.util.StringRepresentable;

public enum TransformationRecipeType implements StringRepresentable, IRecipeMFRType {
	TRANSFORMATION_RECIPE,
	TRANSFORMATION_RECIPE_BLOCKSTATE,
	TRANSFORMATION_RECIPE_PROGRESSIVE,
	NONE;

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase();
	}

	public static TransformationRecipeType deserialize(String name) {
		for (TransformationRecipeType type : values()) {
			if (type.getSerializedName().equals(name)) return type;
		}
		return NONE;
	}

	@Override
	public TransformationRecipeType getByNameWithModId(String name, String modId) {
		for (TransformationRecipeType type : values()) {
			if ((modId + ":" + type.getSerializedName()).equals(name)) return type;
		}
		return NONE;
	}
}
