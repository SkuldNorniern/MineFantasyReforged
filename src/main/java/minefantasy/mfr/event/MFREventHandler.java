package minefantasy.mfr.event;

import minefantasy.mfr.MineFantasyReforged;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

@EventBusSubscriber(modid = MineFantasyReforged.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class MFREventHandler {
    // Port events from legacy MFREventHandler.java and event/ package
    // Split client-only events into a client-dist-only class annotated with Dist.CLIENT

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        // Port stamina, knowledge, and player data logic from legacy PlayerData
    }

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        // Port world load logic
    }
}
