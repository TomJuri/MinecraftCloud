package de.tomjuri.hycloud.controller.terminal;

import de.tomjuri.hycloud.controller.Controller;
import lombok.SneakyThrows;
import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.LineReader;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

public class Console {
    private final Terminal terminal;
    private final ConsoleRunner runner;

    @SneakyThrows
    public Console() {
        AnsiConsole.systemInstall();
        this.terminal = TerminalBuilder.builder().system(true).build();
        LineReaderImpl lineReader = new LineReaderImpl(terminal, "HyCloud-Terminal", null);
        lineReader.setCompleter(new ConsoleCompleter(((Controller) Controller.getInstance()).getCommandProvider()));
        lineReader.setCompletionMatcher(new ConsoleCompletionMatcher());
        lineReader.setAutosuggestion(LineReader.SuggestionType.COMPLETER);
        lineReader.option(LineReader.Option.AUTO_GROUP, false);
        lineReader.option(LineReader.Option.AUTO_MENU_LIST, true);
        lineReader.option(LineReader.Option.AUTO_FRESH_LINE, true);
        lineReader.option(LineReader.Option.EMPTY_WORD_OPTIONS, false);
        lineReader.option(LineReader.Option.HISTORY_TIMESTAMPED, false);
        lineReader.option(LineReader.Option.DISABLE_EVENT_EXPANSION, true);
        lineReader.variable(LineReader.BELL_STYLE, "none");
        lineReader.variable(LineReader.COMPLETION_STYLE_LIST_BACKGROUND, "inverse");
        runner = new ConsoleRunner(lineReader);
    }

    @SneakyThrows
    public void close() {
        AnsiConsole.systemUninstall();
        runner.interrupt();
        terminal.close();
    }

    public void write(String input) {
        terminal.puts(InfoCmp.Capability.carriage_return);
        terminal.writer().println(ConsoleColor.toColoredString('&', input));
        terminal.flush();
    }
}
