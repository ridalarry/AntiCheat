package me.rida.anticheat.checks.combat;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.MathUtil;

public class AutoClickerC extends Check {
	public static final Map<UUID, Long> cpsMS = new WeakHashMap<>();
	public static final Map<UUID, Double[]> cps = new WeakHashMap<>();
	public static final Map<UUID, Long> lastTickWithPacketSent = new WeakHashMap<>();
	public static final Map<UUID, Boolean> lastPacketTick = new WeakHashMap<>();
	public static final Map<UUID, Long> packetHitsSinceLastCheck = new WeakHashMap<>();
	public static final Map<UUID, Long> lastCheckedTick = new WeakHashMap<>();
	public static final Map<UUID, Long> hitsSinceLastCheck = new WeakHashMap<>();
	public AutoClickerC(AntiCheat AntiCheat) {
		super("AutoClickerC", "AutoClicker",  CheckType.Combat, true, false, false, false, true, 20, 1, 600000L, AntiCheat);
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onInt(PlayerInteractEvent e) {
		Double[] arrdouble;
		final Player p = e.getPlayer();
		if (p == null) {
			return;
		}
		if (e.getAction() != Action.LEFT_CLICK_AIR
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}
		if (cps.containsKey(p.getUniqueId())) {
			arrdouble = cps.get(p.getUniqueId());
			arrdouble[4] = arrdouble[4] + 1.0;
		} else {
			arrdouble = new Double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		}
		cps.put(p.getUniqueId(), arrdouble);
		for (final Map.Entry<UUID, Double[]> entry : cps.entrySet()) {
			final Double[] arrdouble2 = entry.getValue();
			this.analyzeDouble(p);
			if (Arrays.stream(arrdouble2).anyMatch(d -> d >= 10.0) && arrdouble2[4].equals(arrdouble2[0]) && arrdouble2[4].equals(arrdouble2[1]) && arrdouble2[4].equals(arrdouble2[2]) && arrdouble2[4].equals(arrdouble2[3])) {
				getAntiCheat().logCheat(this, p, "[1]", "(Type: C)");
			}
			if (Arrays.stream(arrdouble2).anyMatch(d -> d > 12.0) && MathUtil.close(arrdouble2, 2)) {
				getAntiCheat().logCheat(this, p, "[2]", "(Type: C)");
			}
			arrdouble2[0] = arrdouble2[1];
			arrdouble2[1] = arrdouble2[2];
			arrdouble2[2] = arrdouble2[3];
			arrdouble2[3] = arrdouble2[4];
			arrdouble2[4] = 0.0;
			cps.put(p.getUniqueId(), arrdouble2);
		}
	}
	@SuppressWarnings("unused")
	@EventHandler(priority=EventPriority.HIGH)
	public void onAttack(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		final Player p = (Player)e.getDamager();
	}
	public void analyzeDouble(Player p) {
		final UUID uUID = p.getUniqueId();
		if (cpsMS.containsKey(uUID) && System.currentTimeMillis() - cpsMS.get(p.getUniqueId()) <= 1) {
			getAntiCheat().logCheat(this, p, "[3]", "(Type: C)");
		}
		cpsMS.put(uUID, System.currentTimeMillis());
	}
}