package de.tomjuri.hycloud.controller.service;

import de.tomjuri.hycloud.api.group.Group;
import de.tomjuri.hycloud.api.service.Service;
import dev.derklaro.aerogel.auto.Provides;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class ServiceImpl implements Service {

    private final String name;
    private final Group group;
    private final Process process;

    @Override
    public void start() {
        // TODO
    }

    @Override
    public void stop() {
        // TODO
    }
}
