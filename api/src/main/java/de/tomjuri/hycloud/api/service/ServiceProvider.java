package de.tomjuri.hycloud.api.service;

import java.util.List;

public interface ServiceProvider {
    Service getService(String name);
    List<Service> getServices();
    void registerService(Service service);
    void unregisterService(Service service);
}
