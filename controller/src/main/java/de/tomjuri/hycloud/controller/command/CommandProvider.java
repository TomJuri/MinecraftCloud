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
import de.tomjuri.hycloud.api.command.ICommandSender;
import de.tomjuri.hycloud.controller.command.impl.GroupCommand;
import de.tomjuri.hycloud.controller.command.impl.HelpCommand;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public class CommandProvider {

    private final CommandManager<ICommandSender> commandManager;
    private final AnnotationParser<ICommandSender> annotationParser;

    public CommandProvider() {
        commandManager = new CloudCommandManager();
        annotationParser = new AnnotationParser<>(commandManager, ICommandSender.class, parameters -> SimpleCommandMeta.empty());

        registerCommand(new GroupCommand(CloudAPI.getInstance().getGroupProvider()));
        registerCommand(new HelpCommand(commandManager));
    }

    public void registerCommand(Object command) {
        annotationParser.parse(command).stream().findFirst().orElseThrow();
    }

    public void call(String input, ICommandSender sender) {
        commandManager.executeCommand(sender, input).handle((commandResult, throwable) -> {
            // TODO make this better
            if (throwable == null) return null;
            if (throwable instanceof NoSuchCommandException)
                sender.sendMessage("Command not found");
            else if(throwable instanceof InvalidCommandSenderException)
                sender.sendMessage("You can't execute this command");
            else if(throwable instanceof NoPermissionException)
                sender.sendMessage("You don't have the permission to execute this command");
            else if(throwable instanceof InvalidSyntaxException) {
                if(((InvalidSyntaxException) throwable).getCurrentChain().size() == 1) {
                    commandManager.executeCommand(sender, "help " + input.trim());
                    return null;
                }
                sender.sendMessage(throwable.getMessage());
            } else if(throwable instanceof ArgumentParseException)
                sender.sendMessage(throwable.getCause().getMessage());
            return null;
        });
    }

    public List<String> getCompletions(String input, ICommandSender sender) {
        return commandManager.suggest(sender, input);
    }

    private static class CloudCommandManager extends CommandManager<ICommandSender> {

        public CloudCommandManager() {
            super(CommandExecutionCoordinator.simpleCoordinator(), new CloudCommandRegistrationHandler());
        }

        @Override
        public boolean hasPermission(@NonNull ICommandSender sender, @NonNull String permission) {
            return true;
        }

        @Override
        public @NonNull CommandMeta createDefaultCommandMeta() {
            return SimpleCommandMeta.empty();
        }
    }

    private static class CloudCommandRegistrationHandler implements CommandRegistrationHandler {

        private final Map<CommandArgument<?, ?>, Command<ICommandSender>> commands = new HashMap<>();

        @Override
        @SuppressWarnings("unchecked")
        public boolean registerCommand(@NonNull Command<?> command) {
            CommandArgument<?, ?> commandArgument = command.getArguments().get(0);
            if (commands.containsKey(commandArgument)) return false;
            commands.put(commandArgument, (Command<ICommandSender>) command);
            return true;
        }
    }
}
