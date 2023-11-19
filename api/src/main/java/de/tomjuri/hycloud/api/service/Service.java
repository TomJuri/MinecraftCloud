package de.tomjuri.hycloud.api.service;

import de.tomjuri.hycloud.api.group.Group;

public interface Service {
    String getName();
    Group getGroup();
    void start();
    void stop();
}
