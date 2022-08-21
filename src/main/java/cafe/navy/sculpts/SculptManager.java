package cafe.navy.sculpts;

import cafe.navy.bedrock.paper.hologram.Hologram;
import cafe.navy.bedrock.paper.message.Message;
import cafe.navy.bedrock.paper.realm.Realm;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SculptManager {

    private final @NonNull Realm realm;
    private final @NonNull Map<String, Sculpt> sculpts;
    private final @NonNull Map<UUID, Sculpt> uuidIndex;

    public SculptManager(final @NonNull Realm realm) {
        this.realm = realm;
        this.sculpts = new HashMap<>();
        this.uuidIndex = new HashMap<>();
    }

    public @NonNull Sculpt createSculpt(final @NonNull String id,
                                        final @NonNull Location location) {
        final Sculpt sculpt = new Sculpt(location, new Hologram(location.add(0, 1, 0), List.of(Message.create(id))));
        this.sculpts.put(id, sculpt);
        this.uuidIndex.put(sculpt.uuid(), sculpt);
        this.realm.add(sculpt);
        return sculpt;
    }

    public @NonNull Map<String, Sculpt> sculpts() {
        return Map.copyOf(this.sculpts);
    }
}
