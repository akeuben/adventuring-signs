package ca.akeuben.adventuringsigns;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface AdventuringSign {

    @NotNull String adventuringsigns$getLine(int line);

    boolean isLineCustom(int line);

    void adventuringsigns$setLine(int line, @Nullable String content);

    default boolean isAdventuring() {
        for(int i = 0; i < 4; i++) {
            if(isLineCustom(i))
                return true;
        }
        return false;
    }

    Level getLevel();

    BlockPos getBlockPos();
}
