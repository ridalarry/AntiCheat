package me.rida.anticheat.checks.combat;

import java.util.Collections;
import java.util.Optional;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.ExtraUtil;
import me.rida.anticheat.utils.MathUtil;

public class AimAssistB extends Check {
	
	public AimAssistB(AntiCheat AntiCheat) {
		super("AimAssistB", "AimAssist",  CheckType.Combat, true, false, false, false, 10, 1, 600000L, AntiCheat);
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(me.rida.anticheat.AntiCheat.getInstance(), PacketType.Play.Client.USE_ENTITY) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				Optional<Entity> entityOp = event.getPlayer().getWorld().getEntities().stream().filter(entity -> entity.getEntityId() == event.getPacket().getIntegers().read(0)).findFirst();

				if(entityOp.isPresent()) {
					Entity entity = entityOp.get();

					EnumWrappers.EntityUseAction action = event.getPacket().getEntityUseActions().read(0);

					if(action.equals(EnumWrappers.EntityUseAction.ATTACK) && entity instanceof LivingEntity) {
						DataPlayer data = me.rida.anticheat.AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());

						if(data != null) {
							data.lastAttack = System.currentTimeMillis();
							data.lastHitEntity = (LivingEntity) entity;
						}
					}
				}
			}
		});
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());
		Player p = event.getPlayer();
		if(data == null
				|| data.lastHitEntity == null
				|| (System.currentTimeMillis() - data.lastAttack) > 350L) return;

		float offset = ExtraUtil.yawTo180F((float) MathUtil.getOffsetFromEntity(event.getPlayer(), data.lastHitEntity)[0]);

		if(data.patterns.size() >= 10) {

			Collections.sort(data.patterns);

			float range = Math.abs(data.patterns.get(data.patterns.size() - 1) -  data.patterns.get(0));

			if(Math.abs(range - data.lastRange) < 4) {
				getAntiCheat().logCheat(this, p, Color.Red + "Experemental", "(Type: A)");
			}
			event.getPlayer().sendMessage("Range: " + range);

			data.lastRange = range;
			data.patterns.clear();
		} else {
			data.patterns.add(offset);
		}
	}
}