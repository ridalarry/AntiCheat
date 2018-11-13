package me.rida.anticheat.checks.combat;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.CheckType;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class FastBowA extends Check {
	public static Map<Player, Long> bowPull;
    public static Map<Player, Integer> count;
    public FastBowA(AntiCheat AntiCheat) {
        super("FastBowA", "FastBow", CheckType.Combat, AntiCheat);
        bowPull = new HashMap<>();
        count = new HashMap<>();
        setViolationsToNotify(2);
        setMaxViolations(7);
        setEnabled(true);
        setBannable(true);
    }

    @EventHandler
    public void Interact(final PlayerInteractEvent e) {
        Player Player = e.getPlayer();
        if (Player.getItemInHand() != null && Player.getItemInHand().getType().equals(Material.BOW)) {
            bowPull.put(Player, System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent e) {
        bowPull.remove(e.getPlayer());

        count.remove(e.getPlayer());
    }

    @EventHandler
    public void onShoot(final ProjectileLaunchEvent e) {
        if (!this.isEnabled()) return;
        if (e.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getEntity();
            if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {
                Player player = (Player) arrow.getShooter();
                if (bowPull.containsKey(player)) {
                    Long time = System.currentTimeMillis() - bowPull.get(player);
                    double power = arrow.getVelocity().length();
                    Long timeLimit = 300L;
                    int Count = 0;
                    if (count.containsKey(player)) {
                        Count = count.get(player);
                    }
                    if (power > 2.5 && time < timeLimit) {
                        count.put(player, Count + 1);
                    } else {
                        count.put(player, Count > 0 ? Count - 1 : Count);
                    }
                    if (Count > 8) {
						getAntiCheat().logCheat(this, player, time + " ms", "(Type: A)");
                        count.remove(player);
                    }
                }
            }
        }
    }
}