package minefantasy.mfr;

import com.mojang.logging.LogUtils;
import minefantasy.mfr.config.MFRConfig;
import minefantasy.mfr.init.ModBlockEntities;
import minefantasy.mfr.init.ModBlocks;
import minefantasy.mfr.init.ModAttachmentTypes;
import minefantasy.mfr.init.ModCreativeTabs;
import minefantasy.mfr.init.ModDataComponents;
import minefantasy.mfr.init.ModIngredientTypes;
import minefantasy.mfr.init.ModItems;
import minefantasy.mfr.init.ModMaterials;
import minefantasy.mfr.init.ModMenuTypes;
import minefantasy.mfr.init.ModRecipeSerializers;
import minefantasy.mfr.init.ModRecipeTypes;
import minefantasy.mfr.init.ModSounds;
import minefantasy.mfr.event.MFREventHandler;
import minefantasy.mfr.network.MFRNetwork;
import minefantasy.mfr.registry.material.factories.CustomMaterialFactory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(MineFantasyReforged.MOD_ID)
public class MineFantasyReforged {
    public static final String MOD_ID = "minefantasyreforged";
    public static final Logger LOG = LogUtils.getLogger();

    public MineFantasyReforged(IEventBus modEventBus, ModContainer modContainer) {
        ModMaterials.initBaseMaterials();
        ModMaterials.initLeatherMaterials();

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModSounds.SOUNDS.register(modEventBus);
        ModCreativeTabs.CREATIVE_TABS.register(modEventBus);
        ModAttachmentTypes.ATTACHMENT_TYPES.register(modEventBus);
        ModDataComponents.DATA_COMPONENTS.register(modEventBus);
        ModRecipeTypes.RECIPE_TYPES.register(modEventBus);
        ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        ModMenuTypes.MENU_TYPES.register(modEventBus);
        ModIngredientTypes.INGREDIENT_TYPES.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.SERVER, MFRConfig.SERVER_SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, MFRConfig.CLIENT_SPEC);

        MFRNetwork.register(modEventBus);

        // Runs after all DeferredRegisters have populated their holders — CustomMaterialFactory
        // resolves item ingredients via ModItems.XXX.get(), which isn't safe any earlier.
        modEventBus.addListener(this::onCommonSetup);

        NeoForge.EVENT_BUS.register(MFREventHandler.class);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        CustomMaterialFactory.load();
    }
}
