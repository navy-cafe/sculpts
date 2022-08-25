package cafe.navy.sculpts;

import broccolai.corn.paper.item.PaperItemBuilder;
import cafe.navy.bedrock.paper.Server;
import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.bedrock.paper.item.ItemType;
import cafe.navy.bedrock.paper.item.ItemTypeBuilder;
import cafe.navy.bedrock.paper.message.Message;
import cafe.navy.bedrock.paper.realm.Realm;
import cafe.navy.bedrock.paper.realm.WorldRealm;
import cafe.navy.sculpts.command.SculptCommand;
import cafe.navy.sculpts.tools.RotateToolType;
import cafe.navy.sculpts.tools.SizeToolType;
import cafe.navy.sculpts.tools.ToolType;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
    private @MonotonicNonNull Map<String, ToolType> tools;

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

        this.tools = new HashMap<>();
        this.registerTool(new RotateToolType());
        this.registerTool(new SizeToolType());
    }

    public void registerTool(final @NonNull ToolType type) {
        this.tools.put(type.id(), type);
    }

    public @NonNull List<ToolType> toolTypes() {
        return List.copyOf(this.tools.values());
    }

    public @NonNull Optional<@NonNull ToolType> toolType(final @NonNull String id) {
        return Optional.ofNullable(this.tools.get(id));
    }

    public void giveTool(final @NonNull Player player,
                         final @NonNull ToolType type) {
        final var itemType = ItemTypeBuilder
                .of("id", type.newItemBuilder().build())
                .onEntityInteract(ctx -> {
                    System.out.println("Handling interact");

                    if (ctx.clientEntity() == null) {
                        return;
                    }

                    final ClientEntity entity = ctx.clientEntity();
                    if (!(entity.getTop() instanceof SculptEntity sculpt)) {
                        return;
                    }

                    type.handleInteract(player, sculpt.sculpt());
                });

        final var item = this.server.items().createItem(itemType.build());
        player.getInventory().addItem(this.server.items().getItemStack(item));
    }



        @Override
    public void onDisable() {
        HandlerList.unregisterAll((Listener) this);
    }

    public @NonNull Optional<@NonNull SculptManager> getManager(final @NonNull UUID realm) {
        return Optional.ofNullable(this.managers.get(realm));
    }

    public @NonNull List<@NonNull SculptManager> managers() {
        return List.copyOf(this.managers.values());
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
