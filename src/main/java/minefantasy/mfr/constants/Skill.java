package minefantasy.mfr.constants;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public enum Skill {

	ARTISANRY("artisanry"),
	ENGINEERING("engineering"),
	CONSTRUCTION("construction"),
	PROVISIONING("provisioning"),
	COMBAT("combat"),
	NONE("none");

	Skill(String unlocalizedName) {
		this.unlocalizedName = unlocalizedName;
	}

	public final String unlocalizedName;

	public int getMaxLevel() {
		return 100;
	}

	public int getStartLevel() {
		return 1;
	}

	public static Skill fromName(String name) {
		for (Skill skill : values()) {
			if (skill.unlocalizedName.equals(name))
				return skill;
		}
		throw new IllegalArgumentException("No such skill with unlocalized name: " + name);
	}

	public int getLvlXP(int level) {
		// TODO: RPGElements.levelUpModifier not yet ported — using 1.0 as default
		float rise = 0.2F;
		return (int) Math.floor(10F * (1.0F + (rise * (level - 1))));
	}

	// TODO: getXP — depends on RPGElements (not yet ported)
	public int[] getXP(Player player) {
		return new int[] {0, 0};
	}

	// TODO: manualLvlUp — depends on RPGElements (not yet ported)
	public void manualLvlUp(Player player, int newLevel) {
	}

	// TODO: addXP — depends on RPGElements and PlayerData (not yet ported)
	public void addXP(Player player, int xp) {
	}

	public void init(CompoundTag tag) {
		int start = getStartLevel();
		tag.putInt("level", start);
		tag.putInt("xp", 0);
		tag.putInt("xpMax", getLvlXP(start));
	}

	@OnlyIn(Dist.CLIENT)
	public String getDisplayName() {
		return net.minecraft.client.resources.language.I18n.get("skill." + unlocalizedName + ".name");
	}
}
