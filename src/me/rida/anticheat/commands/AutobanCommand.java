package me.rida.anticheat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.utils.Color;

public class AutobanCommand implements CommandExecutor {
	private AntiCheat AntiCheat;

	public AutobanCommand(AntiCheat AntiCheat) {
		this.AntiCheat = AntiCheat;
	}

	@SuppressWarnings("unused")
	public boolean onCommand(final CommandSender sender, final Command command, final String alias,
			final String[] args) {
		if (!sender.hasPermission("anticheat.staff")) {
			sender.sendMessage(Color.Red + "No permission.");
			return true;
		}
		if (args.length == 2) {
			final String type = args[0];
			final String playerName = args[1];
			final Player player = Bukkit.getServer().getPlayer(playerName);
			if (player == null || !player.isOnline()) {
				sender.sendMessage(Color.Red + "This player does not exist.");
				return true;
			}
			if (this.AntiCheat.getAutobanQueue().contains(player)) {
				String lowerCase;
				switch (lowerCase = type.toLowerCase()) {
				case "cancel": {
					System.out.println("[" + player.getUniqueId().toString() + "] " + sender.getName()
							+ "'s auto-ban has been cancelled by " + sender.getName());
					Bukkit.broadcast(
							Color.translate(
									AntiCheat.PREFIX + AntiCheat.getConfig().getString("alerts.secondary")
											+ player.getName() + AntiCheat.getConfig().getString("alerts.primary")
											+ "'s auto-ban has been cancelled by "
											+ AntiCheat.getConfig().getString("alerts.secondary") + sender.getName()),
							"anticheat.staff");
					break;
				}
				case "ban": {
					if (this.AntiCheat.getConfig().getBoolean("testmode") == true) {
						sender.sendMessage(Color.Red + "Test mode is enabled therefore this is disabled!");
					} else {
						System.out.println("[" + player.getUniqueId().toString() + "] " + sender.getName()
								+ "'s auto-ban has been forced by " + sender.getName());
						Bukkit.broadcast(Color.translate(
								AntiCheat.PREFIX + AntiCheat.getConfig().getString("alerts.secondary") + player.getName()
										+ AntiCheat.getConfig().getString("alerts.primary")
										+ "'s auto-ban has been forced by "
										+ AntiCheat.getConfig().getString("alerts.secondary") + sender.getName()),
								"anticheat.staff");
						this.AntiCheat.autobanOver(player);
					}
					break;
				}
				default:
					break;
				}
				this.AntiCheat.removeFromAutobanQueue(player);
				this.AntiCheat.removeViolations(player);
			} else {
				sender.sendMessage(String.valueOf(Color.Red) + "This player is not in the autoban queue!");
			}
		}
		return true;
	}
}