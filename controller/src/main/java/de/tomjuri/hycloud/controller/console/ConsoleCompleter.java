package de.tomjuri.hycloud.controller.console;

import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.List;

public class ConsoleCompleter implements Completer {

    private final Console console;

    public ConsoleCompleter(Console console) {
        this.console = console;
    }

    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
        console.getCommandProvider().getCompletions(line.line()).forEach(suggestion -> candidates.add(new Candidate(suggestion)));
    }
}
