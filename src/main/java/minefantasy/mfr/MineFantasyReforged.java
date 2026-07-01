package minefantasy.mfr;

import com.mojang.logging.LogUtils;
import minefantasy.mfr.config.MFRConfig;
import minefantasy.mfr.init.ModBlockEntities;
import minefantasy.mfr.init.ModBlocks;
import minefantasy.mfr.init.ModItems;
import minefantasy.mfr.init.ModSounds;
import minefantasy.mfr.event.MFREventHandler;
import minefantasy.mfr.network.MFRNetwork;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(MineFantasyReforged.MOD_ID)
public class MineFantasyReforged {
    public static final String MOD_ID = "minefantasyreforged";
    public static final Logger LOG = LogUtils.getLogger();

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB =
            CREATIVE_MODE_TABS.register("minefantasyreforged", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.minefantasyreforged"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.STEEL_INGOT.get().getDefaultInstance())
                    .displayItems((params, output) -> {
                        // Items added as they are ported
                        ModItems.ITEMS.getEntries().forEach(entry -> output.accept(entry.get()));
                    })
                    .build());

    public MineFantasyReforged(IEventBus modEventBus, ModContainer modContainer) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModSounds.SOUNDS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.SERVER, MFRConfig.SERVER_SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, MFRConfig.CLIENT_SPEC);

        MFRNetwork.register(modEventBus);

        NeoForge.EVENT_BUS.register(MFREventHandler.class);
    }
}
