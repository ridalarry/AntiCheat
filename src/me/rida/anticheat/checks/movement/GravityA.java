package me.rida.anticheat.checks.movement;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.BlockUtils;
import me.rida.anticheat.utils.MathUtils;
import me.rida.anticheat.utils.PlayerUtils;
import me.rida.anticheat.utils.ServerUtils;
import me.rida.anticheat.utils.UtilNewVelocity;
import me.rida.anticheat.utils.UtilVelocity;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class GravityA extends Check {
    public GravityA(AntiCheat AntiCheat) {
		super("GravityA", "Gravity", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e, Player player) {
        Player p = e.getPlayer();
        DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
        if (data != null) {
            double diff = MathUtils.getVerticalDistance(e.getFrom(), e.getTo());
            double LastY = data.getLastY_Gravity();
            double MaxG = 7;
            if ((PlayerUtils.wasOnSlime(p)) || !player.getGameMode().equals(GameMode.CREATIVE)) {
                data.setGravity_VL(0);
                return;
            }
            if (e.getTo().getY() < e.getFrom().getY()) {
                return;
            }
            if (BlockUtils.isHalfBlock(p.getLocation().add(0, -1.50, 0).getBlock()) || BlockUtils.isNearHalfBlock(p) || BlockUtils.isStair(p.getLocation().add(0,1.50,0).getBlock()) || BlockUtils.isNearStiar(p) || !player.getGameMode().equals(GameMode.CREATIVE) || UtilNewVelocity.didTakeVel(p)
                    || PlayerUtils.wasOnSlime(p)) {
                data.setGravity_VL(0);
                return;
            }
            if (p.getLocation().getBlock().getType() != Material.CHEST &&
                    p.getLocation().getBlock().getType() != Material.TRAPPED_CHEST && p.getLocation().getBlock().getType() != Material.ENDER_CHEST && data.getAboveBlockTicks() == 0) {
                if (!PlayerUtils.onGround2(p) && !PlayerUtils.isOnGround3(p) && !PlayerUtils.isOnGround(p)) {
                    if ((((ServerUtils.isBukkitVerison("1_7") || ServerUtils.isBukkitVerison("1_8")) && Math.abs(p.getVelocity().getY() - LastY) > 0.000001)
                            || (!ServerUtils.isBukkitVerison("1_7") && !ServerUtils.isBukkitVerison("1_8") && Math.abs(p.getVelocity().getY() - diff) > 0.000001))
                            && !PlayerUtils.onGround2(p)
                            && e.getFrom().getY() < e.getTo().getY()
                            && (p.getVelocity().getY() >= 0 || p.getVelocity().getY() < (-0.0784 * 5)) && !UtilVelocity.didTakeVelocity(p) && p.getNoDamageTicks() == 0.0) {
                        if (data.getGravity_VL() >= MaxG) {
                            getAntiCheat().logCheat(this, p, "Player's motion was changed to an unexpected value.", null);
                        } else {
                            data.setGravity_VL(data.getGravity_VL() + 1);
                        }
                    } else {
                        data.setGravity_VL(0);
                    }
                }
            }
            data.setLastY_Gravity(diff);
        }
    }
}
