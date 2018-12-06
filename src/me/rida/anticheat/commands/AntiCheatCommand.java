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
import me.rida.anticheat.other.GUI;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.MathUtil;

public class AntiCheatCommand implements CommandExecutor {
	private AntiCheat AntiCheat;

	public AntiCheatCommand(AntiCheat AntiCheat) {
		this.AntiCheat = AntiCheat;
	}
	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender s, Command c, String a, String[] g) {
		if (!s.hasPermission("anticheat.staff")) {
			s.sendMessage(Color.Red + "No permission.");
			return true;
		}
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
		if (g[0].equalsIgnoreCase("bchecks")) {List<String> checkNames = new ArrayList<>();

		for(Check checkLoop : AntiCheat.getChecks()) {
			checkNames.add((checkLoop.isBannable() ? Color.Green + checkLoop.getIdentifier() : Color.Red + checkLoop.getIdentifier()) + Color.Gray);
		}
		s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
		s.sendMessage(Color.Gray + "Checks: " + checkNames.toString());
		s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
		return true;
		}
		if (g[0].equalsIgnoreCase("btchecks")) {List<String> checkNames = new ArrayList<>();

		for(Check checkLoop : AntiCheat.getChecks()) {
			checkNames.add((checkLoop.hasBanTimer() ? Color.Green + checkLoop.getIdentifier() : Color.Red + checkLoop.getIdentifier()) + Color.Gray);
		}
		s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
		s.sendMessage(Color.Gray + "Checks: " + checkNames.toString());
		s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
		return true;
		}
		if (g[0].equalsIgnoreCase("jchecks")) {List<String> checkNames = new ArrayList<>();

		for(Check checkLoop : AntiCheat.getChecks()) {
			checkNames.add((checkLoop.isJudgmentDay() ? Color.Green + checkLoop.getIdentifier() : Color.Red + checkLoop.getIdentifier()) + Color.Gray);
		}
		s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
		s.sendMessage(Color.Gray + "Checks: " + checkNames.toString());
		s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
		return true;
		}
		if (g[0].equalsIgnoreCase("mvchecks")) {List<String> checkNames = new ArrayList<>();

		for(Check checkLoop : AntiCheat.getChecks()) {
			checkNames.add(Color.Green + checkLoop.getIdentifier() + ": " + checkLoop.getMaxViolations());
		}
		s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
		s.sendMessage(Color.Gray + "Checks: " + checkNames.toString());
		s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
		return true;
		}
		if (g[0].equalsIgnoreCase("vnchecks")) {List<String> checkNames = new ArrayList<>();

		for(Check checkLoop : AntiCheat.getChecks()) {
			checkNames.add(Color.Green + checkLoop.getIdentifier() + ": " + checkLoop.getViolationsToNotify());
		}
		s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
		s.sendMessage(Color.Gray + "Checks: " + checkNames.toString());
		s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
		return true;
		}
		if (g[0].equalsIgnoreCase("vtchecks")) {List<String> checkNames = new ArrayList<>();

		for(Check checkLoop : AntiCheat.getChecks()) {
			checkNames.add(Color.Green + checkLoop.getIdentifier() + ": " + checkLoop.getViolationResetTime());
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
		if (g[0].equalsIgnoreCase("gui")) {
			if (s instanceof Player) {
				Player p = (Player) s;
				GUI.openAntiCheatMain(p);
			} else {
				s.sendMessage(
						Color.Red + "This is for players only! Do /AntiCheat help to find a command you can do here.");
			}
			return true;
		}
			if (g[0].equalsIgnoreCase("ping")) {
				if (s instanceof Player) {
					Player p = (Player) s;
					if (g.length == 1) {
						s.sendMessage(AntiCheat.PREFIX + Color.Gray + "[Vanilla] " + Color.Gray + "Your ping: " + Color.Red
								+ AntiCheat.getLag().getPing(p));
						s.sendMessage(AntiCheat.PREFIX + Color.Gray + "[AntiCheat] " + Color.Gray + "Your ping: " + Color.Red
								+ AntiCheatAPI.getPing(p));
						return true;
					}
					if (g.length == 2) {
						Player target = Bukkit.getPlayer(g[1]);
						if (target == null) {
							s.sendMessage(AntiCheat.PREFIX + Color.Red + "That player is not online!");
							return true;
						}
						s.sendMessage(AntiCheat.PREFIX + Color.Gray + "[Vanilla] " +Color.White + target.getName() + "'s " + Color.Gray + "ping: "
								+ Color.Red + AntiCheat.getLag().getPing(target));
						s.sendMessage(AntiCheat.PREFIX + Color.Gray + "[AntiCheat] " +Color.White + target.getName() + "'s " + Color.Gray + "ping: "
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
			if (g[0].equalsIgnoreCase("violations")) {
				if (g.length != 2) {
					s.sendMessage(Color.Red + "Invalid argument!");
					return true;
				}
				if (s instanceof Player) {
					String playerName2 = g[1];
					Player player = this.AntiCheat.getServer().getPlayer(playerName2);
					Player p = (Player) s;
					if (player == null || !player.isOnline()) {
						s.sendMessage(Color.Red + "This player is not online!");
						return true;
					}
					GUI.openStatus(p, player);
				} else {
					s.sendMessage(Color.Red + "This is for players only!");
				}
				return true;
			}
			if (g[0].equalsIgnoreCase("dump")) {
				String playerName = g[1];
				String checkName = g[2];
				Check check = null;
				for (Check checkcheck : this.AntiCheat.getChecks()) {
					if (checkcheck.getIdentifier().equalsIgnoreCase(checkName)) {
						check = checkcheck;
					}
				}
				if (check == null) {
					s.sendMessage(Color.Red + "This check does not exist!");
					return true;
				}
				String result = check.dump(playerName);
				if (result == null) {
					s.sendMessage(Color.Red + "Error creating dump file for player " + playerName + ".");
				}
				s.sendMessage(
						AntiCheat.PREFIX + Color.Gray + "Dropped dump thread at " + Color.Yellow + "/dumps/" + result + ".txt");
				return true;
			}if (g[0].equalsIgnoreCase("clean") || g[0].equalsIgnoreCase("gc")) {
				s.sendMessage(AntiCheat.PREFIX + Color.Gray + "Forcing garbage collector..." + Color.Gray + "[" + Color.Aqua
						+ AntiCheat.getLag().getFreeRam() + Color.Gray + "/" + Color.Red + AntiCheat.getLag().getMaxRam() + Color.Gray
						+ "]");
				System.gc();
				s.sendMessage(AntiCheat.PREFIX + Color.Green + "Completed garbage collector! " + Color.Gray + "[" + Color.Aqua
						+ MathUtil.trim(3, AntiCheat.getLag().getFreeRam()) + Color.Gray + "/" + Color.Red
						+ MathUtil.trim(3, AntiCheat.getLag().getMaxRam()) + Color.Gray + "]");
				return true;
			}
			if (g[0].equalsIgnoreCase("lag") || g[0].equalsIgnoreCase("performance")) {
				s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
				s.sendMessage(Color.Red + Color.Bold + "Performance Usage:");
				s.sendMessage("");
				s.sendMessage(Color.Gray + "TPS: " + Color.White + MathUtil.trim(2, AntiCheat.getLag().getTPS()));
				s.sendMessage(Color.Gray + "Free Ram: " + Color.White + AntiCheat.getLag().getFreeRam() + "MB");
				s.sendMessage(Color.Gray + "Max Ram: " + Color.White + AntiCheat.getLag().getMaxRam() + "MB");
				s.sendMessage(Color.Gray + "Used Ram: " + Color.White
						+ Math.abs(AntiCheat.getLag().getMaxRam() - AntiCheat.getLag().getFreeRam()) + "MB");
				if (Math.abs(
						AntiCheat.getLag().getMaxRam() - AntiCheat.getLag().getFreeRam()) > AntiCheat.getLag().getMaxRam()
								/ 2.1) {
					s.sendMessage(
							Color.Aqua + Color.Italics + "It is recommended you do /AntiCheat clean to clear up some RAM.");
				}
				s.sendMessage(
						AntiCheat.getLag().getLag() > 20 ? Color.Red + "Server Usage: " + AntiCheat.getLag().getLag() + "%"
								: Color.Green + "Server Usage: " + AntiCheat.getLag().getLag() + "%");

				s.sendMessage(Color.DGray + Color.Strike + "----------------------------------------------------");
				return true;
			}
			if (g[0].equalsIgnoreCase("test")) {
				s.sendMessage(String.valueOf(10 % 7));
				return true;
			}
			if (g[0].equalsIgnoreCase("bans")) {
				if (s instanceof Player) {
					Player p = (Player) s;
					GUI.openBans(p);
				} else {
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