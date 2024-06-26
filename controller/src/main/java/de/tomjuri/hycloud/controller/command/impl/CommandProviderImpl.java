package de.tomjuri.hycloud.controller.command.impl;

import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.exceptions.*;
import cloud.commandframework.meta.SimpleCommandMeta;
import de.tomjuri.hycloud.api.command.CommandSender;
import de.tomjuri.hycloud.api.group.GroupProvider;
import de.tomjuri.hycloud.controller.command.CommandProvider;
import de.tomjuri.hycloud.controller.command.cmd.GroupCommand;
import de.tomjuri.hycloud.controller.command.cmd.HelpCommand;
import dev.derklaro.aerogel.auto.Provides;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.Getter;

import java.util.List;

@Singleton
@Provides(CommandProvider.class)
public class CommandProviderImpl implements CommandProvider {

    @Getter
    private final CommandManager<CommandSender> commandManager;
    private final AnnotationParser<CommandSender> annotationParser;
    private final CommandSender sender;

    @Inject
    public CommandProviderImpl(CommandSender sender, GroupProvider groupProvider) {
        this.commandManager = new CloudCommandManager();
        this.annotationParser = new AnnotationParser<>(this.commandManager, CommandSender.class, parameters -> SimpleCommandMeta.empty());
        this.sender = sender;
    }

    @Inject
    public void registerDefaultCommands(
            GroupCommand groupCommand,
            HelpCommand helpCommand
    ) {
        this.registerCommand(groupCommand);
        this.registerCommand(helpCommand);
    }

    public void registerCommand(Object command) {
        this.annotationParser.parse(command).stream().findFirst().orElseThrow();
    }

    @Override
    public void call(String input) {
        this.commandManager.executeCommand(this.sender, input).handle((commandResult, throwable) -> {
            if (throwable == null) return null;
            switch (throwable) {
                case NoSuchCommandException ignored -> this.sender.sendMessage("Command not found");
                case InvalidCommandSenderException ignored -> this.sender.sendMessage("You can't execute this command");
                case NoPermissionException ignored -> this.sender.sendMessage("You don't have the permission to execute this command");
                case InvalidSyntaxException invalidSyntaxException -> {
                    if (invalidSyntaxException.getCurrentChain().size() == 1) {
                        this.commandManager.executeCommand(this.sender, "help " + input.trim());
                        return null;
                    }
                    this.sender.sendMessage(throwable.getMessage());
                }
                case ArgumentParseException ignored -> this.sender.sendMessage(throwable.getCause().getMessage());
                default -> throw new IllegalStateException("Unexpected throwable: " + throwable);
            }
            return null;
        });
    }

    @Override
    public List<String> getCompletions(String input) {
        return this.commandManager.suggest(this.sender, input);
    }
}
