package me.rida.anticheat.checks.combat;

import org.bukkit.event.EventHandler;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.packets.PacketPlayerType;
import me.rida.anticheat.packets.events.PacketPlayerEvent;

public class TwitchA extends Check {
	public TwitchA(AntiCheat AntiCheat) {
		super("TwitchA", "Twitch", AntiCheat);

		this.setEnabled(true);
		this.setBannable(true);

		setMaxViolations(5);
	}

	@EventHandler
	public void Player(PacketPlayerEvent e) {
		if (e.getType() != PacketPlayerType.LOOK) {
			return;
		}
		if ((e.getPitch() > 90.1F) || (e.getPitch() < -90.1F)) {
			getAntiCheat().logCheat(this, e.getPlayer(), null, null);
		}
	}
}
