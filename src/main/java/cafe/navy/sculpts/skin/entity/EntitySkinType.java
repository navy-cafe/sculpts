package cafe.navy.sculpts.skin.entity;

import cafe.navy.sculpts.Sculpt;
import cafe.navy.sculpts.skin.SkinType;
import net.kyori.adventure.text.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class EntitySkinType extends SkinType<EntitySkinData> {

    private final @NonNull EntityType<?> type;

    public EntitySkinType(final @NonNull EntityType<?> type) {
        this.type = type;
    }

    @Override
    public @NonNull Component name() {
        return Component.text("Entity - " + this.type.id);
    }

    @Override
    public @NonNull String id() {
        return "entity_" + this.type.id;
    }

    @Override
    public @NonNull List<Component> description() {
        return List.of(
                Component.text("An entity.")
        );
    }

    @Override
    public void apply(final @NonNull Sculpt sculpt,
                      final @NonNull EntitySkinData data) {
        sculpt.setSkin(new EntitySkin(sculpt, data));
    }

    @Override
    public @NonNull EntitySkinData createDefaultData(@NonNull Location location) {
        final Entity entity = this.type.create(((CraftWorld) location.getWorld()).getHandle());
        entity.teleportTo(location.getX(), location.getY(), location.getZ());
        return new EntitySkinData(entity);
    }
}
