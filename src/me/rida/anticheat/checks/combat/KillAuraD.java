package me.rida.anticheat.checks.combat;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.packets.PacketPlayerType;
import me.rida.anticheat.packets.events.PacketKillauraEvent;

public class KillAuraD extends Check {

	public static Map<UUID, Map.Entry<Double, Double>> packetTicks;

	public KillAuraD(AntiCheat AntiCheat) {
		super("KillAuraD", "KillAura",  CheckType.Combat, true, false, false, false, true, 150, 1, 600000L, AntiCheat);
		packetTicks = new HashMap<>();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void packet(PacketKillauraEvent e) {
		Player p = e.getPlayer();
		if (!getAntiCheat().isEnabled()
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}

		double Count = 0;
		double Other = 0;
		if (packetTicks.containsKey(e.getPlayer().getUniqueId())) {
			Count = packetTicks.get(e.getPlayer().getUniqueId()).getKey();
			Other = packetTicks.get(e.getPlayer().getUniqueId()).getValue();
		}

		if (e.getType() == PacketPlayerType.ARM_SWING) {
			Other++;
		}

		if (e.getType() == PacketPlayerType.USE) {
			Count++;
		}

		if(Count > Other && Other >= 2) {
			if (Count > 3) {
				getAntiCheat().logCheat(this, p, "Packet" + " Count: " + Count + " Other: " + Other, "(Type: D)");
			}
		}
		if(Count > 3 || Other >= 3) {
			Count = 0;
			Other = 0;
		}
		packetTicks.put(e.getPlayer().getUniqueId(), new AbstractMap.SimpleEntry<>(Count, Other));
	}
}