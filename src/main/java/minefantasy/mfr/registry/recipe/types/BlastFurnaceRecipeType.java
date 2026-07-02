package minefantasy.mfr.registry.recipe.types;

import net.minecraft.util.StringRepresentable;

public enum BlastFurnaceRecipeType implements StringRepresentable, IRecipeMFRType {
	BLAST_FURNACE_RECIPE,
	NONE;

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase();
	}

	public static BlastFurnaceRecipeType deserialize(String name) {
		for (BlastFurnaceRecipeType type : values()) {
			if (type.getSerializedName().equals(name)) return type;
		}
		return NONE;
	}

	@Override
	public BlastFurnaceRecipeType getByNameWithModId(String name, String modId) {
		for (BlastFurnaceRecipeType type : values()) {
			if ((modId + ":" + type.getSerializedName()).equals(name)) return type;
		}
		return NONE;
	}
}
