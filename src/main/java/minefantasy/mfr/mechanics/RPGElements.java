package minefantasy.mfr.mechanics;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.data.IStoredVariable;
import minefantasy.mfr.data.Persistence;
import minefantasy.mfr.data.PlayerData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RPGElements {

	public static final IStoredVariable<CompoundTag> SKILL_STATS_KEY =
			IStoredVariable.StoredVariable.ofNBT("skillStats", Persistence.ALWAYS).setSynced();

	public static boolean isSystemActive = true;
	public static float levelSpeedModifier = 1.0F;
	public static float levelUpModifier = 1.5F;
	public static ArrayList<Skill> skillsList = new ArrayList<>();
	private static final HashMap<String, Skill> skillsMap = new HashMap<>();

	static {
		PlayerData.registerStoredVariables(SKILL_STATS_KEY);
		skillsList.addAll(Arrays.asList(Skill.values()));
		for (Skill skill : Skill.values()) {
			skillsMap.put(skill.unlocalizedName, skill);
		}
	}

	public static CompoundTag getSkill(Player player, String skillname) {
		PlayerData data = PlayerData.get(player);
		CompoundTag nbt = data.getVariable(SKILL_STATS_KEY);
		if (nbt == null) {
			MineFantasyReforged.LOG.error("Skill Stats are null! This is bad, please report");
			return new CompoundTag();
		}
		return nbt.getCompound(skillname).orElseGet(CompoundTag::new);
	}

	public static void initSkills(PlayerData data) {
		if (data.getVariable(SKILL_STATS_KEY) == null) {
			CompoundTag nbt = new CompoundTag();
			for (Skill skill : skillsList) {
				if (skill != Skill.NONE) {
					CompoundTag tag = new CompoundTag();
					skill.init(tag);
					tag.putString("name", skill.unlocalizedName);
					nbt.put(skill.unlocalizedName, tag);
					MineFantasyReforged.LOG.info("Initiate skill: {}", skill.unlocalizedName);
				}
			}
			data.setVariable(SKILL_STATS_KEY, nbt);
		}
	}

	public static Skill getSkillByName(String name) {
		return skillsMap.get(name.toLowerCase());
	}

	public static int getLevel(Player player, Skill skill) {
		return getSkill(player, skill.unlocalizedName).getInt("level").orElse(0);
	}

	public static float getWeaponModifier(Player player, Skill skill) {
		int minSkill = 25;
		int level = getLevel(player, skill);
		if (level <= minSkill) return 1.0F;
		float progress = ((float) level - minSkill) / ((float) skill.getMaxLevel() - minSkill);
		return 1.0F + progress;
	}

	public static boolean hasLevel(Player player, Skill skill, int requirement) {
		return getLevel(player, skill) >= requirement;
	}
}
