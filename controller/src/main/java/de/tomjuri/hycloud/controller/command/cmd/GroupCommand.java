package de.tomjuri.hycloud.controller.command.cmd;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.parsers.Parser;
import cloud.commandframework.annotations.suggestions.Suggestions;
import cloud.commandframework.context.CommandContext;
import de.tomjuri.hycloud.api.command.CommandSender;
import de.tomjuri.hycloud.api.group.GroupType;
import de.tomjuri.hycloud.api.group.Group;
import de.tomjuri.hycloud.api.group.GroupProvider;
import de.tomjuri.hycloud.controller.group.GroupImpl;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Queue;

public class GroupCommand {

    private final GroupProvider groupProvider;

    @Inject
    public GroupCommand(GroupProvider groupProvider) {
        this.groupProvider = groupProvider;
    }

    @Suggestions("groups")
    public List<String> groupSuggestions(CommandContext<CommandSender> sender, String input) {
        return groupProvider.getGroups().stream().map(Group::getName).toList();
    }

    @Parser(suggestions = "groups")
    public Group groupParser(CommandContext<CommandSender> sender, Queue<String> input) {
        String name = input.remove();
        if (!groupProvider.existsGroup(name)) {
            throw new RuntimeException("This group does not exist");
        }
        return groupProvider.getGroup(name);
    }

    @CommandMethod("groups create <name>")
    public void createGroup(CommandSender sender, @Argument("name") String name) {
        if (groupProvider.existsGroup(name)) {
            throw new RuntimeException("This group already exists");
        }
        groupProvider.createGroup(new GroupImpl(name, 0, GroupType.MINECRAFT));
        sender.sendMessage("Group created");
    }

    @CommandMethod("groups delete <group-name>")
    public void deleteGroup(CommandSender sender, @Argument("group-name") Group group) {
        groupProvider.deleteGroup(group);
        sender.sendMessage("Group deleted");
    }

    @CommandMethod("groups list")
    public void listGroups(CommandSender sender) {
        List<Group> groups = groupProvider.getGroups();
        if (groups.isEmpty()) {
            sender.sendMessage("There are no groups");
            return;
        }
        sender.sendMessage("Groups:\n" + String.join("\n ", groups.stream().map(Group::getName).toArray(String[]::new)));
    }
}
