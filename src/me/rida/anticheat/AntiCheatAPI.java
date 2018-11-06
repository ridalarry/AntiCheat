package me.rida.anticheat;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.other.Latency;

public class AntiCheatAPI {

	private static AntiCheat daedalus;

	@SuppressWarnings("unused")
	private Plugin plugin;

	public AntiCheatAPI(Plugin plugin) {
		this.plugin = plugin;
		daedalus = (AntiCheat) plugin;
	}

	public static List<Check> getChecks() {
		return daedalus.getChecks();
	}

	public static Integer getPing(Player player) {
		return Integer.valueOf(Math.round((Latency.getLag(player) / 2) * 6));
	}

}
