package de.tomjuri.hycloud.controller.terminal;

import de.tomjuri.hycloud.controller.command.CommandProvider;
import de.tomjuri.hycloud.controller.command.ConsoleCommandSender;
import lombok.AllArgsConstructor;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.List;

@AllArgsConstructor
public class ConsoleCompleter implements Completer {
    private final CommandProvider commandProvider;

    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
        commandProvider.getCompletions(line.line(), new ConsoleCommandSender()).forEach(suggestion -> candidates.add(new Candidate(suggestion)));
    }
}
