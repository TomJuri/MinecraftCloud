package de.tomjuri.hycloud.controller;

import de.tomjuri.hycloud.api.CloudAPI;
import de.tomjuri.hycloud.controller.command.CommandProvider;
import de.tomjuri.hycloud.controller.group.GroupProviderImpl;
import de.tomjuri.hycloud.api.group.IGroupProvider;
import de.tomjuri.hycloud.api.service.IServiceProvider;
import de.tomjuri.hycloud.controller.network.NettyServer;
import de.tomjuri.hycloud.controller.service.ServiceProviderImpl;
import de.tomjuri.hycloud.controller.terminal.Console;
import lombok.Getter;

@Getter
public class Controller extends CloudAPI {

    private final IGroupProvider groupProvider;
    private final IServiceProvider serviceProvider;
    private final CommandProvider commandProvider;
    private final Console terminal;
    private final NettyServer nettyServer;

    public Controller() {
        groupProvider = new GroupProviderImpl();
        serviceProvider = new ServiceProviderImpl();
        commandProvider = new CommandProvider();
        terminal = new Console();
        nettyServer = new NettyServer();
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
    }
}
