package de.tomjuri.hycloud.controller.console;

import org.jline.reader.UserInterruptException;

public class ConsoleRunner extends Thread {

    private final Console console;

    public ConsoleRunner(Console console) {
        this.console = console;
        setDaemon(false);
        setName("JlineTerminalRunner");
        setPriority(1);
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = console.getLineReader().readLine(ConsoleColor.toColoredString('&', "&bHyCloud &7» "))) != null) {
                if (line.trim().isEmpty()) continue;
                console.getCommandProvider().call(line.trim());
            }
        } catch(UserInterruptException ignored) {
            System.exit(0);
        }
    }
}
