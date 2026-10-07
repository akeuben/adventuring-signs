package ca.akeuben.adventuringsigns.platform;

import ca.akeuben.adventuringsigns.platform.services.IPlatformHelper;
import net.minecraft.server.MinecraftServer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.event.server.ServerLifecycleEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jspecify.annotations.Nullable;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public @Nullable MinecraftServer getMinecraftServerInstance() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}