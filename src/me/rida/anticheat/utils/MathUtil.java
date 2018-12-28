package me.rida.anticheat.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Base64;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class MathUtil {

	public static int floor(double var0) {
		final int var2 = (int) var0;
		return var0 < var2 ? var2 - 1 : var2;
	}

	public static long elapsed(long starttime) {
		return System.currentTimeMillis() - starttime;
	}
	public static boolean isInteger(String s) {
		return isInteger(s,10);
	}
	private static double xDiff;
	private static double yDiff;
	private static double zDiff;

	public static void Distance(final Location one, final Location two) {
		xDiff = (Math.abs(one.getX() - two.getX()));
		yDiff = (Math.abs(one.getY() - two.getY()));
		zDiff = (Math.abs(one.getZ() - two.getZ()));
	}
	public static boolean isInteger(String s, int radix) {
		if(s.isEmpty()) {
			return false;
		}
		for(int i = 0; i < s.length(); i++) {
			if(i == 0 && s.charAt(i) == '-') {
				if(s.length() == 1) {
					return false;
				} else {
					continue;
				}
			}
			if(Character.digit(s.charAt(i),radix) < 0) {
				return false;
			}
		}
		return true;
	}

	public static boolean elapsed(long from, long required) {
		return System.currentTimeMillis() - from > required;
	}

	public static float yawTo180F(float flub) {
		if ((flub %= 360.0f) >= 180.0f) {
			flub -= 360.0f;
		}
		if (flub < -180.0f) {
			flub += 360.0f;
		}
		return flub;
	}

	public static float[] getRotations(Location one, Location two) {
		final double diffX = two.getX() - one.getX();
		final double diffZ = two.getZ() - one.getZ();
		final double diffY = two.getY() + 2.0 - 0.4 - (one.getY() + 2.0);
		final double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
		final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
		final float pitch = (float) (-Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
		return new float[]{yaw, pitch};
	}

	public static double[] getOffsetFromEntity(Player player, LivingEntity entity) {
		final double yawOffset = Math.abs(yawTo180F(player.getEyeLocation().getYaw()) - yawTo180F(getRotations(player.getLocation(), entity.getLocation())[0]));
		final double pitchOffset = Math.abs(Math.abs(player.getEyeLocation().getPitch()) - Math.abs(getRotations(player.getLocation(), entity.getLocation())[1]));
		return new double[]{yawOffset, pitchOffset};
	}

	@SuppressWarnings("unused")
	public static boolean close(Double[] arrdouble, int n) {
		boolean bl;
		final double d = arrdouble[4];
		final double d2 = arrdouble[3];
		final double d3 = arrdouble[2];
		final double d4 = arrdouble[1];
		final double d5 = arrdouble[0];
		final boolean bl2 = (d >= d2 ? d - d2 : d2 - d) <= n;
		final boolean bl3 = (d >= d3 ? d - d3 : d3 - d) <= n;
		final boolean bl4 = (d >= d4 ? d - d4 : d4 - d) <= n;
		final boolean bl5 = bl = (d >= d5 ? d - d5 : d5 - d) <= n;
		if (bl2 && bl3 && bl4 && bl) {
			return true;
		}
		return false;
	}

	public static Vector getRotation(Location location, Location location2) {
		final double d = location2.getX() - location.getX();
		final double d2 = location2.getY() - location.getY();
		final double d3 = location2.getZ() - location.getZ();
		final double d4 = Math.sqrt(d * d + d3 * d3);
		final float f = (float)(Math.atan2(d3, d) * 180.0 / 3.141592653589793) - 90.0f;
		final float f2 = (float)(- Math.atan2(d2, d4) * 180.0 / 3.141592653589793);
		return new Vector(f, f2, 0.0f);
	}

	public static double clamp180(double d) {
		if ((d %= 360.0) >= 180.0) {
			d -= 360.0;
		}
		if (d < -180.0) {
			d += 360.0;
		}
		return d;
	}

	public static double getHorizontalDistance(Location to, Location from) {
		final double x = Math.abs(Math.abs(to.getX()) - Math.abs(from.getX()));
		final double z = Math.abs(Math.abs(to.getZ()) - Math.abs(from.getZ()));

		return Math.sqrt(x * x + z * z);
	}

	public static double getVerticalDistance(Location to, Location from) {
		final double y = Math.abs(Math.abs(to.getY()) - Math.abs(from.getY()));

		return Math.sqrt(y * y);
	}
	public static double getVerticalDistance3(Location location, Location location2) {
		final double d = (location2.getY() - location.getY()) * (location2.getY() - location.getY());
		final double d2 = Math.sqrt(d);
		final double d3 = Math.abs(d2);
		return d3;
	}

	public static double getHorizontalDistance3(Location location, Location location2) {
		double d = 0.0;
		final double d2 = (location2.getX() - location.getX()) * (location2.getX() - location.getX());
		final double d3 = (location2.getZ() - location.getZ()) * (location2.getZ() - location.getZ());
		final double d4 = Math.sqrt(d2 + d3);
		d = Math.abs(d4);
		return d;
	}

	public static double fix180(double d) {
		if ((d %= 360.0) >= 180.0) {
			d -= 360.0;
		}
		if (d < -180.0) {
			d += 360.0;
		}
		return d;
	}

	public static double getDistance3D(Location location, Location location2) {
		final double d = (location2.getX() - location.getX()) * (location2.getX() - location.getX());
		final double d2 = (location2.getY() - location.getY()) * (location2.getY() - location.getY());
		final double d3 = (location2.getZ() - location.getZ()) * (location2.getZ() - location.getZ());
		final double d4 = Math.sqrt(d + d2 + d3);
		final double d5 = Math.abs(d4);
		return d5;
	}

	@SuppressWarnings("unused")
	public static float getOffset(Player player, LivingEntity livingEntity) {
		double d = 0.0;
		final Location location = player.getLocation().add(0.0, player.getEyeHeight(), 0.0);
		final Location location2 = player.getLocation().add(0.0, player.getEyeHeight(), 0.0);
		final Vector vector = new Vector(location2.getYaw(), location2.getPitch(), 0.0f);
		final Vector vector2 = getRotation(location2, location);
		final double d2 = clamp180(vector.getX() - vector2.getX());
		final double d3 = clamp180(vector.getY() - vector2.getY());
		final double d4 = getHorizontalDistance3(location2, location);
		final double d5 = getDistance3D(location2, location);
		final double d6 = d2 * d4 * d5;
		final double d7 = d3 * Math.abs(location.getY() - location2.getY()) * d5;
		d += Math.abs(d6);
		d += Math.abs(d7);
		return 0.0f;
	}
	public static double getHorizontalDistance2(Location location, Location location2) {
		final double d = (location2.getX() - location.getX()) * (location2.getX() - location.getX());
		final double d2 = (location2.getZ() - location.getZ()) * (location2.getZ() - location.getZ());
		final double d3 = Math.sqrt(d + d2);
		final double d4 = Math.abs(d3);
		return d4;
	}


	public static double getDistance3D2(Location location, Location location2) {
		final double d = (location2.getX() - location.getX()) * (location2.getX() - location.getX());
		final double d2 = (location2.getY() - location.getY()) * (location2.getY() - location.getY());
		final double d3 = (location2.getZ() - location.getZ()) * (location2.getZ() - location.getZ());
		final double d4 = Math.sqrt(d + d2 + d3);
		final double d5 = Math.abs(d4);
		return d5;
	}

	public static double[] getOffsets2(Player player, LivingEntity livingEntity) {
		final Location location = livingEntity.getLocation().add(0.0, livingEntity.getEyeHeight(), 0.0);
		final Location location2 = player.getLocation().add(0.0, player.getEyeHeight(), 0.0);
		final Vector vector = new Vector(location2.getYaw(), location2.getPitch(), 0.0f);
		final Vector vector2 = getRotation(location2, location);
		final double d = fix180(vector.getX() - vector2.getX());
		final double d2 = fix180(vector.getY() - vector2.getY());
		final double d3 = getHorizontalDistance2(location2, location);
		final double d4 = getDistance3D2(location2, location);
		final double d5 = d * d3 * d4;
		final double d6 = d2 * Math.abs(Math.sqrt(location.getY() - location2.getY())) * d4;
		return new double[]{Math.abs(d5), Math.abs(d6)};
	}

	public static double round(double value, int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static double getFraction(final double value) {
		return value % 1.0;
	}

	public static double trim(int degree, double d) {
		String format = "#.#";
		for (int i = 1; i < degree; ++i) {
			format = String.valueOf(format) + "#";
		}
		final DecimalFormat twoDForm = new DecimalFormat(format);
		return Double.valueOf(twoDForm.format(d).replaceAll(",", "."));
	}

	public static String decrypt(String strEncrypted) {
		String strData = "";

		try {
			final byte[] decoded = Base64.getDecoder().decode(strEncrypted);
			strData = (new String(decoded, StandardCharsets.UTF_8) + "\n");

		} catch (final Exception e) {
			e.printStackTrace();
		}
		return strData;
	}

	public static double offset(final Vector a, final Vector b) {
		return a.subtract(b).length();
	}

	public static Vector getHorizontalVector(final Vector v) {
		v.setY(0);
		return v;
	}

	public static Vector getVerticalVector(final Vector v) {
		v.setX(0);
		v.setZ(0);
		return v;
	}

	public static double getxDiff() {
		return xDiff;
	}

	public static double getyDiff() {
		return yDiff;
	}

	public static double getzDiff() {
		return zDiff;
	}

}