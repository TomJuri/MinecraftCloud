package de.tomjuri.hycloud.controller.command;

import de.tomjuri.hycloud.api.command.CommandSender;
import de.tomjuri.hycloud.controller.Controller;
import de.tomjuri.hycloud.controller.console.Console;
import dev.derklaro.aerogel.auto.Provides;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
@Provides(CommandSender.class)
public class ConsoleCommandSender implements CommandSender {

    private final Console console;

    @Inject
    public ConsoleCommandSender(Console console) {
        this.console = console;
    }

    @Override
    public void sendMessage(String message) {
        console.write(message);
    }
}
