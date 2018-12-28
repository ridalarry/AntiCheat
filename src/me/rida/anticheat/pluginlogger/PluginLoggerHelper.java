package me.rida.anticheat.pluginlogger;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class PluginLoggerHelper {

	public static java.util.logging.Logger openLogger(File file, String format) throws Throwable {
		try {
			final Logger logger = Logger.getAnonymousLogger();
			FileHandler fh;
			final File outDir = file.toPath().getParent().toFile();
			if (!outDir.exists()) {
				outDir.mkdirs();
			}
			fh = new FileHandler(file.getAbsolutePath(), 0, 1, true);
			logger.addHandler(fh);
			logger.setUseParentHandlers(false);
			final PluginLoggerFormatter formatter = new PluginLoggerFormatter(format);
			fh.setFormatter(formatter);
			return logger;
		} catch (final Throwable ex) {
			throw new Throwable("Error creating logger", ex);
		}
	}

	public static void closeLogger(Logger logger) {
		for (final Handler fh : logger.getHandlers()) {
			fh.close();
			logger.removeHandler(fh);
		}
	}
}