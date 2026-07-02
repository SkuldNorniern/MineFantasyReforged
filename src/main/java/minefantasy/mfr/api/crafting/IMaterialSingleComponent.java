package minefantasy.mfr.api.crafting;

import minefantasy.mfr.registry.material.types.CustomMaterialType;

public interface IMaterialSingleComponent extends IMaterialComponent {
	CustomMaterialType getMaterialType();
}
