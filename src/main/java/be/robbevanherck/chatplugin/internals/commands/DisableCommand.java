package be.robbevanherck.chatplugin.internals.commands;

import be.robbevanherck.chatplugin.repositories.ChatServiceRepository;
import be.robbevanherck.chatplugin.services.ChatService;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import static com.mojang.brigadier.arguments.StringArgumentType.string;

/**
 * Command to link a Minecraft user to a mapping
 */
public class DisableCommand {
    /**
     * Private constructor to hide the implicit public one
     */
    private DisableCommand() {}
    /**
     * Register the command
     * @param dispatcher The Dispatcher required
     */
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            CommandManager.literal("disable")
                .then(CommandManager.argument("name", string())
                    .suggests((context, builder) -> {
                        builder.suggest("all");
                        ChatServiceRepository.getEnabledChatServices().forEach(service -> builder.suggest(service.getDisplayName()));
                        return builder.buildFuture();
                    })
                    .executes(
                        ctx -> {
                            String name = StringArgumentType.getString(ctx, "name");

                            if (name.equals("all")) {
                                ChatServiceRepository.getEnabledChatServices().forEach(ChatService::disable);
                                return Command.SINGLE_SUCCESS;
                            }

                            ChatService serviceToDisable = ChatServiceRepository.getChatServiceByName(name);

                            if (serviceToDisable == null) {
                                ctx.getSource().sendFeedback(new LiteralText("That chat service was not found.").formatted(Formatting.RED), false);
                                return 0;
                            }

                            if (!serviceToDisable.isEnabled()) {
                                ctx.getSource().sendFeedback(new LiteralText(name + " was already disabled.").formatted(Formatting.RED), false);
                                return Command.SINGLE_SUCCESS;
                            }

                            serviceToDisable.disable();
                            ctx.getSource().sendFeedback(new LiteralText(name + " was disabled."), false);
                            return Command.SINGLE_SUCCESS;
                        }
                    )
                )
        );
    }
}
