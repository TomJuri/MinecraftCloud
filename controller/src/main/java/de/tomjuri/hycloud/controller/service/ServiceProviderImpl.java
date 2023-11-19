package de.tomjuri.hycloud.controller.service;

import de.tomjuri.hycloud.api.service.Service;
import de.tomjuri.hycloud.api.service.ServiceProvider;
import dev.derklaro.aerogel.auto.Provides;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
@Provides(ServiceProvider.class)
public class ServiceProviderImpl implements ServiceProvider {

    private final Map<String, Service> servicePool = new HashMap<>();

    @Override
    public Service getService(String name) {
        return servicePool.get(name);
    }

    @Override
    public List<Service> getServices() {
        return servicePool.values().stream().toList();
    }

    @Override
    public void registerService(Service service) {
        servicePool.put(service.getName(), service);
    }

    @Override
    public void unregisterService(Service service) {
        servicePool.remove(service.getName());
    }
}
