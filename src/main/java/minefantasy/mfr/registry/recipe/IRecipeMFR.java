package minefantasy.mfr.registry.recipe;

import minefantasy.mfr.constants.Skill;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

public interface IRecipeMFR {
	String getRequiredResearch();

	@Nullable Skill getSkill();

	int getSkillXp();

	boolean shouldSlotGiveSkillXp();

	float getVanillaXp();

	default void giveSkillXp(Player player, float baseXp) {
		Skill skill = getSkill();
		if (skill != null && skill != Skill.NONE) {
			skill.addXP(player, (int) (baseXp + getSkillXp()));
		}
	}

	default void giveSkillXpPerCount(Player player, float baseXP, int count) {
		Skill skill = getSkill();
		if (skill == null || skill == Skill.NONE) {
			return;
		}

		float baseXp = ((float) getSkillXp() / 10) + baseXP;
		int amount = resolveSplitCount(baseXp, count);
		if (amount > 0) {
			skill.addXP(player, amount);
		}
	}

	default void giveVanillaXp(Player player, float baseXP, int count) {
		int amount = resolveSplitCount(getVanillaXp() + baseXP, count);
		if (amount > 0 && player.level() instanceof ServerLevel serverLevel) {
			ExperienceOrb.award(serverLevel, player.position().add(0, 0.5, 0), amount);
		}
	}

	private static int resolveSplitCount(float xpPerItem, int count) {
		if (xpPerItem == 0.0F) {
			return 0;
		}
		if (xpPerItem < 1.0F) {
			int floor = Mth.floor((float) count * xpPerItem);
			if (floor < Mth.ceil((float) count * xpPerItem)
					&& Math.random() < (double) ((float) count * xpPerItem - (float) floor)) {
				++floor;
			}
			return floor;
		}
		return count;
	}
}
