package me.rida.anticheat.commands;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.MathUtil;
import net.minecraft.util.org.apache.commons.io.FileUtils;

public class GetLogCommand1_7 implements CommandExecutor {

	private final AntiCheat AntiCheat;

	public GetLogCommand1_7(AntiCheat AntiCheat) {
		this.AntiCheat = AntiCheat;
	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String l, String[] g) {
		if (!s.hasPermission("anticheat.log") && !s.hasPermission("anticheat.admin")) {
			s.sendMessage(Color.Red + "No permission.");
			return true;
		}

		if (g.length != 2) {
			s.sendMessage(AntiCheat.PREFIX + Color.Red + "Usage: /getlog <name> <page>");
			return true;
		}

		final String player = g[0];
		final String a = g[1];
		final Pattern pattern = Pattern.compile("^[0-9]");
		final Matcher matcher = pattern.matcher(a);
		if (!(matcher.find())) {
			s.sendMessage(AntiCheat.PREFIX + Color.Red + "Usage: /getlog <name> <page>");
			return true;
		}
		if (!(MathUtil.isInteger(a))) {
			s.sendMessage(AntiCheat.PREFIX + Color.Red + "Usage: /getlog <name> <page>");
			return true;

		}
		final int page = Math.round(Integer.parseInt(g[1]));
		if (page < 1) {
			s.sendMessage(AntiCheat.PREFIX + Color.Red + "Usage: /getlog <name> <page>");
			return true;
		}
		final String path = AntiCheat.getDataFolder() + File.separator + "logs" + File.separator + g[0] + ".txt";
		final File file = new File(path);
		if (!file.exists()) {
			s.sendMessage(AntiCheat.PREFIX + Color.Red + "The player '" + Color.Bold + player + Color.Red
					+ "' does not have a ban log! This is CASE SENSITIVE!");
			return true;
		}
		try {
			final List<String> lines = FileUtils.readLines(file);
			if ((lines.size() / (page * 10)) < 1) {
				s.sendMessage(AntiCheat.PREFIX + Color.Red + "There is no page " + page + " for this log!");
				return true;
			}
			s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
			s.sendMessage(Color.Gray + "Log for " + Color.White + player + Color.Red + " Page " + page);
			s.sendMessage("");
			for (int i = (page - 1) * 10; (i) < page * 10; i++) {
				if (i < lines.size()) {
					s.sendMessage(lines.get(i));
				}
			}
			s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
		} catch (final Exception e) {
			s.sendMessage(
					AntiCheat.PREFIX + Color.Red + "Unknown error occured when tryin to read file and upload to pastebin!");
			e.printStackTrace();
		}
		return true;
	}
}