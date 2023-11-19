package de.tomjuri.hycloud.controller.console;

import de.tomjuri.hycloud.controller.command.CommandProvider;
import de.tomjuri.hycloud.controller.console.completion.ConsoleCompleter;
import de.tomjuri.hycloud.controller.console.completion.ConsoleCompletionMatcher;
import dev.derklaro.aerogel.auto.Provides;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.SneakyThrows;
import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.LineReader;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

@Singleton
@Provides(Console.class)
public class ConsoleImpl implements Console {

    @Getter
    private final CommandProvider commandProvider;
    private final Terminal terminal;
    @Getter
    private final LineReaderImpl lineReader;
    private final ConsoleRunner runner;

    @Inject
    @SneakyThrows
    public ConsoleImpl(CommandProvider commandProvider) {
        if(!installAnsi()) System.out.println("Failed to install Ansi. Colors will not work.");
        this.commandProvider = commandProvider;
        terminal = TerminalBuilder.builder().system(true).build();
        lineReader = new LineReaderImpl(terminal, "HyCloud-Terminal", null);
        lineReader.setCompleter(new ConsoleCompleter(this));
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
        runner = new ConsoleRunner(this);
    }

    @Override
    public void start() {
        runner.start();
    }

    @Override
    @SneakyThrows
    public void close() {
        AnsiConsole.systemUninstall();
        runner.interrupt();
        terminal.close();
    }

    @Override
    public void write(String input) {
        terminal.puts(InfoCmp.Capability.carriage_return);
        terminal.writer().println(ConsoleColor.toColoredString('&', input));
        terminal.flush();
    }

    private boolean installAnsi() {
        try {
            AnsiConsole.systemInstall();
            return true;
        } catch (Throwable t) {
            return false;
        }
    }
}
