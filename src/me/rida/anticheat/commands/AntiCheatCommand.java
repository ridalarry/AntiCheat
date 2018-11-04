package me.rida.anticheat.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.AntiCheatAPI;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.needscleanup.UtilsB;

public class AntiCheatCommand implements CommandExecutor {
	private AntiCheat AntiCheat;

	public AntiCheatCommand(AntiCheat AntiCheat) {
		this.AntiCheat = AntiCheat;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if(args.length == 0) {
        sender.sendMessage(ChatColor.RED + "AntiCheat v" + AntiCheat.getInstance().getDescription().getVersion() + " : Created By " + ChatColor.AQUA + "[Rida, Mr_JaVa_ , funkemunky]");
        sender.sendMessage(ChatColor.RED + "Do '/anticheat help' for list of commands.");
        
			if (sender instanceof Player) {
				Player p = (Player) sender;
			} else {
				sender.sendMessage(
						Color.Red + "This is for players only! Do /anticheat help to find a command you can do here.");
			}
			return true;
		} else {
			}
			if (args[0].equalsIgnoreCase("reload")) {
				sender.sendMessage(AntiCheat.PREFIX + Color.Gray + "Reloading AntiCheat...");
				AntiCheat.reloadConfig();
				sender.sendMessage(AntiCheat.PREFIX + Color.Green + "Successfully reloaded AntiCheat." + Color.Red + " Restart is recommended specially if noticed any bug!");
				return true;
			}
			if (args[0].equalsIgnoreCase("help")) {
				sender.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
				sender.sendMessage(Color.Red + Color.Bold + "AntiCheat Help:");
				sender.sendMessage(" ");
				sender.sendMessage(Color.Gray + "/anticheat" + Color.Reset + " help" + Color.Gray + "  - View the help page.");
				sender.sendMessage(Color.Gray + "/anticheat" + Color.Reset + " ping" + Color.Gray + "  - Get your ping.");
				sender.sendMessage(Color.Gray + "/anticheat" + Color.Reset + " reload" + Color.Gray + "   - Reload AntiCheat.");
				sender.sendMessage(Color.Gray + "/alerts" + Color.Gray + "  - Toggle alerts on and off.");
				sender.sendMessage(Color.Gray + "/getlog" + Color.Reset + " <player> <page>" + Color.Gray + " - Get player bans by AntiCheat.");
				sender.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
				return true;
			}
			if (args[0].equalsIgnoreCase("ping")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (args.length == 1) {
						sender.sendMessage(AntiCheat.PREFIX + Color.DGray + "[Vanilla] " + Color.Gray + "Your ping: " + Color.Red
								+ AntiCheat.getLag().getPing(p));
						sender.sendMessage(AntiCheat.PREFIX + Color.DGray + "[AntiCheat] " + Color.Gray + "Your ping: " + Color.Red
								+ AntiCheatAPI.getPing(p));
						return true;
					}
					if (args.length == 2) {
						Player target = Bukkit.getPlayer(args[1]);
						if (target == null) {
							sender.sendMessage(AntiCheat.PREFIX + Color.Red + "That player is not online!");
							return true;
						}
						sender.sendMessage(AntiCheat.PREFIX + Color.White + target.getName() + "'s " + Color.Gray + " ping: "
								+ Color.Red + AntiCheat.getLag().getPing(target));
						sender.sendMessage(AntiCheat.PREFIX + Color.White + target.getName() + "'s " + Color.Gray + " ping: "
								+ Color.Red + AntiCheatAPI.getPing(target));
						return true;
					}
					sender.sendMessage(AntiCheat.PREFIX + Color.Red + "Incorrect arguments. Usage: /anticheat ping [player]");
				} 
				else {
					sender.sendMessage(Color.Red + "This is for players only!");
				}
				return true;
			}

			else {
			sender.sendMessage(Color.Red + "Unknown argument '/" + alias + " " + args[0] + "'! Do " + Color.Italics
					+ "/anticheat help " + Color.Red + "for more info!");
		}
		return true;
				}

}


