package me.rida.anticheat.utils;

import org.bukkit.Location;

public class TrigUtils
{
    public static double getDirection(Location from, Location to)
    {
        if ((from == null) || (to == null)) {
            return 0.0D;
        }
        double difX = to.getX() - from.getX();
        double difZ = to.getZ() - from.getZ();

        return wrapAngleTo180_float((float)(Math.atan2(difZ, difX) * 180.0D / 3.141592653589793D) - 90.0F);
    }

    public static double getDistance(double p1, double p2, double p3, double p4)
    {
        double delta1 = p3 - p1;
        double delta2 = p4 - p2;

        return Math.sqrt(delta1 * delta1 + delta2 * delta2);
    }

    public static float wrapAngleTo180_float(float value)
    {
        value %= 360.0F;
        if (value >= 180.0F) {
            value -= 360.0F;
        }
        if (value < -180.0F) {
            value += 360.0F;
        }
        return value;
    }

    public static float fixRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_)
    {
        float var4 = wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
        if (var4 > p_70663_3_) {
            var4 = p_70663_3_;
        }
        if (var4 < -p_70663_3_) {
            var4 = -p_70663_3_;
        }
        return p_70663_1_ + var4;
    }
}
