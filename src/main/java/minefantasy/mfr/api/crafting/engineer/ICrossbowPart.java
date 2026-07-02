package minefantasy.mfr.api.crafting.engineer;

import java.util.HashMap;

public interface ICrossbowPart {
	HashMap<String, ICrossbowPart> components = new HashMap<>();

	String getComponentType();

	int getID();

	String getUnlocalisedName();

	float getModifier(String type);

	boolean makesSmallWeapon();
}
