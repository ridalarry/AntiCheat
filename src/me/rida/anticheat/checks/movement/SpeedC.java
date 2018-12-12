package me.rida.anticheat.checks.movement;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.CheatUtil;
import me.rida.anticheat.utils.MathUtil;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.TimeUtil;

public class SpeedC extends Check {

	public static Map<UUID, Map.Entry<Integer, Long>> speedTicks;
	public static Map<UUID, Map.Entry<Integer, Long>> tooFastTicks;
	public static Map<UUID, Long> lastHit;
	public static Map<UUID, Double> velocity;

	public SpeedC(AntiCheat AntiCheat) {
		super("SpeedC", "Speed", CheckType.Movement, true, true, false, true, 3, 1, 600000L, AntiCheat);
		speedTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
		tooFastTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
		lastHit = new HashMap<UUID, Long>();
		velocity = new HashMap<UUID, Double>();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onHit(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();

			lastHit.put(p.getUniqueId(), System.currentTimeMillis());
		}
	}

	private boolean isOnIce(Player p) {
		Location a = p.getLocation();
		a.setY(a.getY() - 1.0);
		if (a.getBlock().getType().equals((Object) Material.ICE)) {
			return true;
		}
		a.setY(a.getY() - 1.0);
		return a.getBlock().getType().equals((Object) Material.ICE);
	}

	@EventHandler
	private void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();
		if (((e.getFrom().getX() == e.getTo().getX()) && (e.getFrom().getY() == e.getTo().getY()) && (e.getFrom().getZ() == e.getFrom().getZ()))
				|| !getAntiCheat().isEnabled()
				|| p.getAllowFlight()
				|| p.getVehicle() != null
				|| p.getGameMode().equals(GameMode.CREATIVE)
				|| PlayerUtil.isNearIce(p)
				|| PlayerUtil.wasOnSlime(p)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| PlayerUtil.isNearSlime(p)
				|| p.getVelocity().length() + 0.1 < velocity.getOrDefault(p.getUniqueId(), -1.0D)
				|| (getAntiCheat().LastVelocity.containsKey(p.getUniqueId())
						&& !p.hasPotionEffect(PotionEffectType.POISON)
						&& !p.hasPotionEffect(PotionEffectType.WITHER) && p.getFireTicks() == 0)) {
			return;
		}

		long lastHitDiff = lastHit.containsKey(p.getUniqueId())
				? lastHit.get(p.getUniqueId()) - System.currentTimeMillis()
						: 2001L;

				int Count = 0;
				long Time = TimeUtil.nowlong();
				if (speedTicks.containsKey(u)) {
					Count = speedTicks.get(u).getKey().intValue();
					Time = speedTicks.get(u).getValue().longValue();
				}
				int TooFastCount = 0;
				double percent = 0D;
				if (tooFastTicks.containsKey(u)) {
					double OffsetXZ = MathUtil.offset(MathUtil.getHorizontalVector(e.getFrom().toVector()),
							MathUtil.getHorizontalVector(e.getTo().toVector()));
					double LimitXZ = 0.0D;
					if ((PlayerUtil.isInGround(p)) && (p.getVehicle() == null)) {
						LimitXZ = 0.34D;
					} else {
						LimitXZ = 0.39D;
					}
					if (lastHitDiff < 800L) {
						++LimitXZ;
					} else if (lastHitDiff < 1600L) {
						LimitXZ += 0.4;
					} else if (lastHitDiff < 2000L) {
						LimitXZ += 0.1;
					}
					if (CheatUtil.slabsNear(p.getLocation())) {
						LimitXZ += 0.05D;
					}
					Location b = PlayerUtil.getEyeLocation(p);
					b.add(0.0D, 1.0D, 0.0D);
					if ((b.getBlock().getType() != Material.AIR) && (!CheatUtil.canStandWithin(b.getBlock()))) {
						LimitXZ = 0.69D;
					}
					Location below = p.getLocation().clone().add(0.0D, -1.0D, 0.0D);

					if (CheatUtil.isStair(below.getBlock())) {
						LimitXZ += 0.6;
					}

					if (isOnIce(p)) {
						if ((b.getBlock().getType() != Material.AIR) && (!CheatUtil.canStandWithin(b.getBlock()))) {
							LimitXZ = 1.0D;
						} else {
							LimitXZ = 0.75D;
						}
					}
					float speed = p.getWalkSpeed();
					LimitXZ += (speed > 0.2F ? speed * 10.0F * 0.33F : 0.0F);
					for (PotionEffect effect : p.getActivePotionEffects()) {
						if (effect.getType().equals(PotionEffectType.SPEED)) {
							if (p.isOnGround()) {
								LimitXZ += 0.061D * (effect.getAmplifier() + 1);
							} else {
								LimitXZ += 0.031D * (effect.getAmplifier() + 1);
							}
						}
					}
					if (OffsetXZ > LimitXZ && !TimeUtil.elapsed(tooFastTicks.get(p.getUniqueId()).getValue().longValue(), 150L)) {
						percent = (OffsetXZ - LimitXZ) * 100;
						TooFastCount = tooFastTicks.get(p.getUniqueId()).getKey().intValue()
								+ 3;
					} else {
						TooFastCount = TooFastCount > -150 ? TooFastCount-- : -150;
					}
				}
				if (TooFastCount >= 11) {
					TooFastCount = 0;
					Count++;
					dumplog(p, "Logged for Speed Type C; New Count: " + Count);
				}
				if (speedTicks.containsKey(p.getUniqueId()) && TimeUtil.elapsed(Time, 30000L)) {
					Count = 0;
					Time = TimeUtil.nowlong();
				}
				if (Count >= 3) {
					dumplog(p, "Logged for Speed Type C; Count: " + Count);
					Count = 0;
					getAntiCheat().logCheat(this, p, Math.round(percent) + "% faster than normal", "(Type: C)");
				}
				if (!p.isOnGround()) {
					velocity.put(u, p.getVelocity().length());
				} else {
					velocity.put(u, -1.0D);
				}
				tooFastTicks.put(p.getUniqueId(),
						new AbstractMap.SimpleEntry<Integer, Long>(TooFastCount, System.currentTimeMillis()));
				speedTicks.put(p.getUniqueId(),
						new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
	}
}