package minefantasy.mfr.registry.recipe.types;

import net.minecraft.util.StringRepresentable;

public enum QuernRecipeType implements StringRepresentable, IRecipeMFRType {
	QUERN_RECIPE,
	NONE;

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase();
	}

	public static QuernRecipeType deserialize(String name) {
		for (QuernRecipeType type : values()) {
			if (type.getSerializedName().equals(name)) return type;
		}
		return NONE;
	}

	@Override
	public QuernRecipeType getByNameWithModId(String name, String modId) {
		for (QuernRecipeType type : values()) {
			if ((modId + ":" + type.getSerializedName()).equals(name)) return type;
		}
		return NONE;
	}
}
