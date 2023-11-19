package de.tomjuri.hycloud.controller.group;

import de.tomjuri.hycloud.api.group.GroupType;
import de.tomjuri.hycloud.api.group.Group;
import dev.derklaro.aerogel.auto.Provides;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupImpl implements Group {
    private final String name;
    private final int getMinOnlineCount;
    private final GroupType type;
}
