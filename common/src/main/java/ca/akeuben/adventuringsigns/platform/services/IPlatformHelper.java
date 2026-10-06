package ca.akeuben.adventuringsigns.platform.services;

import net.minecraft.server.MinecraftServer;
import org.jspecify.annotations.Nullable;

public interface IPlatformHelper {

    @Nullable MinecraftServer getMinecraftServerInstance();
}