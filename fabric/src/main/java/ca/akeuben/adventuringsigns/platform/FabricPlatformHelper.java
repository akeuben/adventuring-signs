package ca.akeuben.adventuringsigns.platform;

import ca.akeuben.adventuringsigns.AdventuringSignsFabric;
import ca.akeuben.adventuringsigns.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import org.jspecify.annotations.Nullable;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public @Nullable MinecraftServer getMinecraftServerInstance() {
        return AdventuringSignsFabric.server;
    }
}
