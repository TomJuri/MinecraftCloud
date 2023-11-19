package de.tomjuri.hycloud.controller.command;

import java.util.List;

public interface CommandProvider {
    void call(String input);
    List<String> getCompletions(String input);
}
