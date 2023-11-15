package de.tomjuri.hycloud.controller.service;

import de.tomjuri.hycloud.api.service.IService;
import de.tomjuri.hycloud.api.service.IServiceProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceProviderImpl implements IServiceProvider {
    private final Map<String, IService> servicePool = new HashMap<>();

    @Override
    public IService getService(String name) {
        return servicePool.get(name);
    }

    @Override
    public List<IService> getServices() {
        return servicePool.values().stream().toList();
    }

    @Override
    public void registerService(IService service) {
        servicePool.put(service.getName(), service);
    }

    @Override
    public void unregisterService(IService service) {
        servicePool.remove(service.getName());
    }
}
