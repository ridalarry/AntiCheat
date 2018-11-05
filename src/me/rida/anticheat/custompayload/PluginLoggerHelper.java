package me.rida.anticheat.custompayload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.*;

public class PluginLoggerHelper {

    public static java.util.logging.Logger openLogger(File file, String format) throws Throwable {
        try {
            Logger logger = Logger.getAnonymousLogger();
            FileHandler fh;
            File outDir = file.toPath().getParent().toFile();
            if (!outDir.exists()) {
                //noinspection ResultOfMethodCallIgnored
                outDir.mkdirs();
            }
            fh = new FileHandler(file.getAbsolutePath(), 0, 1, true);
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
            PluginLoggerFormatter formatter = new PluginLoggerFormatter(format);
            fh.setFormatter(formatter);
            return logger;
        } catch (Throwable ex) {
            throw new Throwable("Error creating logger", ex);
        }
    }

    public static void closeLogger(Logger logger) {
        for (Handler fh : logger.getHandlers()) {
            fh.close();
            logger.removeHandler(fh);
        }
    }
}