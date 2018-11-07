package me.rida.anticheat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.utils.Color;

public class AlertsCommand implements CommandExecutor {
	private AntiCheat AntiCheat;

	public AlertsCommand(AntiCheat AntiCheat) {
		this.AntiCheat = AntiCheat;
	}

	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You have to be a player to run this command!");
			return true;
		}
		Player player = (Player) sender;
		if (!player.hasPermission("anticheat.staff")) {
			sender.sendMessage(Color.Red + "No permission.");
			return true;
		}
		if (this.AntiCheat.hasAlertsOn(player)) {
			this.AntiCheat.toggleAlerts(player);
			player.sendMessage(Color.translate(
					AntiCheat.PREFIX + AntiCheat.getConfig().getString("alerts.primary") + "Alerts toggled " + Color.Red
							+ "off" + AntiCheat.getConfig().getString("alerts.primary") + "!"));
		} else {
			this.AntiCheat.toggleAlerts(player);
			player.sendMessage(Color.translate(
					AntiCheat.PREFIX + AntiCheat.getConfig().getString("alerts.primary") + "Alerts toggled " + Color.Green
							+ "on" + AntiCheat.getConfig().getString("alerts.primary") + "!"));
		}
		return true;
	}
}
