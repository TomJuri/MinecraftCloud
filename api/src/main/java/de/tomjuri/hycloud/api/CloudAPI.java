package de.tomjuri.hycloud.api;

import de.tomjuri.hycloud.api.service.ServiceProvider;
import de.tomjuri.hycloud.api.group.GroupProvider;

public abstract class CloudAPI {

    private static CloudAPI instance;

    public CloudAPI() {
        instance = this;
    }

    public static CloudAPI getInstance() {
        return instance;
    }

    public abstract GroupProvider getGroupProvider();
    public abstract ServiceProvider getServiceProvider();
}
