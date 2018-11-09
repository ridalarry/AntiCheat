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
        double diffX = two.getX() - one.getX();
        double diffZ = two.getZ() - one.getZ();
        double diffY = two.getY() + 2.0 - 0.4 - (one.getY() + 2.0);
        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float) (-Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
        return new float[]{yaw, pitch};
    }

    public static double[] getOffsetFromEntity(Player player, LivingEntity entity) {
        double yawOffset = Math.abs(yawTo180F(player.getEyeLocation().getYaw()) - yawTo180F(getRotations(player.getLocation(), entity.getLocation())[0]));
        double pitchOffset = Math.abs(Math.abs(player.getEyeLocation().getPitch()) - Math.abs(getRotations(player.getLocation(), entity.getLocation())[1]));
        return new double[]{yawOffset, pitchOffset};
    }

    public static boolean close(Double[] arrdouble, int n) {
        boolean bl;
        double d = arrdouble[4];
        double d2 = arrdouble[3];
        double d3 = arrdouble[2];
        double d4 = arrdouble[1];
        double d5 = arrdouble[0];
        boolean bl2 = (d >= d2 ? d - d2 : d2 - d) <= (double)n;
        boolean bl3 = (d >= d3 ? d - d3 : d3 - d) <= (double)n;
        boolean bl4 = (d >= d4 ? d - d4 : d4 - d) <= (double)n;
        boolean bl5 = bl = (d >= d5 ? d - d5 : d5 - d) <= (double)n;
        if (bl2 && bl3 && bl4 && bl) {
            return true;
        }
        return false;
    }

    public static Vector getRotation(Location location, Location location2) {
        double d = location2.getX() - location.getX();
        double d2 = location2.getY() - location.getY();
        double d3 = location2.getZ() - location.getZ();
        double d4 = Math.sqrt(d * d + d3 * d3);
        float f = (float)(Math.atan2(d3, d) * 180.0 / 3.141592653589793) - 90.0f;
        float f2 = (float)(- Math.atan2(d2, d4) * 180.0 / 3.141592653589793);
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
		double x = Math.abs(Math.abs(to.getX()) - Math.abs(from.getX()));
		double z = Math.abs(Math.abs(to.getZ()) - Math.abs(from.getZ()));

		return Math.sqrt(x * x + z * z);
	}

	public static double getVerticalDistance(Location to, Location from) {
		double y = Math.abs(Math.abs(to.getY()) - Math.abs(from.getY()));
		
		return Math.sqrt(y * y);
	}
	public static double getVerticalDistance3(Location location, Location location2) {
	    double d = (location2.getY() - location.getY()) * (location2.getY() - location.getY());
	    double d2 = Math.sqrt(d);
	    double d3 = Math.abs(d2);
	    return d3;
	}

	public static double getHorizontalDistance3(Location location, Location location2) {
	    double d = 0.0;
	    double d2 = (location2.getX() - location.getX()) * (location2.getX() - location.getX());
	    double d3 = (location2.getZ() - location.getZ()) * (location2.getZ() - location.getZ());
	    double d4 = Math.sqrt(d2 + d3);
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
	    double d = (location2.getX() - location.getX()) * (location2.getX() - location.getX());
	    double d2 = (location2.getY() - location.getY()) * (location2.getY() - location.getY());
	    double d3 = (location2.getZ() - location.getZ()) * (location2.getZ() - location.getZ());
	    double d4 = Math.sqrt(d + d2 + d3);
	    double d5 = Math.abs(d4);
	    return d5;
	}

	public static float getOffset(Player player, LivingEntity livingEntity) {
	    double d = 0.0;
	    Location location = player.getLocation().add(0.0, player.getEyeHeight(), 0.0);
	    Location location2 = player.getLocation().add(0.0, player.getEyeHeight(), 0.0);
	    Vector vector = new Vector(location2.getYaw(), location2.getPitch(), 0.0f);
	    Vector vector2 = getRotation(location2, location);
	    double d2 = clamp180(vector.getX() - vector2.getX());
	    double d3 = clamp180(vector.getY() - vector2.getY());
	    double d4 = getHorizontalDistance3(location2, location);
	    double d5 = getDistance3D(location2, location);
	    double d6 = d2 * d4 * d5;
	    double d7 = d3 * Math.abs(location.getY() - location2.getY()) * d5;
	    d += Math.abs(d6);
	    d += Math.abs(d7);
	    return 0.0f;
	}
	public static double getHorizontalDistance2(Location location, Location location2) {
	    double d = (location2.getX() - location.getX()) * (location2.getX() - location.getX());
	    double d2 = (location2.getZ() - location.getZ()) * (location2.getZ() - location.getZ());
	    double d3 = Math.sqrt(d + d2);
	    double d4 = Math.abs(d3);
	    return d4;
	}


	public static double getDistance3D2(Location location, Location location2) {
	    double d = (location2.getX() - location.getX()) * (location2.getX() - location.getX());
	    double d2 = (location2.getY() - location.getY()) * (location2.getY() - location.getY());
	    double d3 = (location2.getZ() - location.getZ()) * (location2.getZ() - location.getZ());
	    double d4 = Math.sqrt(d + d2 + d3);
	    double d5 = Math.abs(d4);
	    return d5;
	}

	public static double[] getOffsets2(Player player, LivingEntity livingEntity) {
	    Location location = livingEntity.getLocation().add(0.0, livingEntity.getEyeHeight(), 0.0);
	    Location location2 = player.getLocation().add(0.0, player.getEyeHeight(), 0.0);
	    Vector vector = new Vector(location2.getYaw(), location2.getPitch(), 0.0f);
	    Vector vector2 = getRotation(location2, location);
	    double d = fix180(vector.getX() - vector2.getX());
	    double d2 = fix180(vector.getY() - vector2.getY());
	    double d3 = getHorizontalDistance2(location2, location);
	    double d4 = getDistance3D2(location2, location);
	    double d5 = d * d3 * d4;
	    double d6 = d2 * Math.abs(Math.sqrt(location.getY() - location2.getY())) * d4;
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
        DecimalFormat twoDForm = new DecimalFormat(format);
        return Double.valueOf(twoDForm.format(d).replaceAll(",", "."));
    }

    public static String decrypt(String strEncrypted) {
        String strData = "";

        try {
            byte[] decoded = Base64.getDecoder().decode(strEncrypted);
            strData = (new String(decoded, StandardCharsets.UTF_8) + "\n");

        } catch (Exception e) {
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
}