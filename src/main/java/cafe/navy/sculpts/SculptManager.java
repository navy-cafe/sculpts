package cafe.navy.sculpts;

import cafe.navy.bedrock.paper.hologram.Hologram;
import cafe.navy.bedrock.paper.message.Message;
import cafe.navy.bedrock.paper.realm.Realm;
import cafe.navy.sculpts.skin.SkinType;
import org.bukkit.Location;
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

    public SculptManager(final @NonNull Realm realm) {
        this.realm = realm;
        this.sculpts = new HashMap<>();
        this.idIndex = new HashMap<>();
    }

    public @NonNull Sculpt createSculpt(final @NonNull Location location) {
        return this.createSculpt(null, location);
    }

    public @NonNull Sculpt createSculpt(final @Nullable String id,
                                        final @NonNull Location location) {
        final Sculpt sculpt = new Sculpt(location, new Hologram(location.add(0, 1, 0), List.of(Message.create("A new sculpt!"))));
        if (id != null) {
            this.idIndex.put(id, sculpt);
        }
        this.sculpts.put(sculpt.uuid(), sculpt);
        this.realm.add(sculpt);
        return sculpt;
    }

    public @NonNull Map<UUID, Sculpt> sculpts() {
        return Map.copyOf(this.sculpts);
    }

    public @NonNull Map<String, Sculpt> sculptsById() {
        return Map.copyOf(this.idIndex);
    }
}
