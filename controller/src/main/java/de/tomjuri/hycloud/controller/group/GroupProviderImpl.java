package de.tomjuri.hycloud.controller.group;

import de.tomjuri.hycloud.api.group.GroupProvider;
import de.tomjuri.hycloud.api.group.Group;
import dev.derklaro.aerogel.auto.Provides;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
@Provides(GroupProvider.class)
public class GroupProviderImpl implements GroupProvider {

    private final Map<String, Group> groupPool = new HashMap<>();

    @Override
    public Group getGroup(String name) {
        return groupPool.get(name);
    }

    @Override
    public List<Group> getGroups() {
        return groupPool.values().stream().toList();
    }

    @Override
    public boolean existsGroup(String name) {
        return groupPool.containsKey(name);
    }

    @Override
    public void createGroup(Group group) {
        groupPool.put(group.getName(), group);
    }

    @Override
    public void deleteGroup(Group group) {
        groupPool.remove(group.getName());
    }
}
