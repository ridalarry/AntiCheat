package me.rida.anticheat.utils;

import java.lang.reflect.Field;

import me.rida.anticheat.AntiCheat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Ping {

	private double tps;

	public Ping(AntiCheat AntiCheat) {
		new BukkitRunnable() {
			long sec;
			long currentSec;
			int ticks;

			public void run() {
				this.sec = (System.currentTimeMillis() / 1000L);
				if (this.currentSec == this.sec) {
					this.ticks += 1;
				} else {
					this.currentSec = this.sec;
					Ping.this.tps = (Ping.this.tps == 0.0D ? this.ticks : (Ping.this.tps + this.ticks) / 2.0D);
					this.ticks = 0;
				}
			}
		}.runTaskTimerAsynchronously(AntiCheat, 1L, 1L);
	}

	public double getTPS() {
		return tps + 1.0D > 20.0D ? 20.0D : tps + 1.0D;
	}

	public static Object getFieldValue(Object instance, String fieldName) throws Exception {
		Field field = instance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(instance);
	}

	public static int getPing(Player who) {
		try {
			String bukkitversion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
			Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + bukkitversion + ".entity.CraftPlayer");
			Object handle = craftPlayer.getMethod("getHandle").invoke(who);
			Integer ping = (Integer) handle.getClass().getDeclaredField("ping").get(handle);

			return ping.intValue();
		} catch (Exception e) {
			return -1;
		}
	}

}
