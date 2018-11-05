package me.rida.anticheat.utils;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AngleUtil {
    public static Vector getRotation(Location location, Location location2) {
        double d = location2.getX() - location.getX();
        double d2 = location2.getY() - location.getY();
        double d3 = location2.getZ() - location.getZ();
        double d4 = Math.sqrt(d * d + d3 * d3);
        float f = (float)(Math.atan2(d3, d) * 180.0 / 3.141592653589793) - 90.0f;
        float f2 = (float)(- Math.atan2(d2, d4) * 180.0 / 3.141592653589793);
        return new Vector(f, f2, 0.0f);
    }

    public static double getVerticalDistance(Location location, Location location2) {
        double d = (location2.getY() - location.getY()) * (location2.getY() - location.getY());
        double d2 = Math.sqrt(d);
        double d3 = Math.abs(d2);
        return d3;
    }

    public static double getHorizontalDistance(Location location, Location location2) {
        double d = (location2.getX() - location.getX()) * (location2.getX() - location.getX());
        double d2 = (location2.getZ() - location.getZ()) * (location2.getZ() - location.getZ());
        double d3 = Math.sqrt(d + d2);
        double d4 = Math.abs(d3);
        return d4;
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

    public static double[] getOffsets(Player player, LivingEntity livingEntity) {
        Location location = livingEntity.getLocation().add(0.0, livingEntity.getEyeHeight(), 0.0);
        Location location2 = player.getLocation().add(0.0, player.getEyeHeight(), 0.0);
        Vector vector = new Vector(location2.getYaw(), location2.getPitch(), 0.0f);
        Vector vector2 = AngleUtil.getRotation(location2, location);
        double d = AngleUtil.fix180(vector.getX() - vector2.getX());
        double d2 = AngleUtil.fix180(vector.getY() - vector2.getY());
        double d3 = AngleUtil.getHorizontalDistance(location2, location);
        double d4 = AngleUtil.getDistance3D(location2, location);
        double d5 = d * d3 * d4;
        double d6 = d2 * Math.abs(Math.sqrt(location.getY() - location2.getY())) * d4;
        return new double[]{Math.abs(d5), Math.abs(d6)};
    }
}

