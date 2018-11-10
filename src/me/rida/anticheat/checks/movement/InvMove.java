package me.rida.anticheat.checks.movement;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;

public class InvMove extends Check {
	public InvMove(AntiCheat AntiCheat) {
		super("InvMoveA", "InvMove", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setViolationResetTime(3000);
		setBannable(false);
	}


	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void move(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		InventoryView view = p.getOpenInventory();
		Inventory top = view.getTopInventory();
		Inventory bottom = view.getBottomInventory();
		if (view !=null) {
			if (top.toString().contains("CraftInventoryCrafting")
					||getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
        			|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
				return;
			} else {
				getAntiCheat().logCheat(this, p, "Moving while having a gui open!", "(Type: A)");
			}
		}
	}
}