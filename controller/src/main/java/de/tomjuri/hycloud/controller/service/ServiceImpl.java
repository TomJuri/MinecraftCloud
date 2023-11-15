package de.tomjuri.hycloud.controller.service;

import de.tomjuri.hycloud.api.group.IGroup;
import de.tomjuri.hycloud.api.service.IService;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceImpl implements IService {
    private final String name;
    private final IGroup group;
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
