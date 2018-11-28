package me.rida.anticheat.checks.combat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.packets.PacketPlayerType;
import me.rida.anticheat.packets.events.PacketAttackEvent;
import me.rida.anticheat.utils.MathUtil;

public class KillAuraB extends Check {

    public KillAuraB(AntiCheat AntiCheat) {
        super("KillAuraB", "KillAura",  CheckType.Combat, true, true, false, 10, 1, 600000L, AntiCheat);
    }

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	private void onAttack(PacketAttackEvent e) {
        Player p = e.getPlayer();
        DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
        if(e.getType() != PacketPlayerType.USE
        		|| (data == null)) {
        	return;
        }


        int verboseA = data.getKillauraAVerbose();
        long time = data.getLastAimTime();

        if(MathUtil.elapsed(time, 1100L)) {
            time = System.currentTimeMillis();
            verboseA = 0;
        }

        if ((Math.abs(data.getLastKillauraPitch() - e.getPlayer().getEyeLocation().getPitch()) > 1
                || angleDistance((float) data.getLastKillauraYaw(), p.getEyeLocation().getYaw()) > 1
                || Double.compare(p.getEyeLocation().getYaw(), data.getLastKillauraYaw()) != 0)
                && !MathUtil.elapsed(data.getLastPacket(), 100L)) {

            if(angleDistance((float) data.getLastKillauraYaw(), p.getEyeLocation().getYaw()) != data.getLastKillauraYawDif()) {
                if(++verboseA > 9) {
                	if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
                            || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
                		return;
                	}
                	getAntiCheat().logCheat(this, p, null, "(Type: B)");
                }
            }
            data.setLastKillauraYawDif(angleDistance((float) data.getLastKillauraYaw(), p.getEyeLocation().getYaw()));
        } else {
            verboseA = 0;
        }

        data.setKillauraAVerbose(verboseA);
        data.setLastAimTime(time);
    }

	private static float angleDistance(float alpha, float beta) {
        float phi = Math.abs(beta - alpha) % 360;
        return phi > 180 ? 360 - phi : phi;
    }
}
