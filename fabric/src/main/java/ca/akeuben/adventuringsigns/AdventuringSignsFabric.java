package ca.akeuben.adventuringsigns;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class AdventuringSignsFabric implements ModInitializer {

    public static MinecraftServer server;

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            AdventuringSignsFabric.server = server;
        });
        ServerLifecycleEvents.SERVER_STOPPED.register(_ -> {
            AdventuringSignsFabric.server = null;
        });

        Constants.LOG.info("Initialized Adventuring Signs for Fabric!");
    }
}
