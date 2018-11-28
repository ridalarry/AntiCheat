package me.rida.anticheat.checks.other;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;

public class InvMoveB extends Check {
	public InvMoveB(AntiCheat AntiCheat) {
	super("InvMoveB", "InvMove", CheckType.Other, AntiCheat);
	setEnabled(true);
	setMaxViolations(10);
	setBannable(false);
	}

	@SuppressWarnings("unused")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void attack(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getDamager();
		InventoryView view = p.getOpenInventory();
		Inventory top = view.getTopInventory();
		Inventory bottom = view.getBottomInventory();
		if (view !=null) {
			if (top.toString().contains("CraftInventoryCrafting")
					|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
					|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
				return;
			} else {
				getAntiCheat().logCheat(this, p, "Attacking while having a gui open!", "(Type: B)");
			}
		}
	}
}