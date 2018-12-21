package me.rida.anticheat;

import java.util.List;

import org.bukkit.plugin.Plugin;

import me.rida.anticheat.checks.Check;

public class AntiCheatAPI {

	private static AntiCheat AntiCheat;

	@SuppressWarnings("unused")
	private Plugin plugin;

	public AntiCheatAPI(Plugin plugin) {
		this.plugin = plugin;
		AntiCheat = (AntiCheat) plugin;
	}

	public static List<Check> getChecks() {
		return AntiCheat.getChecks();
	}
}