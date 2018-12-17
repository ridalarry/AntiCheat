package me.rida.anticheat.checks.combat;

import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.packets.PacketPlayerType;
import me.rida.anticheat.packets.events.PacketAttackEvent;
import me.rida.anticheat.utils.MathUtil;

public class ReachA extends Check {

	public ReachA(AntiCheat AntiCheat) {
		super("ReachA", "Reach",  CheckType.Combat, true, true, false, true, false, 7, 1, 30000L, AntiCheat);
	}

	@SuppressWarnings("unused")
	private int getKB(Player p){
		int enchantmentLevel = 0;
		ItemStack[] inv = p.getInventory().getContents();
		for(ItemStack item:inv){
			if (item != null)
				if(item.getType() != null){
					if(item.getEnchantments().containsKey(Enchantment.KNOCKBACK)){
						return enchantmentLevel = item.getEnchantmentLevel(Enchantment.KNOCKBACK);
					}
				}
		}
		return 0;
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	private void onAttack(PacketAttackEvent e) {
		Entity p2 = e.getEntity();
		Player p = e.getPlayer();
		if(e.getType() != PacketPlayerType.USE
				|| e.getEntity() == null 
				|| p2 instanceof Enderman 
				|| p2.isDead()
				|| p.getGameMode().equals(GameMode.CREATIVE)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()){
			return;
		}

		double distance = MathUtil.getHorizontalDistance(p.getLocation(), p2.getLocation()) - 0.35;
		double maxReach = 4.2;
		double yawDifference = 180 - Math.abs(Math.abs(p.getEyeLocation().getYaw()) - Math.abs(p2.getLocation().getYaw()));
		double KB = getKB(p);
		maxReach+= Math.abs(p.getVelocity().length() + p2.getVelocity().length()) * 0.4;
		maxReach+= yawDifference * 0.01;
		maxReach+= getAntiCheat().getLag().getPing(p) * 0.00097; 

		if(maxReach < 4.2) {
			maxReach = 4.2;
		}
		if(KB > 0) {
			maxReach += KB;
		}
		if (p2 instanceof Slime || p2 instanceof Spider) {
			maxReach += 1.0;
		}
		if (p2 instanceof Giant) {
			maxReach += 2.0;
		}
		String en = p2.getName().toString();
		if(distance > maxReach) {
			getAntiCheat().logCheat(this, p, MathUtil.trim(3, distance) + " > " + MathUtil.trim(3, maxReach) + "; KB: " + KB + "; Attacked: " + en, "(Type: A)");
		}
	}
}