package me.rida.anticheat.checks.combat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.packets.PacketPlayerType;
import me.rida.anticheat.packets.events.PacketPlayerEvent;
import org.bukkit.entity.Player;

public class TwitchA extends Check {
	public TwitchA(AntiCheat AntiCheat) {
		super("TwitchA", "Twitch",  CheckType.Combat, AntiCheat);

		setEnabled(true);
		setBannable(true);

		setMaxViolations(5);
	}

    @EventHandler(priority=EventPriority.MONITOR)
	public void Player(PacketPlayerEvent e) {
		Player p = e.getPlayer();
		if (e.getType() != PacketPlayerType.LOOK) {
			return;
		}
		if ((e.getPitch() > 90.1F) || (e.getPitch() < -90.1F)) {
			if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
                || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
				return;
			}
			getAntiCheat().logCheat(this, p, null, "(Type: A)");
		}
	}
}
