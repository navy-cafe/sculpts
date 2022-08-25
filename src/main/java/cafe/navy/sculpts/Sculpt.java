package cafe.navy.sculpts;

import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.sculpts.skin.Skin;
import cafe.navy.sculpts.skin.stand.StandSkin;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;

/**
 * {@code Sculpt}
 */
public class Sculpt {

    private final @NonNull UUID uuid;
    private final @Nullable String id;
    private @NonNull Location location;
    private @NonNull List<String> hologramLines;
    private @Nullable Skin skin;
    private @Nullable SculptEntity entity;

    public Sculpt(final @NonNull UUID uuid,
                  final @NonNull Location location) {
        this(uuid, location, null, null, null);
    }

    public Sculpt(final @NonNull UUID uuid,
                  final @NonNull Location location,
                  final @NonNull String id) {
        this(uuid, location, id, null, null);
    }

    public Sculpt(final @NonNull UUID uuid,
                  final @NonNull Location location,
                  final @Nullable String id,
                  final @Nullable List<String> hologramLines,
                  final @Nullable Skin skin) {
        this.uuid = uuid;
        this.location = location;
        this.id = id;
        this.hologramLines = new ArrayList<>();
        if (hologramLines != null) {
            this.hologramLines.addAll(hologramLines);
        }
        this.skin = Objects.requireNonNullElse(skin, new StandSkin(this));
    }

    public @NonNull Location location() {
        return this.location;
    }

    public @NonNull UUID uuid() {
        return this.uuid;
    }

    public @NonNull Optional<@NonNull String> id() {
        return Optional.ofNullable(this.id);
    }

    public @NonNull List<String> hologramLines() {
        return List.copyOf(this.hologramLines);
    }

    public @NonNull Optional<Skin> skin() {
        return Optional.ofNullable(this.skin);
    }

    public void setSkin(final @NonNull Skin skin) {
        this.skin = skin;

        if (this.entity != null) {
            this.entity.setSkin(skin);
        }
    }

    public void setEntity(final @NonNull SculptEntity entity) {
        this.entity = entity;
        if (this.skin != null) {
            this.entity.setSkin(this.skin);
        }
    }

    public boolean hasEntity() {
        return this.entity != null;
    }

    public @NonNull SculptEntity getEntity() {
        Objects.requireNonNull(this.entity);
        return this.entity;
    }

}
