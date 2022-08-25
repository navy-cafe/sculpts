package cafe.navy.sculpts.skin.entity;

import net.minecraft.world.entity.Entity;
import org.checkerframework.checker.nullness.qual.NonNull;

public class EntitySkinData {

    private final @NonNull Entity entity;

    public EntitySkinData(final @NonNull Entity entity) {
        this.entity = entity;
    }

    public @NonNull Entity entity() {
        return this.entity;
    }

}
