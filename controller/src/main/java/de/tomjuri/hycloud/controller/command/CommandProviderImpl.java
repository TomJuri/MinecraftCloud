package de.tomjuri.hycloud.controller.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.exceptions.*;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.internal.CommandRegistrationHandler;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.meta.SimpleCommandMeta;
import de.tomjuri.hycloud.api.CloudAPI;
import de.tomjuri.hycloud.api.command.CommandSender;
import de.tomjuri.hycloud.api.group.GroupProvider;
import de.tomjuri.hycloud.controller.command.cmd.GroupCommand;
import de.tomjuri.hycloud.controller.command.cmd.HelpCommand;
import dev.derklaro.aerogel.auto.Provides;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

@Singleton
@Provides(CommandProvider.class)
public class CommandProviderImpl implements CommandProvider {

    private final CommandManager<CommandSender> commandManager;
    private final AnnotationParser<CommandSender> annotationParser;
    private final CommandSender sender;

    @Inject
    public CommandProviderImpl(CommandSender sender, GroupProvider groupProvider) {
        commandManager = new CloudCommandManager();
        annotationParser = new AnnotationParser<>(commandManager, CommandSender.class, parameters -> SimpleCommandMeta.empty());
        this.sender = sender;

        registerCommand(new GroupCommand(groupProvider));
        registerCommand(new HelpCommand(commandManager));
    }

    public void registerCommand(Object command) {
        annotationParser.parse(command).stream().findFirst().orElseThrow();
    }

    @Override
    public void call(String input) {
        commandManager.executeCommand(sender, input).handle((commandResult, throwable) -> {
            if (throwable == null) return null;
            switch (throwable) {
                case NoSuchCommandException ignored -> sender.sendMessage("Command not found");
                case InvalidCommandSenderException ignored -> sender.sendMessage("You can't execute this command");
                case NoPermissionException ignored -> sender.sendMessage("You don't have the permission to execute this command");
                case InvalidSyntaxException invalidSyntaxException -> {
                    if (invalidSyntaxException.getCurrentChain().size() == 1) {
                        commandManager.executeCommand(sender, "help " + input.trim());
                        return null;
                    }
                    sender.sendMessage(throwable.getMessage());
                }
                case ArgumentParseException ignored -> sender.sendMessage(throwable.getCause().getMessage());
                default -> throw new IllegalStateException("Unexpected throwable: " + throwable);
            }
            return null;
        });
    }

    @Override
    public List<String> getCompletions(String input) {
        return commandManager.suggest(sender, input);
    }

    private static class CloudCommandManager extends CommandManager<CommandSender> {

        public CloudCommandManager() {
            super(CommandExecutionCoordinator.simpleCoordinator(), new CloudCommandRegistrationHandler());
        }

        @Override
        public boolean hasPermission(@NonNull CommandSender sender, @NonNull String permission) {
            return true;
        }

        @Override
        public @NonNull CommandMeta createDefaultCommandMeta() {
            return SimpleCommandMeta.empty();
        }
    }

    private static class CloudCommandRegistrationHandler implements CommandRegistrationHandler {

        private final Map<CommandArgument<?, ?>, Command<CommandSender>> commands = new HashMap<>();

        @Override
        @SuppressWarnings("unchecked")
        public boolean registerCommand(@NonNull Command<?> command) {
            CommandArgument<?, ?> commandArgument = command.getArguments().get(0);
            if (commands.containsKey(commandArgument)) return false;
            commands.put(commandArgument, (Command<CommandSender>) command);
            return true;
        }
    }
}
