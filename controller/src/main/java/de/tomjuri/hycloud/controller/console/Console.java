package de.tomjuri.hycloud.controller.console;

import de.tomjuri.hycloud.controller.command.CommandProvider;
import de.tomjuri.hycloud.controller.command.CommandProviderImpl;
import org.jline.reader.LineReader;

public interface Console {
    void start();
    void close();
    void write(String message);
    CommandProvider getCommandProvider();
    LineReader getLineReader();
}
