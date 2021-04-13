package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class FileLogger implements DebugListener {
    static {
        try {
            FileInputStream configFile = new FileInputStream("logger.properties");
            LogManager.getLogManager().readConfiguration(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static final Map<LogLevel, Level> levelMapper = Map.of(
            LogLevel.DEBUG, Level.FINE,
            LogLevel.INFO, Level.INFO,
            LogLevel.WARN, Level.WARNING,
            LogLevel.ERROR, Level.SEVERE);

    final Logger logger;


    public FileLogger(Class clazz) {
        logger = Logger.getLogger(clazz.getName());
    }

    @Override
    public void logMessage(LogLevel level, String category, String message) {
        String logStatement = new StringBuilder()
                .append(category)
                .append(" - ")
                .append(message)
                .toString();
        logger.log(levelMapper.get(level), logStatement);
    }

}
