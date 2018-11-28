package me.rida.anticheat.checks.other;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;

public class InvMoveA extends Check {
	public InvMoveA(AntiCheat AntiCheat) {
		super("InvMoveA", "InvMove", CheckType.Other, AntiCheat);
		setEnabled(true);
		setMaxViolations(20);
		setViolationResetTime(3000);
		setBannable(false);
		setViolationsToNotify(20);
	}


	@SuppressWarnings("unused")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void move(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		InventoryView view = p.getOpenInventory();
		Inventory top = view.getTopInventory();
		Inventory bottom = view.getBottomInventory();
		if (view !=null) {
			if (top.toString().contains("CraftInventoryCrafting")
					|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
        			|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
				return;
			} else {
				getAntiCheat().logCheat(this, p, "Moving while having a gui open!", "(Type: A)");
			}
		}
	}
}