package de.tomjuri.hycloud.controller.command.cmd;

import cloud.commandframework.Command;
import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.parsers.Parser;
import cloud.commandframework.annotations.suggestions.Suggestions;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.context.CommandContext;
import de.tomjuri.hycloud.api.command.CommandSender;
import de.tomjuri.hycloud.controller.command.CommandProvider;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class HelpCommand {
    private final CommandProvider commandProvider;

    @Inject
    public HelpCommand(CommandProvider commandProvider) {
        this.commandProvider = commandProvider;
    }

    @Suggestions("commands")
    public List<String> commandSuggestions(CommandContext<CommandSender> sender, String input) {
        return this.commandProvider.getCommandManager().commands().stream().map(command -> command.getArguments().get(0).getName()).distinct().collect(Collectors.toList());
    }

    @Parser(suggestions = "commands")
    public String commandParser(CommandContext<CommandSender> sender, Queue<String> input) {
        return input.remove();
    }

    @CommandMethod("help")
    public void help(CommandSender sender) {
        var commands = this.commandProvider.getCommandManager().commands().stream().map(command -> command.getArguments().get(0).getName()).collect(Collectors.toSet());
        sender.sendMessage(format(new ArrayList<>(commands)));
    }

    @CommandMethod("help <command>")
    public void helpCertain(CommandSender sender, @Argument("command") String name) {
        if(this.commandProvider.getCommandManager().commands().stream().filter(command -> command.getArguments().get(0).getName().equalsIgnoreCase(name)).findFirst().isEmpty()) {
            sender.sendMessage("Command not found");
            return;
        }
        sender.sendMessage(format(getUsages(name)));
    }

    public List<String> getUsages(String rootCommand) {
        List<String> commandUsage = new ArrayList<>();
        for(Command<CommandSender> command : this.commandProvider.getCommandManager().commands()) {
            List<CommandArgument<CommandSender, ?>> arguments = command.getArguments();
            if (arguments.isEmpty() || !arguments.get(0).getName().equalsIgnoreCase(rootCommand))
                continue;
            commandUsage.add(this.commandProvider.getCommandManager().commandSyntaxFormatter().apply(arguments, null));
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
