package me.rida.anticheat.pluginlogger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class PluginLoggerFormatter extends Formatter {
	private static final String DEFAULT_FORMAT = "[%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s]: %5$s%6$s%n";
	private final Date dat = new Date();
	private final String format;

	PluginLoggerFormatter() {
		this(null);
	}

	PluginLoggerFormatter(String format) {
		if (format == null || format.isEmpty()) {
			format = DEFAULT_FORMAT;
		}
		try {
			String.format(format, new Date(), "", "", "", "", "");
		} catch (final IllegalArgumentException var3) {
			format = DEFAULT_FORMAT;
		}
		this.format = format;
	}

	@Override
	public synchronized String format(LogRecord record) {
		dat.setTime(record.getMillis());
		String source;
		if (record.getSourceClassName() != null) {
			source = record.getSourceClassName();
			if (record.getSourceMethodName() != null) {
				source += " " + record.getSourceMethodName();
			}
		} else {
			source = record.getLoggerName();
		}
		final String message = formatMessage(record);
		String throwable = "";
		if (record.getThrown() != null) {
			final StringWriter sw = new StringWriter();
			final PrintWriter pw = new PrintWriter(sw);
			pw.println();
			record.getThrown().printStackTrace(pw);
			pw.close();
			throwable = sw.toString();
		}
		return String.format(format,
				dat,
				source,
				record.getLoggerName(),
				record.getLevel().getName(),
				message,
				throwable);
	}
}