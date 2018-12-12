package me.rida.anticheat.events;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerVelocityEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.client.PMEA;
import me.rida.anticheat.checks.combat.AntiKBA;
import me.rida.anticheat.checks.combat.AutoClickerA;
import me.rida.anticheat.checks.combat.AutoClickerB;
import me.rida.anticheat.checks.combat.AutoClickerC;
import me.rida.anticheat.checks.combat.AutoClickerD;
import me.rida.anticheat.checks.combat.CriticalsB;
import me.rida.anticheat.checks.combat.FastBowA;
import me.rida.anticheat.checks.combat.HitBoxA;
import me.rida.anticheat.checks.combat.HitBoxC;
import me.rida.anticheat.checks.combat.KillAuraA;
import me.rida.anticheat.checks.combat.KillAuraD;
import me.rida.anticheat.checks.combat.KillAuraF;
import me.rida.anticheat.checks.combat.KillAuraG;
import me.rida.anticheat.checks.combat.KillAuraH;
import me.rida.anticheat.checks.combat.KillAuraK;
import me.rida.anticheat.checks.combat.ReachD;
import me.rida.anticheat.checks.combat.RegenA;
import me.rida.anticheat.checks.movement.AscensionA;
import me.rida.anticheat.checks.movement.AscensionC;
import me.rida.anticheat.checks.movement.FlyB;
import me.rida.anticheat.checks.movement.GlideA;
import me.rida.anticheat.checks.movement.JesusA;
import me.rida.anticheat.checks.movement.NoFallA;
import me.rida.anticheat.checks.movement.NoSlowdownA;
import me.rida.anticheat.checks.movement.SneakA;
import me.rida.anticheat.checks.movement.SpeedB;
import me.rida.anticheat.checks.movement.SpeedC;
import me.rida.anticheat.checks.other.ChangeA;
import me.rida.anticheat.checks.other.TimerA;
import me.rida.anticheat.checks.player.PacketsA;
import me.rida.anticheat.utils.ServerUtil;

@SuppressWarnings("static-access")
public class SharedEvents implements Listener {
	private static Map<Player, Long> lastSprintStart = new HashMap<Player, Long>();
	private static Map<Player, Long> lastSprintStop = new HashMap<Player, Long>();

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();

		PacketsA.blacklist.add(u);
			this.lastSprintStart.remove((Object)p);
			this.lastSprintStop.remove((Object)p);
		if (ServerUtil.isBukkitVerison("1_7")) {
			if (e.getPlayer().hasPermission("anticheat.staff")) {
				AntiCheat.AlertsOn.add(p);
			}
		}
		if(AntiCheat.isInPhaseTimer(p)) {
			AntiCheat.timerLeft.put(p.getName().toString(), 3);
		}else {
			AntiCheat.getInstance().startTimerPhaseCheck(p);
		}


