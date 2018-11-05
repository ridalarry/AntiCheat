package me.rida.anticheat.checks.clients;

import java.text.DecimalFormat;
import org.bukkit.entity.Player;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.AntiCheat;

public class Spook
extends Check {
    private float lastYaw;
    private int lastBad;
    private static Spook spooka;

    public Spook(AntiCheat AntiCheat) {
        super("SpookA", "Spook", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
		setViolationsToNotify(1);
    }

    float onAim(Player player, float f) {
        float f2 = Math.abs(f - this.lastYaw) % 180.0f;
        this.lastYaw = f;
        if (f2 > 1.0f && (float)Math.round(f2) == f2) {
            if (f2 == (float)this.lastBad) {
            	getAntiCheat().logCheat(this, player, Color.Red + "Experemental", null);
                return f2;
            }
            this.lastBad = Math.round(f2);
        } else {
            this.lastBad = 0;
        }
        return 0.0f;
    }

    static Spook SpookAInstance() {
        return spooka;
    }
}

