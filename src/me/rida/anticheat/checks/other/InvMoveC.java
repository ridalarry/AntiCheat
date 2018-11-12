package me.rida.anticheat.checks.other;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;


import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;

public class InvMoveC extends Check {
    public InvMoveC(AntiCheat AntiCheat) {
        super("InvMoveC", "InvMove", CheckType.Other, AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
		setViolationsToNotify(1);
    }

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void InventoryClickEvent(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
			if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
			|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
		return;
	} else {
		if (p.isSprinting()) {
		getAntiCheat().logCheat(this, p, "Sprinting while having a gui open!", "(Type: C)");
	}
		if (p.isSneaking()) {
		getAntiCheat().logCheat(this, p, "Sneaking while having a gui open!", "(Type: C)");
		}
	}
	}

}
