package de.tomjuri.hycloud.api.service;

import java.util.List;

public interface IServiceProvider {
    IService getService(String name);
    List<IService> getServices();
    void registerService(IService service);
    void unregisterService(IService service);
}
