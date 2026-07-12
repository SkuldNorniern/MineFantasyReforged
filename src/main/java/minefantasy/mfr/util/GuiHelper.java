package minefantasy.mfr.util;

import minefantasy.mfr.constants.Tool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiHelper {

	public static void renderToolIcon(GuiGraphicsExtractor graphics, String toolType, int tier, int x, int y, boolean available, boolean button) {
		renderToolIcon(graphics, toolType, tier, x, y, false, available, button);
	}

	public static void renderToolIcon(GuiGraphicsExtractor graphics, String toolType, int tier, int x, int y, boolean outline,
			boolean available, boolean button) {
		int[] icon = getToolTypeIcon(toolType);
		if (button) {
			graphics.blit(RenderPipelines.GUI_TEXTURED, TextureHelperMFR.getResource("textures/gui/icons.png"),
					x, y, outline ? 20 : 0, 0, 20, 20, 256, 256);
		}
		graphics.blit(RenderPipelines.GUI_TEXTURED, TextureHelperMFR.getResource("textures/gui/icons.png"),
				x, y, icon[0], icon[1] + 20, 20, 20, 256, 256);
		if (tier > -1) {
			graphics.text(Minecraft.getInstance().font, "" + tier, x + 4, y + 10, 0xFFFFFF, true);
		}
	}

	public static boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY, int guiLeft, int guiTop) {
		pointX = pointX - guiLeft;
		pointY = pointY - guiTop;
		return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1;
	}

	public static int[] getToolTypeIcon(String s) {
		int width = 20;
		int height = 20;
		if (s.equalsIgnoreCase(Tool.HANDS.getName())) return new int[]{0, 0};
		if (s.equalsIgnoreCase(Tool.KNIFE.getName())) return new int[]{width, 0};
		if (s.equalsIgnoreCase(Tool.SAW.getName())) return new int[]{width * 2, 0};
		if (s.equalsIgnoreCase(Tool.MALLET.getName())) return new int[]{width * 3, 0};
		if (s.equalsIgnoreCase(Tool.NEEDLE.getName())) return new int[]{width * 4, 0};
		if (s.equalsIgnoreCase(Tool.HAMMER.getName())) return new int[]{width * 5, 0};
		if (s.equalsIgnoreCase(Tool.HEAVY_HAMMER.getName())) return new int[]{width * 6, 0};
		if (s.equalsIgnoreCase(Tool.SPOON.getName())) return new int[]{width * 7, 0};
		if (s.equalsIgnoreCase(Tool.SHEARS.getName())) return new int[]{width * 8, 0};
		if (s.equalsIgnoreCase(Tool.SPANNER.getName())) return new int[]{width * 9, 0};
		if (s.equalsIgnoreCase(Tool.BRUSH.getName())) return new int[]{width * 11, 0};
		if (s.equalsIgnoreCase("anvil")) return new int[]{0, height * 2};
		if (s.equalsIgnoreCase("carpenter")) return new int[]{width, height * 2};
		if (s.equalsIgnoreCase("tanner")) return new int[]{width * 2, height * 2};
		if (s.equalsIgnoreCase("kitchen_bench")) return new int[]{width * 3, height * 2};
		return new int[]{0, 0};
	}

	public static int getColourForRGB(int red, int green, int blue) {
		return (red << 16) + (green << 8) + blue;
	}

	public static void drawHotItemIcon(GuiGraphicsExtractor graphics, int x, int y) {
		graphics.blit(RenderPipelines.GUI_TEXTURED, TextureHelperMFR.getResource("textures/gui/knowledge/anvil_grid.png"),
				x, y, 248, 0, 8, 8, 256, 256);
	}
}
