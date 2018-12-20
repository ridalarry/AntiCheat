package me.rida.anticheat.checks.movement;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.TimerUtil;

public class ImpossibleMovementsA extends Check {
	public ImpossibleMovementsA(AntiCheat AntiCheat) {
		super("ImpossibleMovementsA", "ImpMove", CheckType.Movement, true, false, false, false, true, 20, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Location from  =e.getFrom();
		Location to = e.getTo();
		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| p.getGameMode().equals(GameMode.CREATIVE)
				|| DataPlayer.getWasFlying() > 0
				|| p.getAllowFlight()) {
			return;
		}
		DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
		if (data != null) {
			if (p.getLocation().add(0,-0.30,0).getBlock().getType() == Material.CACTUS && p.getLocation().getBlock().getType() == Material.AIR) {
				if (data.getAntiCactus_VL() >= 3) {
					getAntiCheat().logCheat(this, p, "Impossible Movements: (Anti Cactus)", "(Type: A)");
				} else {
					data.setAntiCactus_VL(data.getAntiCactus_VL()+1);
				}
			} else {
				data.setAntiCactus_VL(0);
			}
			if (!data.isWebFloatMS_Set() && p.getLocation().add(0,-0.50,0).getBlock().getType() == Material.COBWEB) {
				data.setWebFloatMS_Set(true);
				data.setWebFloatMS(TimerUtil.nowlong());
			} else if (data.isWebFloatMS_Set()) {
				if (e.getTo().getY() == e.getFrom().getY()) {
					double x = Math.floor(from.getX());
					double z = Math.floor(from.getZ());
					if(Math.floor(to.getX())!=x||Math.floor(to.getZ())!=z) {
						if (data.getWebFloat_BlockCount() > 0) {
							if (p.getLocation().add(0,-0.50,0).getBlock().getType() != Material.COBWEB) {
								data.setWebFloatMS_Set(false);
								data.setWebFloat_BlockCount(0);
							}
							getAntiCheat().logCheat(this, p, "Impossible Movements: (Web Float)", "(Type: A)");
						} else {
							data.setWebFloat_BlockCount(data.getWebFloat_BlockCount()+1);
						}
					}
				} else {
					data.setWebFloatMS_Set(false);
					data.setWebFloat_BlockCount(0);
				}
			}
		}
	}
}