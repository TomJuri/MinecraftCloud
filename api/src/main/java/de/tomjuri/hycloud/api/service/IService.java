package de.tomjuri.hycloud.api.service;

import de.tomjuri.hycloud.api.group.IGroup;

public interface IService {
    String getName();
    IGroup getGroup();
    void start();
    void stop();
}
