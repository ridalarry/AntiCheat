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
import org.bukkit.event.player.PlayerQuitEvent;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.MathUtil;

public class AutoClickerC
extends Check {
    private static final Map<UUID, Long> cpsMS = new WeakHashMap<UUID, Long>();
    private static final Map<UUID, Double[]> cps = new WeakHashMap<UUID, Double[]>();
    private static final Map<UUID, Long> lastTickWithPacketSent = new WeakHashMap<UUID, Long>();
    private static final Map<UUID, Boolean> lastPacketTick = new WeakHashMap<UUID, Boolean>();
    private static final Map<UUID, Long> packetHitsSinceLastCheck = new WeakHashMap<UUID, Long>();
    private static final Map<UUID, Long> lastCheckedTick = new WeakHashMap<UUID, Long>();
    private static final Map<UUID, Long> hitsSinceLastCheck = new WeakHashMap<UUID, Long>();

    public AutoClickerC(AntiCheat AntiCheat) {
        super("AutoClickerC", "AutoClicker", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
		setViolationsToNotify(1);
    }

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onInt(PlayerInteractEvent e) {
        Double[] arrdouble;
        Player p = e.getPlayer();
        if (e.getAction() != Action.LEFT_CLICK_AIR) {
            return;
        }
        if (cps.containsKey(p.getUniqueId())) {
            arrdouble = cps.get(p.getUniqueId());
            arrdouble[4] = arrdouble[4] + 1.0;
        } else {
            arrdouble = new Double[]{0.0, 0.0, 0.0, 0.0, 0.0};
        }
        cps.put(p.getUniqueId(), arrdouble);
        for (Map.Entry<UUID, Double[]> entry : cps.entrySet()) {
            Double[] arrdouble2 = entry.getValue();
            this.analyzeDouble(p);
            if (Arrays.stream(arrdouble2).anyMatch(d -> d >= 10.0) && arrdouble2[4].equals(arrdouble2[0]) && arrdouble2[4].equals(arrdouble2[1]) && arrdouble2[4].equals(arrdouble2[2]) && arrdouble2[4].equals(arrdouble2[3])) {
            	getAntiCheat().logCheat(this, p, Color.Red + "Experemental", "(Type: C)");
            }
            if (Arrays.stream(arrdouble2).anyMatch(d -> d > 12.0) && MathUtil.close(arrdouble2, 2)) {
            	getAntiCheat().logCheat(this, p, Color.Red + "Experemental", "(Type: C)");
            }
            arrdouble2[0] = arrdouble2[1];
            arrdouble2[1] = arrdouble2[2];
            arrdouble2[2] = arrdouble2[3];
            arrdouble2[3] = arrdouble2[4];
            arrdouble2[4] = 0.0;
            cps.put(p.getUniqueId(), arrdouble2);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        Player p = (Player)e.getDamager();
    }

    public void analyzeDouble(Player p) {
        UUID uUID = p.getUniqueId();
        if (cpsMS.containsKey(uUID) && System.currentTimeMillis() - cpsMS.get(p.getUniqueId()) <= 1) {
        	getAntiCheat().logCheat(this, p, Color.Red + "Experemental", "(Type: A)");
        }
        cpsMS.put(uUID, System.currentTimeMillis());
    }

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onDisconnect(PlayerQuitEvent playerQuitEvent) {
        UUID uUID = playerQuitEvent.getPlayer().getUniqueId();
        packetHitsSinceLastCheck.remove(uUID);
        lastCheckedTick.remove(uUID);
        lastPacketTick.remove(uUID);
        lastTickWithPacketSent.remove(uUID);
        hitsSinceLastCheck.remove(uUID);
        cps.remove(uUID);
        cpsMS.remove(uUID);
    }
}

