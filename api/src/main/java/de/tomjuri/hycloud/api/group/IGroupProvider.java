package de.tomjuri.hycloud.api.group;

import java.util.List;

public interface IGroupProvider {
    IGroup getGroup(String name);
    List<IGroup> getGroups();
    boolean existsGroup(String name);
    void createGroup(IGroup group);
    void deleteGroup(IGroup group);
}
