package minefantasy.mfr.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent;

@OnlyIn(Dist.CLIENT)
public class ClientTickHandler {

	public static int pageFlipTicks = 0;
	public static int ticksInGame = 0;
	public static float partialTicks = 0;

	public static void notifyPageChange() {
		if (pageFlipTicks == 0)
			pageFlipTicks = 5;
	}

	@SubscribeEvent
	public void renderFramePre(RenderFrameEvent.Pre event) {
		partialTicks = event.getPartialTick().getGameTimeDeltaPartialTick(false);
	}

	@SubscribeEvent
	public void clientTickPost(ClientTickEvent.Post event) {
		Screen gui = Minecraft.getInstance().screen;
		if (gui == null || !gui.isPauseScreen()) {
			ticksInGame++;
		}
	}
}
