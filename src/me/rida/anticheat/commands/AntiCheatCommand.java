package me.rida.anticheat.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.AntiCheatAPI;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.Color;

public class AntiCheatCommand implements CommandExecutor {
	private AntiCheat AntiCheat;

	public AntiCheatCommand(AntiCheat AntiCheat) {
		this.AntiCheat = AntiCheat;
	}
	@Override
	public boolean onCommand(CommandSender s, Command c, String a, String[] g) {

        if(g.length == 0) {
        s.sendMessage(Color.Red + "Do '/anticheat help' for list of commands.");
        
			if (s instanceof Player) {
				Player p = (Player) s;
			} else {
				s.sendMessage(
						Color.Red + "This is for players only! Do /anticheat help to find a command you can do here.");
			}
			return true;
		} else {
			}
			if (g[0].equalsIgnoreCase("checks")) {List<String> checkNames = new ArrayList<>();

            for(Check checkLoop : AntiCheat.getChecks()) {
                checkNames.add((checkLoop.isEnabled() ? Color.Green + checkLoop.getIdentifier() : Color.Red + checkLoop.getIdentifier()) + Color.Gray);
            }
				s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
	            s.sendMessage(Color.Gray + "Checks: " + checkNames.toString());
				s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
				return true;
			}
			if (g[0].equalsIgnoreCase("reload")) {
				s.sendMessage(AntiCheat.PREFIX + Color.Gray + "Reloading AntiCheat...");
				AntiCheat.reloadConfig();
				s.sendMessage(AntiCheat.PREFIX + Color.Green + "Successfully reloaded AntiCheat.");
				s.sendMessage(AntiCheat.PREFIX + Color.Red + " Restart is recommended specially if noticed any bug!");
				return true;
			}
			if (g[0].equalsIgnoreCase("help")) {
				s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
				s.sendMessage(Color.Red + Color.Bold + "AntiCheat Help:");
				s.sendMessage(" ");
				s.sendMessage(Color.Gray + "/anticheat" + Color.Reset + " help" + Color.Gray + "  - View the help page.");
				s.sendMessage(Color.Gray + "/anticheat" + Color.Reset + " ping" + Color.Gray + "  - Get your ping.");
				s.sendMessage(Color.Gray + "/anticheat" + Color.Reset + " reload" + Color.Gray + "   - Reload AntiCheat.");
				s.sendMessage(Color.Gray + "/anticheat" + Color.Reset + " checks" + Color.Gray + "   - List the checks.");
				s.sendMessage(Color.Gray + "/alerts" + Color.Gray + "  - Toggle alerts on and off.");
				s.sendMessage(Color.Gray + "/getlog" + Color.Reset + " <player> <page>" + Color.Gray + " - Get player bans by AntiCheat.");
				s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
				return true;
			}
			if (g[0].equalsIgnoreCase("ping")) {
				if (s instanceof Player) {
					Player p = (Player) s;
					if (g.length == 1) {
						s.sendMessage(AntiCheat.PREFIX + Color.DGray + "[Vanilla] " + Color.Gray + "Your ping: " + Color.Red
								+ AntiCheat.getLag().getPing(p));
						s.sendMessage(AntiCheat.PREFIX + Color.DGray + "[AntiCheat] " + Color.Gray + "Your ping: " + Color.Red
								+ AntiCheatAPI.getPing(p));
						return true;
					}
					if (g.length == 2) {
						Player target = Bukkit.getPlayer(g[1]);
						if (target == null) {
							s.sendMessage(AntiCheat.PREFIX + Color.Red + "That player is not online!");
							return true;
						}
						s.sendMessage(AntiCheat.PREFIX + Color.White + target.getName() + "'s " + Color.Gray + " ping: "
								+ Color.Red + AntiCheat.getLag().getPing(target));
						s.sendMessage(AntiCheat.PREFIX + Color.White + target.getName() + "'s " + Color.Gray + " ping: "
								+ Color.Red + AntiCheatAPI.getPing(target));
						return true;
					}
					s.sendMessage(AntiCheat.PREFIX + Color.Red + "Incorrect arguments. Usage: /anticheat ping [player]");
				} 
				else {
					s.sendMessage(Color.Red + "This is for players only!");
				}
				return true;
			}

			else {
			s.sendMessage(Color.Red + "Unknown argument '/" + a + " " + g[0] + "'! Do " + Color.Italics
					+ "/anticheat help " + Color.Red + "for more info!");
		}
		return true;
				}

}