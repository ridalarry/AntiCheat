package me.rida.anticheat.commands;

import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.Config;
import me.rida.anticheat.utils.JDayUtil;

public class JDayCommand implements CommandExecutor {
	private final AntiCheat AntiCheat;

	public JDayCommand(AntiCheat AntiCheat) {
		this.AntiCheat = AntiCheat;
	}
	@SuppressWarnings({ "unused", "deprecation" })
	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String alias,
			final String[] args) {
		if (!sender.hasPermission("anticheat.staff")) {
			sender.sendMessage(Color.Red + "No permission.");
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(AntiCheat.PREFIX + Color.translate("&7Invalid argument!"));
			sender.sendMessage(AntiCheat.PREFIX + Color.translate("&7Usage '/jday start' to start and '/jday add PLAYER' to add a player to the list!"));
			return true;
		}
		if (args[0].equalsIgnoreCase("start")) {
			final String type = args[0];
			if ( JDayUtil.getAmountToBan() == 0 ) {
				sender.sendMessage(AntiCheat.PREFIX + Color.translate("&7There are no pending bans!"));
				return true;
			}
			sender.sendMessage(AntiCheat.PREFIX + Color.translate("&7Judgement day starting!"));
			JDayUtil.executeBanWave();
			return true;
		}
		else if (args[0].equalsIgnoreCase("add")) {
			final String type = args[0];
			if (args.length < 2) {
				sender.sendMessage(AntiCheat.PREFIX + Color.translate("&7Invalid argument!"));
				sender.sendMessage(AntiCheat.PREFIX + Color.translate("&7Usage '/jday start' to start and '/jday add PLAYER REASON' to add a player to the list!"));
				return true;
			}
			final String playerName = args[1];
			final Player player = Bukkit.getServer().getPlayer(playerName);
			final String lowerCase;
			if (this.AntiCheat.getConfig().getBoolean("testmode") == true) {
				sender.sendMessage(Color.Red + "Test mode is enabled therefore this is disabled!");
			} else {
				final OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
				final Config pending = new Config("pendingusers");

				final StringBuilder str = new StringBuilder();
				String reason;
				for (int i = 2; i < args.length; i++) {
					str.append(args[i]).append(" ");
				}
				reason = str.toString();
				if (reason.equalsIgnoreCase(null) || reason.isEmpty() || reason.equals(" ")) {
					reason = "No Reason Specified. ";
				} else {
					reason = str.toString();
				}
				final int reasonLength = reason.length();
				reason = reason.substring(0, reasonLength-1);
				pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".Name", target.getName());
				pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".UUID", String.valueOf(target.getUniqueId()));
				pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".Date", String.valueOf(Calendar.getInstance().getTime()));
				pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".Reason", reason);
				pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".ExecutedBy", sender.getName());
				pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".wasOnline", sender.getName());
				if (target.isOnline()) {
					pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".wasOnline", true);
				} else {
					pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".wasOnline", false);
				}

				pending.saveConfigFile();
				sender.sendMessage(Color.Red + args[1] + Color.Red + " has been added to the list!");
			}
		}
		else {
			sender.sendMessage(AntiCheat.PREFIX + Color.translate("&7Invalid argument!"));
			sender.sendMessage(AntiCheat.PREFIX + Color.translate("&7Usage '/jday start' to start and '/jday add PLAYER' to add a player to the list!"));
			return true;
		}
		return true;
	}
}