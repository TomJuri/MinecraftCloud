package de.tomjuri.hycloud.controller;

import de.tomjuri.hycloud.controller.console.Console;
import jakarta.inject.Inject;

public class ShutdownHook extends Thread {

    private final Console console;

    @Inject
    public ShutdownHook(Console console) {
        this.console = console;
    }

    @Override
    public void run() {
        console.close();
    }
}
