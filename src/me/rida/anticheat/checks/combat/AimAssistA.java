package me.rida.anticheat.checks.combat;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.MathUtils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Collections;
import java.util.Optional;

public class AimAssistA extends Check {
    public AimAssistA(AntiCheat AntiCheat) {
        super("AimAssistA", "AimAssest", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setViolationResetTime(3000);
		setBannable(false);
		setViolationsToNotify(5);

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(AntiCheat.getInstance(), PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Optional<Entity> entityOp = event.getPlayer().getWorld().getEntities().stream().filter(entity -> entity.getEntityId() == event.getPacket().getIntegers().read(0)).findFirst();

                if(entityOp.isPresent()) {
                    Entity entity = entityOp.get();

                    EnumWrappers.EntityUseAction action = event.getPacket().getEntityUseActions().read(0);

                    if(action.equals(EnumWrappers.EntityUseAction.ATTACK) && entity instanceof LivingEntity) {
                        DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());

                        if(data != null) {
                            data.lastAttack = System.currentTimeMillis();
                            data.lastHitEntity = (LivingEntity) entity;
                        }
                    }
                }
            }
        });
    }

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerMove(PlayerMoveEvent e) {
        DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(e.getPlayer());
        Player p = e.getPlayer();
        if(data == null
                || data.lastHitEntity == null
                || (System.currentTimeMillis() - data.lastAttack) > 150L) return;

        float offset = MathUtils.yawTo180F((float) MathUtils.getOffsetFromEntity(e.getPlayer(), data.lastHitEntity)[0]);

        if(data.patterns.size() >= 10) {

            Collections.sort(data.patterns);

            float range = Math.abs(data.patterns.get(data.patterns.size() - 1) -  data.patterns.get(0));

            if(Math.abs(range - data.lastRange) < 4) {
            	getAntiCheat().logCheat(this, p, Color.Red + "Experemental", "(Type: A)");
                
            }

            data.lastRange = range;
            data.patterns.clear();
        } else {
            data.patterns.add(offset);
        }


    }


}