		AntiCheat.getInstance().getDataManager().addPlayerData(e.getPlayer());
	}
	@EventHandler(priority = EventPriority.HIGH)
	private void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
			JesusA.onWater.remove(p);
			NoFallA.cancel.add(p);
			JesusA.placedBlockOnWater.remove(p);
			JesusA.count.remove(p);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onTeleport(PlayerTeleportEvent e) {
		Player p = e.getPlayer();
		if (e.getCause() == TeleportCause.ENDER_PEARL) {
			NoFallA.cancel.add(p);
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onVelocity(PlayerVelocityEvent e) {
		JesusA.velocity.put(e.getPlayer(), System.currentTimeMillis());
	}


	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void OnPlace(BlockPlaceEvent e) {
		if (e.getBlockReplacedState().getBlock().getType() == Material.WATER) {
			JesusA.placedBlockOnWater.add(e.getPlayer());
		}
	}
	@EventHandler
	public void PlayerChangedWorld(PlayerChangedWorldEvent e) {
		UUID u = e.getPlayer().getUniqueId();
		PacketsA.blacklist.add(u);
	}

	@EventHandler
	private void PlayerRespawn(PlayerRespawnEvent e) {
		UUID u = e.getPlayer().getUniqueId();
		PacketsA.blacklist.add(u);
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();

		PacketsA.packetTicks.remove(uuid);
		PacketsA.lastPacket.remove(uuid);
		PacketsA.blacklist.remove(uuid);
		TimerA.packets.remove(uuid);
		TimerA.verbose.remove(uuid);
		TimerA.lastPacket.remove(uuid);
		TimerA.toCancel.remove(p);
		SpeedC.lastHit.remove(uuid);
		SpeedC.tooFastTicks.remove(uuid);
		SpeedC.speedTicks.remove(uuid);
		SpeedC.velocity.remove(uuid);
		ChangeA.falling.remove(uuid);
		SneakA.sneakTicks.remove(uuid);
		SpeedB.speedTicks.remove(uuid);
		SpeedB.tooFastTicks.remove(uuid);
		SpeedB.lastHit.remove(uuid);
		NoSlowdownA.speedTicks.remove(uuid);
		PMEA.forgeMods.remove(uuid);
		NoFallA.FallDistance.remove(uuid);
		NoFallA.FallDistance.containsKey(uuid);
		NoFallA.cancel.remove(e.getPlayer());
		AntiCheat.getInstance().getDataManager().removePlayerData(p);
		AscensionA.AscensionTicks.remove(uuid);
		AscensionA.velocity.remove(uuid);
		FlyB.flyTicks.remove(uuid);
		GlideA.flyTicks.remove(uuid);
		JesusA.placedBlockOnWater.remove(p);
		AntiKBA.lastVelocity.remove((Object)p);
		AntiKBA.awaitingVelocity.remove((Object)p);
		AntiKBA.totalMoved.remove((Object)p);
		AutoClickerA.LastMS.remove(uuid);
		AutoClickerA.Clicks.remove(uuid);
		AutoClickerA.ClickTicks.remove(uuid);
		AutoClickerB.LastMS.remove(uuid);
		AutoClickerB.Clicks.remove(uuid);
		AutoClickerB.Clicks.remove(uuid);
		AutoClickerD.clicks.remove(uuid);
		AutoClickerD.recording.remove(uuid);
		HitBoxC.count.remove(uuid);
		HitBoxC.yawDif.remove(uuid);
		HitBoxC.lastHit.remove(uuid);
		AutoClickerC.packetHitsSinceLastCheck.remove(uuid);
		AutoClickerC.lastCheckedTick.remove(uuid);
		AutoClickerC.lastPacketTick.remove(uuid);
		AutoClickerC.lastTickWithPacketSent.remove(uuid);
		AutoClickerC.hitsSinceLastCheck.remove(uuid);
		AutoClickerC.cps.remove(uuid);
		AutoClickerC.cpsMS.remove(uuid);
		CriticalsB.CritTicks.remove(uuid);
		CriticalsB.CritTicks.remove(uuid);
		HitBoxA.count.remove(uuid);
		HitBoxA.yawDif.remove(uuid);
		HitBoxA.lastHit.remove(uuid);
		KillAuraA.counts.remove(p);
		KillAuraA.blockGlitched.remove(p);
		KillAuraD.packetTicks.remove(uuid);
		KillAuraF.AuraTicks.remove(uuid);
		KillAuraG.AimbotTicks.remove(uuid);
		KillAuraG.Differences.remove(uuid);
		KillAuraG.LastLocation.remove(uuid);
		KillAuraH.lastAttack.remove(p);
		KillAuraK.verbose.remove(uuid);
		KillAuraK.lastArmSwing.containsKey(uuid);
		ReachD.offsets.remove(e.getPlayer());
		ReachD.reachTicks.remove(e.getPlayer());
		ReachD.projectileHit.remove(e.getPlayer());
		FastBowA.bowPull.remove(p);
		FastBowA.count.remove(p);
		RegenA.LastHeal.remove(uuid);
		RegenA.FastHealTicks.remove(uuid);
		AscensionC.flyTicks.remove(uuid);
	}
	public static Map<Player, Long> getLastSprintStart() {
		return lastSprintStart;
	}
	public void setLastSprintStart(Map<Player, Long> lastSprintStart) {
		this.lastSprintStart = lastSprintStart;
	}
	public static Map<Player, Long> getLastSprintStop() {
		return lastSprintStop;
	}
	public void setLastSprintStop(Map<Player, Long> lastSprintStop) {
		this.lastSprintStop = lastSprintStop;
	}
}