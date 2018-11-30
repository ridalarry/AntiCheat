package me.rida.anticheat.checks.client;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;

public class VapeA extends Check implements PluginMessageListener {

	public VapeA(AntiCheat AntiCheat) {
		super("VapeA", "Vape", CheckType.Client, true, true, false, false, 0, 1, 600000L, AntiCheat);
		setEnabled(true);
		setBannable(true);
		setJudgementDay(false);
		
		setAutobanTimer(false);
		
		setMaxViolations(10);
		setViolationsToNotify(1);
		setViolationResetTime(600000L);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onPlayerJoin(PlayerJoinEvent event) {
		event.getPlayer().sendMessage("§8 §8 §1 §3 §3 §7 §8 ");
	}

	public void onPluginMessageReceived(String s, Player p, byte[] data) {
		@SuppressWarnings("unused")
		String str;
		try {
			str = new String(data);
		} catch (Exception ex) {
			str = "";
		}
		getAntiCheat().logCheat(this, p, "Banned for using Cracked Vape!", "(Type: A)");
	}
}