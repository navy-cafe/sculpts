package cafe.navy.sculpts.command;

import cafe.navy.bedrock.paper.Server;
import cafe.navy.bedrock.paper.command.BaseCommand;
import cafe.navy.bedrock.paper.message.Message;
import cafe.navy.bedrock.paper.message.Messages;
import cafe.navy.bedrock.paper.realm.Realm;
import cafe.navy.sculpts.Sculpt;
import cafe.navy.sculpts.SculptsPlugin;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public class SculptCommand implements BaseCommand {

    private final @NonNull Server server;
    private final @NonNull SculptsPlugin plugin;

    public SculptCommand(final @NonNull Server server,
                         final @NonNull SculptsPlugin plugin) {
        this.server = server;
        this.plugin = plugin;
    }

    @Override
    public @NonNull String name() {
        return "sculpts";
    }

    @Override
    public void registerCommands(@NonNull PaperCommandManager<CommandSender> manager) {
        final var builder = this.createBuilder(manager);
        final var debugCreate = builder
                .literal("debug")
                .literal("create")
                .handler(ctx -> {
                    if (!(ctx.getSender() instanceof Player player)) {
                        ctx.getSender().sendMessage(Messages.NO_PLAYER);
                        return;
                    }
                    final var realmOpt = this.plugin.getManager(player.getWorld().getUID());
                    if (realmOpt.isEmpty()) {
                        player.sendMessage(Message.create("no realm"));
                        return;
                    }
                    final var realm = realmOpt.get();
                    realm.createSculpt(UUID.randomUUID().toString(), player.getLocation());
                });

        final var list = builder
                .literal("list")
                .handler(ctx -> {
                    for (final Realm realm : this.server.realms()) {
                        final var managerOpt = this.plugin.getManager(realm.uuid());
                        if (managerOpt.isEmpty()) {
                            continue;
                        }

                        final var realmManager = managerOpt.get();
                        final var sculpts = realmManager.sculptsById();
                        if (sculpts.isEmpty()) {
                            continue;
                        }

                        ctx.getSender().sendMessage(Message.create()
                                .highlight(realm.name())
                                .main(": "+sculpts.size()+" sculpts")
                        );

                        for (final var entry : sculpts.entrySet()) {
                            final String id = entry.getKey();
                            final Sculpt sculpt = entry.getValue();

                            ctx.getSender().sendMessage(Message.create()
                                    .main("  - ")
                                    .highlight(id));
                        }
                    }
                });

        manager.command(builder);
        manager.command(list);
        manager.command(debugCreate);

    }


}
