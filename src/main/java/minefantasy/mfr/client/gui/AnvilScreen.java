package minefantasy.mfr.client.gui;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.menu.AnvilMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

/**
 * Renders the plain anvil-crafting background; the stone-tier texture is used for all anvil
 * tiers for now (per-tier texture selection needs the block entity to expose its tier, which
 * the simplified {@code AnvilBlockEntity} doesn't track yet).
 */
public class AnvilScreen extends AbstractContainerScreen<AnvilMenu> {

	private static final Identifier TEXTURE =
			Identifier.fromNamespaceAndPath(MineFantasyReforged.MOD_ID, "textures/gui/anvil_stone.png");

	public AnvilScreen(AnvilMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title, 235, 210);
	}

	@Override
	protected void init() {
		super.init();
		this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
		super.extractBackground(graphics, mouseX, mouseY, partialTicks);
		int x = (this.width - this.imageWidth) / 2;
		int y = (this.height - this.imageHeight) / 2;
		graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
	}
}
