package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.menu.AnvilMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {

	public static final DeferredRegister<MenuType<?>> MENU_TYPES =
			DeferredRegister.create(Registries.MENU, MineFantasyReforged.MOD_ID);

	public static final DeferredHolder<MenuType<?>, MenuType<AnvilMenu>> ANVIL =
			MENU_TYPES.register("anvil", () -> IMenuTypeExtension.create(AnvilMenu::new));
}
