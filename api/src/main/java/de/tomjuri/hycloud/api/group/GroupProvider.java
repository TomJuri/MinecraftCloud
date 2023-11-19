package de.tomjuri.hycloud.api.group;

import java.util.List;

public interface GroupProvider {
    Group getGroup(String name);
    List<Group> getGroups();
    boolean existsGroup(String name);
    void createGroup(Group group);
    void deleteGroup(Group group);
}
