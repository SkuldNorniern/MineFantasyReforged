package minefantasy.mfr.network;

import minefantasy.mfr.MineFantasyReforged;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class MFRNetwork {
    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(MFRNetwork::registerPayloads);
    }

    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MineFantasyReforged.MOD_ID);
        // Port network packets from legacy NetworkHandler.java
        // Each old SimpleNetworkWrapper packet becomes a CustomPacketPayload implementation
    }
}
