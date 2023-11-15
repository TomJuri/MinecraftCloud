package de.tomjuri.hycloud.controller.command.impl;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.parsers.Parser;
import cloud.commandframework.annotations.suggestions.Suggestions;
import cloud.commandframework.context.CommandContext;
import de.tomjuri.hycloud.api.group.GroupType;
import de.tomjuri.hycloud.api.group.IGroup;
import de.tomjuri.hycloud.api.group.IGroupProvider;
import de.tomjuri.hycloud.api.command.ICommandSender;
import de.tomjuri.hycloud.controller.group.GroupImpl;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Queue;

@AllArgsConstructor
public class GroupCommand {

    private final IGroupProvider groupProvider;

    @Suggestions("groups")
    public List<String> groupSuggestions(CommandContext<ICommandSender> sender, String input) {
        return groupProvider.getGroups().stream().map(IGroup::getName).toList();
    }

    @Parser(suggestions = "groups")
    public IGroup groupParser(CommandContext<ICommandSender> sender, Queue<String> input) {
        String name = input.remove();
        if (!groupProvider.existsGroup(name)) {
            throw new RuntimeException("This group does not exist");
        }
        return groupProvider.getGroup(name);
    }

    @CommandMethod("groups create <name>")
    public void createGroup(ICommandSender sender, @Argument("name") String name) {
        if(groupProvider.existsGroup(name)) {
            throw new RuntimeException("This group already exists");
        }
        groupProvider.createGroup(new GroupImpl(name, 0, GroupType.MINECRAFT));
        sender.sendMessage("Group created");
    }

    @CommandMethod("groups delete <group-name>")
    public void deleteGroup(ICommandSender sender, @Argument("group-name") IGroup group) {
        groupProvider.deleteGroup(group);
        sender.sendMessage("Group deleted");
    }

    @CommandMethod("groups list")
    public void listGroups(ICommandSender sender) {
        List<IGroup> groups = groupProvider.getGroups();
        if(groups.isEmpty()) {
            sender.sendMessage("There are no groups");
            return;
        }
        sender.sendMessage("Groups:\n" + String.join("\n ", groups.stream().map(IGroup::getName).toArray(String[]::new)));
    }
}
