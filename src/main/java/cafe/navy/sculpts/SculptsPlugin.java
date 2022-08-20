package cafe.navy.sculpts;

import cafe.navy.bedrock.paper.Server;
import cafe.navy.bedrock.paper.realm.Realm;
import cafe.navy.bedrock.paper.realm.WorldRealm;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SculptsPlugin extends JavaPlugin {

    private @MonotonicNonNull Server server;
    private @MonotonicNonNull Map<UUID, Realm> realms;
    private @MonotonicNonNull Map<UUID, SculptManager> managers;

    @Override
    public void onEnable() {
        this.server = this.getCore();
        if (!this.server.enabled()) {
            this.server.enable();
        }
        this.loadRealms();
    }

    @Override
    public void onDisable() {

    }

    public @NonNull Optional<@NonNull SculptManager> getManager(final @NonNull UUID realm) {
        return Optional.ofNullable(this.managers.get(realm));
    }

    private void loadRealms() {
        if (this.realms == null) {
            this.realms = new HashMap<>();
        }

        if (this.managers != null) {
            this.managers = new HashMap<>();
        }

        for (final Realm realm : this.server.realms()) {
            if (realm instanceof WorldRealm) {
                this.registerRealm(realm);
            }
        }
    }

    private void registerRealm(final @NonNull Realm realm) {
        this.realms.put(realm.uuid(), realm);
        final SculptManager manager = new SculptManager(realm);
        this.managers.put(realm.uuid(), manager);
    }

    // so angry i can't name this getServer >:(
    private @NonNull Server getCore() {
        final var registration = this.getServer().getServicesManager().getRegistration(Server.class);
        if (registration == null) {
            return new Server(this);
        } else {
            return registration.getProvider();
        }
    }

}
