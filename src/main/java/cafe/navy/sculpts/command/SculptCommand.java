package cafe.navy.sculpts.command;

import cafe.navy.bedrock.paper.Server;
import cafe.navy.bedrock.paper.command.BaseCommand;
import cafe.navy.bedrock.paper.message.Colours;
import cafe.navy.bedrock.paper.message.Message;
import cafe.navy.bedrock.paper.message.Messages;
import cafe.navy.sculpts.Sculpt;
import cafe.navy.sculpts.SculptManager;
import cafe.navy.sculpts.SculptsPlugin;
import cafe.navy.sculpts.tools.ToolType;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.paper.PaperCommandManager;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SculptCommand implements BaseCommand {

    private final @NonNull Server server;
    private final @NonNull SculptsPlugin plugin;
    private final @NonNull StringArgument<CommandSender> toolArg;

    public SculptCommand(final @NonNull Server server,
                         final @NonNull SculptsPlugin plugin) {
        this.server = server;
        this.plugin = plugin;
        this.toolArg = StringArgument
                .<CommandSender>newBuilder("tool")
                .withSuggestionsProvider((ctx, in) -> {
                    return this.plugin.toolTypes().stream().map(ToolType::id).toList();
                })
                .build();
    }

    @Override
    public @NonNull String name() {
        return "sculpts";
    }

    @Override
    public void registerCommands(@NonNull PaperCommandManager<CommandSender> manager) {
        final var builder = this.createBuilder(manager);

        final var list = builder
                .literal("list")
                .handler(this::handleList);

        final var create = builder
                .literal("create")
                .argument(StringArgument.of("id"))
                .handler(this::handleCreate);

        final var tool = builder
                .literal("tool")
                .argument(this.toolArg.copy())
                .handler(this::handleTool);

        manager.command(list);
        manager.command(create);
        manager.command(tool);
    }

    private void handleList(final @NonNull CommandContext<@NonNull CommandSender> ctx) {
        final CommandSender sender = ctx.getSender();
        final List<SculptManager> managers = new ArrayList<>();

        if (sender instanceof Player player) {
            final var managerOpt = this.plugin.getManager(player.getWorld().getUID());
            if (managerOpt.isEmpty()) {
                return;
            }

            managers.add(managerOpt.get());
        } else {
            managers.addAll(this.plugin.managers());
        }

        for (final SculptManager manager : managers) {
            final List<Sculpt> sculpts = manager.sculpts();
            sender.sendMessage(Message
                    .create()
                    .text("   ", Colours.Tones.DARKER_GRAY, TextDecoration.STRIKETHROUGH)
                    .text(manager.realm().name())
                    .text("   ", Colours.Tones.DARKER_GRAY, TextDecoration.STRIKETHROUGH)
            );

            if (sculpts.isEmpty()) {
                sender.sendMessage(Message.create()
                        .highlight("No sculpts in realm " + manager.realm().name()));
                continue;
            }

            for (final Sculpt sculpt : sculpts) {
                sender.sendMessage(Message
                        .create()
                        .text(" - ")
                        .highlight(sculpt.id().orElse(sculpt.uuid().toString()))
                );
            }
        }
    }

    private void handleCreate(final @NonNull CommandContext<@NonNull CommandSender> ctx) {
        final CommandSender sender = ctx.getSender();
        final String id = ctx.get("id");
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.NO_PLAYER);
            return;
        }

        final Location location = player.getLocation();
        final World world = location.getWorld();

        final Optional<SculptManager> managerOpt = this.plugin.getManager(world.getUID());
        if (managerOpt.isEmpty()) {
            sender.sendMessage(Messages.createError().text("There is no SculptManager for this world."));
            return;
        }

        final SculptManager manager = managerOpt.get();
        final Sculpt sculpt = manager.createSculpt(id, location);
    }

    private void handleTool(final @NonNull CommandContext<CommandSender> ctx) {
        final CommandSender sender = ctx.getSender();
        final String toolId = ctx.get("tool");
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.NO_PLAYER);
            return;
        }

        final Optional<ToolType> typeOpt = this.plugin.toolType(toolId);
        if (typeOpt.isEmpty()) {
            return;
        }

        final ToolType type = typeOpt.get();
        this.plugin.giveTool(player, type);
    }


}
