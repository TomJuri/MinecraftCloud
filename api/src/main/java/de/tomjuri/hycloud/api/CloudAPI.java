package de.tomjuri.hycloud.api;

import de.tomjuri.hycloud.api.service.IServiceProvider;
import de.tomjuri.hycloud.api.group.IGroupProvider;
import lombok.Getter;

public abstract class CloudAPI {

    @Getter
    private static CloudAPI instance;

    public CloudAPI() {
        instance = this;
    }

    public abstract IGroupProvider getGroupProvider();
    public abstract IServiceProvider getServiceProvider();
}
