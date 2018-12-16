package me.rida.anticheat.utils;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.rida.anticheat.AntiCheat;

public class JDayUtil {

	@SuppressWarnings("unused")
	public static void signalTimer(){
		Config pending = new Config("pendingusers");
		new BukkitRunnable(){
			@Override
			public void run(){
				executeBanWave();
				AntiCheat.getInstance().saveConfig();
			} //20 = one second
		}.runTaskTimer(AntiCheat.getInstance(), 20 * 86400, 20 * 86400);
	}

	public static void executeBanWave() {
		Config pending = new Config("pendingusers");
		Config banned = new Config("bannedusers");
		String commands = AntiCheat.getInstance().getConfig().getString("settings.bancmd");
		int count = 0;

		for (String s : pending.getConfigFile().getConfigurationSection("PendingUsers").getKeys(false)) {
			count++;
			String name = pending.getConfigFile().getString("PendingUsers." + s + ".Name");
			String uuid = pending.getConfigFile().getString("PendingUsers." + s + ".UUID");
			String reason = pending.getConfigFile().getString("PendingUsers." + s + ".Reason");
			String executedby = pending.getConfigFile().getString("PendingUsers." + s + ".ExecutedBy");
			String wasonline = pending.getConfigFile().getString("PendingUsers." + s + ".wasOnline");
			String date = pending.getConfigFile().getString("PendingUsers." + s + ".Date");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commands.replace("%player%", uuid));

			banned.getConfigFile().set("PendingUsers." + s + ".Name", name);
			banned.getConfigFile().set("PendingUsers." + s + ".UUID", uuid);
			banned.getConfigFile().set("PendingUsers." + s + ".Date", date);
			banned.getConfigFile().set("PendingUsers." + s + ".Reason", reason);
			banned.getConfigFile().set("PendingUsers." + s + ".ExecutedBy", executedby);
			banned.getConfigFile().set("PendingUsers." + s + ".wasOnline", wasonline);
			banned.saveConfigFile();
		}
		pending.getConfigFile().set("PendingUsers", null);
		pending.saveConfigFile();
		AntiCheat.getInstance().getConfig().set("settings.bans", AntiCheat.getInstance().getConfig().getInt("settings.bans") + count);
		AntiCheat.getInstance().saveConfig();
	}

	@SuppressWarnings("unused")
	public static int getAmountToBan(){
		Config pending = new Config("pendingusers");
		int count = 0;
		if ( pending.getConfigFile().getConfigurationSection("PendingUsers") == null ) { return 0; }
		for ( String s : pending.getConfigFile().getConfigurationSection("PendingUsers").getKeys(false) ) {
			count++;
		}
		return count;
	}
}