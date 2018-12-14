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

	@Override
	public boolean onCommand(CommandSender s, Command command, String a, String[] g) {
		if (!(s instanceof Player)) {
			s.sendMessage("You have to be a player to run this command!");
			return true;
		}
		Player p = (Player) s;
		if (!p.hasPermission("anticheat.staff")) {
			s.sendMessage(Color.Red + "No permission.");
			return true;
		}
		if(g.length == 0) {
			if (this.AntiCheat.hasAlertsOn(p)) {
				this.AntiCheat.toggleAlerts(p);
				p.sendMessage(Color.translate(
						AntiCheat.PREFIX + AntiCheat.getConfig().getString("alerts.primary") + "Alerts toggled " + Color.Red
						+ "off" + AntiCheat.getConfig().getString("alerts.primary") + "!"));
			} else {
				this.AntiCheat.toggleAlerts(p);
				p.sendMessage(Color.translate(
						AntiCheat.PREFIX + AntiCheat.getConfig().getString("alerts.primary") + "Alerts toggled " + Color.Green
						+ "on" + AntiCheat.getConfig().getString("alerts.primary") + "!"));
			}
			return true;
		}
		if(g.length == 1) {
			if (g[0].equalsIgnoreCase("on")) {
				p.sendMessage(Color.translate(
						AntiCheat.PREFIX + AntiCheat.getConfig().getString("alerts.primary") + "Alerts toggled " + Color.Green
						+ "on" + AntiCheat.getConfig().getString("alerts.primary") + "!"));
				me.rida.anticheat.AntiCheat.AlertsOn.add(p);
				return true;

			}
			else if (g[0].equalsIgnoreCase("off")) {
				p.sendMessage(Color.translate(
						AntiCheat.PREFIX + AntiCheat.getConfig().getString("alerts.primary") + "Alerts toggled " + Color.Red
						+ "off" + AntiCheat.getConfig().getString("alerts.primary") + "!"));
				me.rida.anticheat.AntiCheat.AlertsOn.remove(p);
				return true;

			}
			else {
				p.sendMessage(Color.translate(
				AntiCheat.PREFIX + "Unknown argument!"));
				return true;
			}
		}
		return true;
	}
}