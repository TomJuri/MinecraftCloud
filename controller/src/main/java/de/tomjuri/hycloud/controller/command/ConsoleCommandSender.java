package de.tomjuri.hycloud.controller.command;

import de.tomjuri.hycloud.api.command.ICommandSender;
import de.tomjuri.hycloud.controller.Controller;

public class ConsoleCommandSender implements ICommandSender {
    @Override
    public void sendMessage(String message) {
        ((Controller) Controller.getInstance()).getTerminal().write(message);
    }
}
