package minefantasy.mfr.init;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(value = Dist.CLIENT)
public class ModKeybindings {

	public static final KeyMapping.Category CATEGORY_GENERAL =
			new KeyMapping.Category(Identifier.fromNamespaceAndPath("mfr", "general"));

	public static final KeyMapping RELOAD_MENU = new KeyMapping(
			"key.mfr.reload_menu",
			KeyConflictContext.IN_GAME,
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_LEFT_SHIFT,
			CATEGORY_GENERAL);

	public static final KeyMapping REMOVE_OFFHAND = new KeyMapping(
			"key.mfr.remove_offhand",
			KeyConflictContext.IN_GAME,
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_G,
			CATEGORY_GENERAL);

	@SubscribeEvent
	public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
		event.registerCategory(CATEGORY_GENERAL);
		event.register(RELOAD_MENU);
		event.register(REMOVE_OFFHAND);
	}
}
