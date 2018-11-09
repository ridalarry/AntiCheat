package me.rida.anticheat.utils.a;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class MathUtils {


    public static boolean elapsed(long from, long required) {
		return System.currentTimeMillis() - from > required;
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

    public static double getFraction(double d) {
        return d % 1.0;
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
}
