package me.rida.anticheat.checks.combat;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.packets.PacketPlayerType;
import me.rida.anticheat.packets.events.PacketAttackEvent;
import me.rida.anticheat.utils.UtilsA;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class KillAuraG extends Check {

    public KillAuraG(AntiCheat AntiCheat) {
        super("KillAuraG", "KillAura", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(true);
    }

    @EventHandler
    public void onAttack(PacketAttackEvent e) {
        if(e.getType() != PacketPlayerType.USE) {
            return;
        }

        Player player = e.getPlayer();
        DataPlayer data = AntiCheat.getInstance().getDataManager().getData(player);

        if(data == null) {
            return;
        }

        int verboseA = data.getKillauraAVerbose();
        long time = data.getLastAimTime();

        if(UtilsA.elapsed(time, 1100L)) {
            time = System.currentTimeMillis();
            verboseA = 0;
        }

        if ((Math.abs(data.getLastKillauraPitch() - e.getPlayer().getEyeLocation().getPitch()) > 1
                || angleDistance((float) data.getLastKillauraYaw(), player.getEyeLocation().getYaw()) > 1
                || Double.compare(player.getEyeLocation().getYaw(), data.getLastKillauraYaw()) != 0)
                && !UtilsA.elapsed(data.getLastPacket(), 100L)) {

            if(angleDistance((float) data.getLastKillauraYaw(), player.getEyeLocation().getYaw()) != data.getLastKillauraYawDif()) {
                if(++verboseA > 9) {
                	getAntiCheat().logCheat(this, player, null, "(Type: G)");
                }
            }
            data.setLastKillauraYawDif(angleDistance((float) data.getLastKillauraYaw(), player.getEyeLocation().getYaw()));
        } else {
            verboseA = 0;
        }

        data.setKillauraAVerbose(verboseA);
        data.setLastAimTime(time);
    }

    public static float angleDistance(float alpha, float beta) {
        float phi = Math.abs(beta - alpha) % 360;
        return phi > 180 ? 360 - phi : phi;
    }
}
