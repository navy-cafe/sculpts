package cafe.navy.sculpts;

import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.bedrock.paper.exception.ClientEntityException;
import cafe.navy.bedrock.paper.hologram.Hologram;
import cafe.navy.bedrock.paper.target.EntityTarget;
import cafe.navy.sculpts.render.SculptHitbox;
import cafe.navy.sculpts.skin.Skin;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Sculpt extends ClientEntity {

    private final @NonNull Hologram hologram;
    private final @NonNull SculptHitbox hitbox;
    private @Nullable Skin<?> skin;

    public Sculpt(final @NonNull UUID uuid,
                  final @NonNull Location location,
                  final @NonNull Hologram hologram) {
        super(uuid, location);
        this.skin = null;
        this.hologram = hologram;
        this.hitbox = new SculptHitbox(this);
    }

    public Sculpt(final @NonNull Location location,
                  final @NonNull Hologram hologram) {
        this(UUID.randomUUID(), location, hologram);
    }

    @Override
    public @NonNull List<ClientEntity> children() {
        final List<ClientEntity> children = new ArrayList<>();
        children.add(this.hitbox);
        children.add(this.hologram);
        if (this.skin != null) {
            children.add(this.skin);
        }
        return children;
    }

    @Override
    protected void show(@NonNull EntityTarget entityTarget) throws ClientEntityException {

    }

    @Override
    protected void hide(@NonNull EntityTarget entityTarget) throws ClientEntityException {

    }

    @Override
    public void update(final @NonNull EntityTarget target) throws ClientEntityException {
        for (final var child : this.children()) {
            child.remove(target);
        }

        for (final var child : this.children()) {
            child.add(target);
        }
    }

    @Override
    protected boolean isEntityIdPresent(final int id) {
        return this.hitbox.checkEntityId(id) || this.hologram.checkEntityId(id) || (this.skin != null && this.skin.checkEntityId(id));
    }

    @Override
    public void teleport(final @NonNull Location location) {
        this.hologram.teleport(location);
        this.hitbox.teleport(location.clone().add(0, (this.skin != null ? this.skin.getHeight() : 1), 0));
        if (this.skin != null) {
            this.skin.teleport(location);
        }
        this.update();
    }

    public void setSkin(final @Nullable Skin<?> skin) {
        if (this.skin != null) {
            this.skin.remove();
        }

        this.skin = skin;

        if (this.skin == null) {
            return;
        }

        final double height = this.skin.getHeight();
        this.hologram.teleport(this.location().add(0, height, 0));
        for (final var viewer : this.viewers()) {
            try {
                this.skin.add(viewer);
            } catch (ClientEntityException e) {
                throw new RuntimeException(e);
            }
        }
        this.update();
    }

}
