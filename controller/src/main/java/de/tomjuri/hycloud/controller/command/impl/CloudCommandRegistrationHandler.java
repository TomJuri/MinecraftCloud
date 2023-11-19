package de.tomjuri.hycloud.controller.command.impl;

import cloud.commandframework.Command;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.internal.CommandRegistrationHandler;
import de.tomjuri.hycloud.api.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class CloudCommandRegistrationHandler implements CommandRegistrationHandler {

    private final Map<CommandArgument<?, ?>, Command<CommandSender>> commands = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public boolean registerCommand(Command<?> command) {
        CommandArgument<?, ?> commandArgument = command.getArguments().get(0);
        if (this.commands.containsKey(commandArgument)) return false;
        this.commands.put(commandArgument, (Command<CommandSender>) command);
        return true;
    }
}