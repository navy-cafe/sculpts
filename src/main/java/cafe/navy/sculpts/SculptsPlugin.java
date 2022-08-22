package cafe.navy.sculpts;

import broccolai.corn.paper.item.PaperItemBuilder;
import cafe.navy.bedrock.paper.Server;
import cafe.navy.bedrock.paper.item.ItemTypeBuilder;
import cafe.navy.bedrock.paper.message.Message;
import cafe.navy.bedrock.paper.realm.Realm;
import cafe.navy.bedrock.paper.realm.WorldRealm;
import cafe.navy.sculpts.command.SculptCommand;
import cafe.navy.sculpts.skin.SkinType;
import cafe.navy.sculpts.skin.entity.EntitySkinType;
import cafe.navy.sculpts.skin.stand.StandSkinType;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public class SculptsPlugin extends JavaPlugin implements Listener {

    private @MonotonicNonNull Server server;
    private @MonotonicNonNull Map<UUID, Realm> realms;
    private @MonotonicNonNull Map<UUID, SculptManager> managers;
    private @MonotonicNonNull Map<String, SkinType> skinTypes;

    @Override
    public void onEnable() {
        this.server = this.getCore();
        if (!this.server.enabled()) {
            this.server.enable();
        }
        this.loadRealms();
        this.server.commands().addCommand(new SculptCommand(this.server, this));

        this.server.items().register(ItemTypeBuilder
                .of("sculpt_base", PaperItemBuilder
                        .ofType(Material.ARMOR_STAND)
                        .name(Message.create("Sculpt Base").asComponent())
                        .lore(List.of(
                                Component.empty(),
                                Message.create("The base component of").asComponent(),
                                Message.create("any sculpt. Place on a block").asComponent(),
                                Message.create("to start using!").asComponent()
                        ))
                        .build())
                .onPlace(ctx -> {
                    final Location location = ctx.cause().getClickedBlock().getLocation();
                    if (location == null) {
                        ctx.cause().getPlayer().sendMessage("No loc!");
                        return;
                    }

                    final var manOpt = this.getManager(location.getWorld().getUID());
                    if (manOpt.isEmpty()) {
                        ctx.cause().getPlayer().sendMessage("bad world!");
                        return;
                    }

                    final var man = manOpt.get();
                    man.createSculpt(location);
                    ctx.cause().getPlayer().sendMessage("sculpt created!");
                })
                .build());

        this.skinTypes = new HashMap<>();
        this.getServer().getPluginManager().registerEvents(this, this);

        this.registerSkinType(new StandSkinType());

        for (final var type : EntityType.values()) {
            if (type.getName() == null) {
                continue;
            }
            final var skinType = new EntitySkinType(net.minecraft.world.entity.EntityType.byString(type.getName()).get());
            this.registerSkinType(skinType);
        }
    }

    public void registerSkinType(final @NonNull SkinType<?> type) {
        this.skinTypes.put(type.id(), type);
        this.server.items().register(ItemTypeBuilder
                .of("sculpt_skin_" + type.id(), PaperItemBuilder
                        .ofType(Material.CHAIN_COMMAND_BLOCK)
                        .name(Message
                                .create("Sculpt Skin: ")
                                .text(type.name())
                                .asComponent())
                        .lore(type.description())
                        .build())
                .onEntityInteract(ctx -> {
                    System.out.println("sculpt_skin_" + type.id());
                    try {
                        final var entity = ctx.clientEntity();
                        if (entity == null) {
                            return;
                        }

                        if (entity.getTop() instanceof Sculpt sculpt) {
                            type.apply(sculpt);
                        }

                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                })
                .build());
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll((Listener) this);
    }

    public @NonNull Optional<@NonNull SculptManager> getManager(final @NonNull UUID realm) {
        return Optional.ofNullable(this.managers.get(realm));
    }

    private void loadRealms() {
        if (this.realms == null) {
            this.realms = new HashMap<>();
        }

        if (this.managers == null) {
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
