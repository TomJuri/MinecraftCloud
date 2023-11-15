package de.tomjuri.hycloud.controller.group;

import de.tomjuri.hycloud.api.group.IGroupProvider;
import de.tomjuri.hycloud.api.group.IGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupProviderImpl implements IGroupProvider {

    private final Map<String, IGroup> groupPool = new HashMap<>();

    @Override
    public IGroup getGroup(String name) {
        return groupPool.get(name);
    }

    @Override
    public List<IGroup> getGroups() {
        return groupPool.values().stream().toList();
    }

    @Override
    public boolean existsGroup(String name) {
        return groupPool.containsKey(name);
    }

    @Override
    public void createGroup(IGroup group) {
        groupPool.put(group.getName(), group);
    }

    @Override
    public void deleteGroup(IGroup group) {
        groupPool.remove(group.getName());
    }
}
