package de.tomjuri.hycloud.controller.group;

import de.tomjuri.hycloud.api.group.GroupType;
import de.tomjuri.hycloud.api.group.IGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupImpl implements IGroup {
    private final String name;
    private final int getMinOnlineCount;
    private final GroupType type;
}
