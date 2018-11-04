package me.rida.anticheat.commands;

import java.util.List;
import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.utils.Color;

import org.apache.commons.io.FileUtils;

public class GetLogCommand implements CommandExecutor {

	private AntiCheat AntiCheat;

	public GetLogCommand(AntiCheat AntiCheat) {
		this.AntiCheat = AntiCheat;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("anticheat.log") && !sender.hasPermission("anticheat.admin")) {
			sender.sendMessage(Color.Red + "No permission.");
			return true;
		}

		if (args.length != 2) {
			sender.sendMessage(AntiCheat.PREFIX + Color.Red + "Usage: /getlog <name> <page>");
			return true;
		}

		String player = args[0];
		int page = Integer.parseInt(args[1]);
		String path = AntiCheat.getDataFolder() + File.separator + "logs" + File.separator + args[0] + ".txt";
		File file = new File(path);
		if (!file.exists()) {
			sender.sendMessage(AntiCheat.PREFIX + Color.Red + "The player '" + Color.Bold + player + Color.Red
					+ "' does not have a ban log! This is CASE SENSITIVE!");
			return true;
		}
		try {
			List<String> lines = FileUtils.readLines(file);
			if ((lines.size() / (page * 10)) < 1) {
				sender.sendMessage(AntiCheat.PREFIX + Color.Red + "There is no page " + page + " for this log!");
				return true;
			}
			sender.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
			sender.sendMessage(Color.Gray + "Log for " + Color.White + player + Color.Red + " Page " + page);
			sender.sendMessage("");
			for (int i = (page - 1) * 10; (i) < page * 10; i++) {
				if (i < lines.size()) {
					sender.sendMessage(lines.get(i));
				}
			}
			sender.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
		} catch (Exception e) {
			sender.sendMessage(
					AntiCheat.PREFIX + Color.Red + "Unknown error occured when tryin to read file and upload to pastebin!");
			e.printStackTrace();
		}
		return true;
	}

}
