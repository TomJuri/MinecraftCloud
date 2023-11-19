package de.tomjuri.hycloud.controller.command;

import cloud.commandframework.CommandManager;
import de.tomjuri.hycloud.api.command.CommandSender;

import java.util.List;

public interface CommandProvider {
    void call(String input);
    List<String> getCompletions(String input);
    CommandManager<CommandSender> getCommandManager();
}
