package me.rida.anticheat.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AimUtil {

    public static AimUtil instance;

    public void setInstance() {
        instance = this;
    }

    public boolean lookingTo(final Player player, final Player target) {
        double offset = 0.0;
        final Location entityLoc = player.getLocation().add(0.0, target.getEyeHeight(), 0.0);
        final Location playerLoc = player.getLocation().add(0.0, player.getEyeHeight(), 0.0);
        final Vector playerRotation = new Vector(playerLoc.getYaw(), playerLoc.getPitch(), 0.0f);
        final Vector expectedRotation = getRotation(playerLoc, entityLoc);
        final double deltaYaw = clamp180(playerRotation.getX() - expectedRotation.getX());
        final double deltaPitch = clamp180(playerRotation.getY() - expectedRotation.getY());
        final double horizontalDistance = getHorizontalDistance(playerLoc, entityLoc);
        final double distance = getDistance3D(playerLoc, entityLoc);
        final double offsetX = deltaYaw * horizontalDistance * distance;
        final double offsetY = deltaPitch * Math.abs(entityLoc.getY() - playerLoc.getY()) * distance;
        offset += Math.abs(offsetX);
        offset += Math.abs(offsetY);
        if (offset > 360.0) {
            return true;
        }
        return false;
    }

    public boolean isLookingToEntity(final Player p, final Entity to) {
        boolean looking = false;
        final Vector n = to.getLocation().toVector().subtract(p.getLocation().toVector());
        final Vector vec$ = p.getLocation().toVector().subtract(to.getLocation().toVector());
        if ((p.getLocation().getDirection().normalize().crossProduct(n).lengthSquared() < 1.05 || to.getLocation().getDirection().normalize().crossProduct(vec$).lengthSquared() < 1.05) && (n.normalize().dot(p.getLocation().getDirection().normalize()) >= 0.0 || vec$.normalize().dot(to.getLocation().getDirection().normalize()) >= 0.0)) {
            looking = true;
        }
        return looking;
    }


    public double clamp180(double theta) {
        theta %= 360.0;
        if (theta >= 180.0) {
            theta -= 360.0;
        }
        if (theta < -180.0) {
            theta += 360.0;
        }
        return theta;
    }

    public double getDistance3D(final Location one, final Location two) {
        double toReturn = 0.0;
        final double xSqr = (two.getX() - one.getX()) * (two.getX() - one.getX());
        final double ySqr = (two.getY() - one.getY()) * (two.getY() - one.getY());
        final double zSqr = (two.getZ() - one.getZ()) * (two.getZ() - one.getZ());
        final double sqrt = Math.sqrt(xSqr + ySqr + zSqr);
        toReturn = Math.abs(sqrt);
        return toReturn;
    }

    public double getHorizontalDistance(final Location one, final Location two) {
        double toReturn = 0.0;
        final double xSqr = (two.getX() - one.getX()) * (two.getX() - one.getX());
        final double zSqr = (two.getZ() - one.getZ()) * (two.getZ() - one.getZ());
        final double sqrt = Math.sqrt(xSqr + zSqr);
        toReturn = Math.abs(sqrt);
        return toReturn;
    }

    public Vector getRotation(final Location one, final Location two) {
        final double dx = two.getX() - one.getX();
        final double dy = two.getY() - one.getY();
        final double dz = two.getZ() - one.getZ();
        final double distanceXZ = Math.sqrt(dx * dx + dz * dz);
        final float yaw = (float) (Math.atan2(dz, dx) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float) (-(Math.atan2(dy, distanceXZ) * 180.0 / 3.141592653589793));
        return new Vector(yaw, pitch, 0.0f);
    }
}