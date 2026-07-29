package minefantasy.mfr.registry.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import minefantasy.mfr.constants.Skill;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

/** Shape-matched alloy recipe (crucible), reusing vanilla's {@link ShapedRecipePattern} for the up-to-3x3 match. */
public class AlloyShapedRecipe extends AlloyRecipe {

	public static final MapCodec<AlloyShapedRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
					Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
					ShapedRecipePattern.MAP_CODEC.forGetter(o -> o.pattern),
					ItemStackTemplate.CODEC.fieldOf("result").forGetter(o -> o.result),
					Codec.INT.optionalFieldOf("tier", 0).forGetter(AlloyRecipe::getTier),
					Codec.STRING.optionalFieldOf("research", "none").forGetter(AlloyRecipe::getRequiredResearch),
					Skill.CODEC.optionalFieldOf("skill", Skill.NONE).forGetter(o -> o.skill),
					Codec.INT.optionalFieldOf("skill_xp", 0).forGetter(AlloyRecipe::getSkillXp),
					Codec.FLOAT.optionalFieldOf("vanilla_xp", 0.0F).forGetter(AlloyRecipe::getVanillaXp))
			.apply(i, AlloyShapedRecipe::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, AlloyShapedRecipe> STREAM_CODEC =
			ByteBufCodecs.fromCodecWithRegistries(MAP_CODEC.codec());

	public static final RecipeSerializer<AlloyShapedRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

	private final ShapedRecipePattern pattern;

	public AlloyShapedRecipe(Recipe.CommonInfo commonInfo, ShapedRecipePattern pattern, ItemStackTemplate result,
			int tier, String requiredResearch, @Nullable Skill skill, int skillXp, float vanillaXp) {
		super(commonInfo, result, tier, requiredResearch, skill, skillXp, vanillaXp);
		this.pattern = pattern;
	}

	@Override
	public boolean matches(CraftingInput input, Level level) {
		return pattern.matches(input);
	}

	@Override
	public RecipeSerializer<AlloyShapedRecipe> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public PlacementInfo placementInfo() {
		return PlacementInfo.createFromOptionals(pattern.ingredients());
	}

	public int getWidth() {
		return pattern.width();
	}

	public int getHeight() {
		return pattern.height();
	}
}
