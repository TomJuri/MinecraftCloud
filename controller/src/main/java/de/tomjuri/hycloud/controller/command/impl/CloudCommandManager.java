package de.tomjuri.hycloud.controller.command.impl;

import cloud.commandframework.CommandManager;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.meta.SimpleCommandMeta;
import de.tomjuri.hycloud.api.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CloudCommandManager extends CommandManager<CommandSender> {

    public CloudCommandManager() {
        super(CommandExecutionCoordinator.simpleCoordinator(), new CloudCommandRegistrationHandler());
    }

    @Override
    public boolean hasPermission(@NotNull CommandSender sender, @NotNull String permission) {
        return true;
    }

    @Override
    public @NotNull CommandMeta createDefaultCommandMeta() {
        return SimpleCommandMeta.empty();
    }
}