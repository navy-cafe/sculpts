package cafe.navy.sculpts;

import cafe.navy.bedrock.paper.realm.Realm;
import cafe.navy.sculpts.tools.ToolType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SculptManager {

    private final @NonNull Realm realm;
    private final @NonNull Map<UUID, Sculpt> sculpts;
    private final @NonNull Map<String, Sculpt> idIndex;
    private final @NonNull Map<UUID, SculptEntity> entities;

    public SculptManager(final @NonNull Realm realm) {
        this.realm = realm;
        this.entities = new HashMap<>();
        this.sculpts = new HashMap<>();
        this.idIndex = new HashMap<>();
    }

    public @NonNull Realm realm() {
        return this.realm;
    }

    public @NonNull Sculpt createSculpt(final @NonNull Location location) {
        return this.createSculpt(null, location);
    }

    public @NonNull Sculpt createSculpt(final @Nullable String id,
                                        final @NonNull Location location) {
        final Sculpt sculpt = new Sculpt(UUID.randomUUID(), location);
        if (id != null) {
            this.idIndex.put(id, sculpt);
        }
        this.sculpts.put(sculpt.uuid(), sculpt);

        final SculptEntity entity = new SculptEntity(sculpt);
        sculpt.setEntity(entity);
        this.realm.add(entity);

        return sculpt;
    }

    public void removeSculpt(final @NonNull Sculpt sculpt) {
        this.realm.remove(this.entities.get(sculpt.uuid()));
        this.entities.remove(sculpt.uuid());
        if (sculpt.id().isPresent()) {
            this.idIndex.remove(sculpt.id().get());
        }
        this.sculpts.remove(sculpt.uuid());
    }

    public @NonNull List<Sculpt> sculpts() {
        return List.copyOf(this.sculpts.values());
    }

    public @NonNull Map<UUID, Sculpt> sculptsByUuid() {
        return Map.copyOf(this.sculpts);
    }

    public @NonNull Map<String, Sculpt> sculptsById() {
        return Map.copyOf(this.idIndex);
    }
}
