package de.tomjuri.hycloud.controller.terminal;

import de.tomjuri.hycloud.api.command.ICommandSender;
import de.tomjuri.hycloud.controller.Controller;
import de.tomjuri.hycloud.controller.command.ConsoleCommandSender;
import org.jline.reader.LineReader;
import org.jline.reader.UserInterruptException;

public class ConsoleRunner extends Thread {

    private final LineReader lineReader;
    private final ICommandSender sender;

    public ConsoleRunner(LineReader lineReader) {
        this.lineReader = lineReader;
        this.sender = new ConsoleCommandSender();
        setDaemon(false);
        setName("JlineTerminalRunner");
        setPriority(1);
        start();
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = lineReader.readLine(ConsoleColor.toColoredString('&', "&bHyCloud &7» "))) != null) {
                if (line.trim().isEmpty()) continue;
                ((Controller) Controller.getInstance()).getCommandProvider().call(line.trim(), sender);
            }
        } catch(UserInterruptException ignored) {
            System.exit(0);
        }
    }
}
