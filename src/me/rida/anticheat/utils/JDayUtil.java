package me.rida.anticheat.utils;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.rida.anticheat.AntiCheat;

public class JDayUtil {

	@SuppressWarnings("unused")
	public static void signalTimer(){
		final Config pending = new Config("pendingusers");
		new BukkitRunnable(){
			@Override
			public void run(){
				executeBanWave();
				AntiCheat.getInstance().saveConfig();
			} //20 = one second
		}.runTaskTimer(AntiCheat.getInstance(), 20 * 86400, 20 * 86400);
	}

	public static void executeBanWave() {
		final Config pending = new Config("pendingusers");
		final Config banned = new Config("bannedusers");
		final String commands = AntiCheat.getInstance().getConfig().getString("settings.bancmd");
		int count = 0;

		for (final String s : pending.getConfigFile().getConfigurationSection("PendingUsers").getKeys(false)) {
			count++;
			final String name = pending.getConfigFile().getString("PendingUsers." + s + ".Name");
			final String uuid = pending.getConfigFile().getString("PendingUsers." + s + ".UUID");
			final String reason = pending.getConfigFile().getString("PendingUsers." + s + ".Reason");
			final String executedby = pending.getConfigFile().getString("PendingUsers." + s + ".ExecutedBy");
			final String wasonline = pending.getConfigFile().getString("PendingUsers." + s + ".wasOnline");
			final String date = pending.getConfigFile().getString("PendingUsers." + s + ".Date");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commands.replace("%player%", uuid));

			banned.getConfigFile().set("BannedUsers." + s + ".Name", name);
			banned.getConfigFile().set("BannedUsers." + s + ".UUID", uuid);
			banned.getConfigFile().set("BannedUsers." + s + ".Date", date);
			banned.getConfigFile().set("BannedUsers." + s + ".Reason", reason);
			banned.getConfigFile().set("BannedUsers." + s + ".ExecutedBy", executedby);
			banned.getConfigFile().set("BannedUsers." + s + ".wasOnline", wasonline);
			banned.saveConfigFile();
		}
		pending.getConfigFile().set("PendingUsers", null);
		pending.saveConfigFile();
		AntiCheat.getInstance().getConfig().set("settings.bans", AntiCheat.getInstance().getConfig().getInt("settings.bans") + count);
		AntiCheat.getInstance().saveConfig();
	}

	@SuppressWarnings("unused")
	public static int getAmountToBan(){
		final Config pending = new Config("pendingusers");
		int count = 0;
		if ( pending.getConfigFile().getConfigurationSection("PendingUsers") == null ) {
			return 0;
		}
		for ( final String s : pending.getConfigFile().getConfigurationSection("PendingUsers").getKeys(false) ) {
			count++;
		}
		return count;
	}
}