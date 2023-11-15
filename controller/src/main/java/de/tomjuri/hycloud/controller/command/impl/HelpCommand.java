package de.tomjuri.hycloud.controller.command.impl;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.parsers.Parser;
import cloud.commandframework.annotations.suggestions.Suggestions;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.context.CommandContext;
import de.tomjuri.hycloud.api.command.ICommandSender;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@AllArgsConstructor
public class HelpCommand {
    private final CommandManager<ICommandSender> commandManager;

    @Suggestions("commands")
    public List<String> commandSuggestions(CommandContext<ICommandSender> sender, String input) {
        return commandManager.commands().stream().map(command -> command.getArguments().get(0).getName()).distinct().collect(Collectors.toList());
    }

    @Parser(suggestions = "commands")
    public String commandParser(CommandContext<ICommandSender> sender, Queue<String> input) {
        return input.remove();
    }

    @CommandMethod("help")
    public void help(ICommandSender sender) {
        var commands = commandManager.commands().stream().map(command -> command.getArguments().get(0).getName()).collect(Collectors.toSet());
        sender.sendMessage(format(new ArrayList<>(commands)));
    }

    @CommandMethod("help <command>")
    public void helpCertain(ICommandSender sender, @Argument("command") String name) {
        if(commandManager.commands().stream().filter(command -> command.getArguments().get(0).getName().equalsIgnoreCase(name)).findFirst().isEmpty()) {
            sender.sendMessage("Command not found");
            return;
        }
        sender.sendMessage(format(getUsages(name)));
    }

    public List<String> getUsages(String rootCommand) {
        List<String> commandUsage = new ArrayList<>();
        for(Command<ICommandSender> command : commandManager.commands()) {
            List<CommandArgument<ICommandSender, ?>> arguments = command.getArguments();
            if (arguments.isEmpty() || !arguments.get(0).getName().equalsIgnoreCase(rootCommand))
                continue;
            commandUsage.add(this.commandManager.commandSyntaxFormatter().apply(arguments, null));
        }
        Collections.sort(commandUsage);
        return commandUsage;
    }

    public String format(List<String> usages) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < usages.size(); i++) {
            String usage = usages.get(i);
            builder.append("- ").append(usage);
            if(i != usages.size() - 1)
                builder.append("\n");
        }
        return builder.toString();
    }
}
