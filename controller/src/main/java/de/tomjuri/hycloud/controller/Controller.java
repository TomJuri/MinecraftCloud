package de.tomjuri.hycloud.controller;

import de.tomjuri.hycloud.api.CloudAPI;
import de.tomjuri.hycloud.api.group.GroupProvider;
import de.tomjuri.hycloud.api.service.ServiceProvider;
import de.tomjuri.hycloud.controller.console.Console;
import jakarta.inject.Inject;
import lombok.Getter;

@Getter
public class Controller extends CloudAPI {

    private final GroupProvider groupProvider;
    private final ServiceProvider serviceProvider;
    private final Console console;

    @Inject
    public Controller(GroupProvider groupProvider, ServiceProvider serviceProvider, Console console) {
        this.groupProvider = groupProvider;
        this.serviceProvider = serviceProvider;
        this.console = console;
    }

    public void start() {
        this.console.start();
    }
}
