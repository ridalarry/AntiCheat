package me.rida.anticheat.checks.client;

import org.bukkit.entity.Player;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.Color;

public class SpookA extends Check {
	private float lastYaw;
	private int lastBad;
	private static SpookA spooka;

	public SpookA(AntiCheat AntiCheat) {
		super("SpookA", "Spook", CheckType.Client, true, false, false, false, 10, 1, 600000L, AntiCheat);
		setEnabled(true);
		setBannable(false);
		setJudgementDay(false);
		
		setAutobanTimer(false);
		
		setMaxViolations(10);
		setViolationsToNotify(1);
		setViolationResetTime(600000L);
	}

	public float onAim(Player p, float f) {
		float f2 = Math.abs(f - this.lastYaw) % 180.0f;
		this.lastYaw = f;
		if (f2 > 1.0f && (float)Math.round(f2) == f2) {
			if (f2 == (float)this.lastBad) {
				getAntiCheat().logCheat(this, p, Color.Red + "Experemental detection of a hack client!", "(Type: A)");
				return f2;
			}
			this.lastBad = Math.round(f2);
		} else {
			this.lastBad = 0;
		}
		return 0.0f;
	}

	public static SpookA SpookAInstance() {
		return spooka;
	}
}