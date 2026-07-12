package minefantasy.mfr.util;

import com.google.common.collect.Maps;
import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Map;

// TODO: renderEffect and renderModel require a full rewrite for the MC 26 rendering pipeline
//       (PoseStack, MultiBufferSource, BakedModel.getQuads — no longer same signature)
@OnlyIn(Dist.CLIENT)
public class TextureHelperMFR {
	public static final Identifier ITEM_GLINT = Identifier.parse("textures/misc/enchanted_item_glint.png");
	private static final Map<String, Identifier> resourceList = Maps.newHashMap();

	public static Identifier getResource(String directory) {
		return resourceList.computeIfAbsent(directory, d -> {
			MFRLogUtil.logDebug("MineFantasy: Added Resource: " + d);
			return Identifier.fromNamespaceAndPath(MineFantasyReforged.MOD_ID, d);
		});
	}
}
