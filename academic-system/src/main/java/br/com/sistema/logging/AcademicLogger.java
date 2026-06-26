package br.com.sistema.logging;

import java.io.IOException;
import java.util.logging.*;

public class AcademicLogger {

    private static final Logger LOGGER = Logger.getLogger("br.com.sistema");
    private static volatile boolean configured = false;

    public static Logger getLogger() {
        if (!configured) {
            configure();
        }
        return LOGGER;
    }

    private static synchronized void configure() {
        if (configured) return;
        try {
            LOGGER.setUseParentHandlers(false);

            FileHandler fileHandler = new FileHandler("academic-system.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            LOGGER.addHandler(fileHandler);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            consoleHandler.setLevel(Level.WARNING);
            LOGGER.addHandler(consoleHandler);

            LOGGER.setLevel(Level.ALL);
            configured = true;
        } catch (IOException e) {
            Logger.getAnonymousLogger().warning("Não foi possível configurar FileHandler: " + e.getMessage());
        }
    }
}
