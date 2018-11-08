package me.rida.anticheat.checks.movement;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.UtilNewVelocity;
import me.rida.anticheat.utils.UtilVelocity;
import me.rida.anticheat.utils.UtilsA;
import me.rida.anticheat.utils.UtilsB;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class FlyA extends Check {
    public FlyA(AntiCheat AntiCheat) {
		super("FlyA", "Fly", AntiCheat);
		this.setBannable(true);
		this.setEnabled(true);
		setMaxViolations(4);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        Player p = e.getPlayer();
        if (p.getGameMode().equals(GameMode.CREATIVE)
                || p.getAllowFlight()
                || e.getPlayer().getVehicle() != null
                || p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE
                || UtilsA.isOnClimbable(p, 0)
                || UtilsA.isOnClimbable(p, 1) || UtilVelocity.didTakeVelocity(p)) {
            return;
        }

        DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);

        if (data == null) {
            return;
        }
        //Ascension Check
        if (!UtilNewVelocity.didTakeVel(p) && !UtilsA.wasOnSlime(p)) {
            Vector vec = new Vector(to.getX(), to.getY(), to.getZ());
            double Distance = vec.distance(new Vector(from.getX(), from.getY(), from.getZ()));
            if (p.getFallDistance() == 0.0f && p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR && p.getLocation().getBlock().getRelative(BlockFace.UP).getType() == Material.AIR) {
                if (Distance > 0.50 && !UtilsA.isOnGround(p) && e.getTo().getY() > e.getFrom().getY() && e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ() && !UtilVelocity.didTakeVelocity(p)) {
                	getAntiCheat().logCheat(this, p, "[3] Distance: " + Distance,  "(Type: A)");
                } else if (Distance > 0.90 && !UtilsA.isOnGround(p) && e.getTo().getY() > e.getFrom().getY() && e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ()) {
                	getAntiCheat().logCheat(this, p, "[2] Distance: " + Distance, "(Type: A)");
                } else if (Distance > 1.0 && !UtilsA.isOnGround(p) && e.getTo().getY() > e.getFrom().getY() && e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ()) {
                	getAntiCheat().logCheat(this, p, "[3] Distance: " + Distance, "(Type: A)");
                } else if (Distance > 3.24 && !UtilsA.isOnGround(p) && e.getTo().getY() > e.getFrom().getY() && e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ()) {
                	getAntiCheat().logCheat(this, p, "[4] Distance: " + Distance, "(Type: A)");
                }
            }
        }
        if (!UtilNewVelocity.didTakeVel(p) && !UtilsA.wasOnSlime(p)) {
            if (e.getTo().getY() > e.getFrom().getY() && data.getAirTicks() > 2 && !UtilVelocity.didTakeVelocity(p)) {
                if (!UtilsA.isOnGround4(p) && !UtilsA.onGround2(p) && !UtilsA.isOnGround(p)) {
                    if (UtilsA.getDistanceToGround(p) > 2) {
                        if (data.getGoingUp_Blocks() >= 3 && data.getAirTicks() >= 10) {
                            // getAntiCheat().logCheat(this, p, "[5] Distance: 10 blocks or more", "(Type: A)");
                         //   setBackPlayer(p);
                        } else {
                            data.setGoingUp_Blocks(data.getGoingUp_Blocks() + 1);
                        }
                    } else {
                        data.setGoingUp_Blocks(0);
                    }
                } else {
                    data.setGoingUp_Blocks(0);
                }
            } else if (e.getTo().getY() < e.getFrom().getY()) {
                data.setGoingUp_Blocks(0);
            } else {
                data.setGoingUp_Blocks(0);
            }
        } else {
            data.setGoingUp_Blocks(0);
        }
        //Hover check
        if(!UtilsA.isOnGround(p)) {
            double distanceToGround = getDistanceToGround(p);
            double yDiff = UtilsA.getVerticalDistance(e.getFrom(), e.getTo());
            int verbose = data.getFlyHoverVerbose();

            if(distanceToGround > 2) {
                verbose = yDiff == 0 ? verbose + 6 : yDiff < 0.06 ? verbose + 4 : 0;
            } else if(data.getAirTicks() > 7
                    && yDiff < 0.001) {
                verbose+= 2;
            } else {
                verbose = 0;
            }

            if(verbose > 20) {
            	getAntiCheat().logCheat(this, p, "[6]", "(Type: A)");
                verbose = 0;
            }
            data.setFlyHoverVerbose(verbose);
        }

        //Glide check
        if (UtilsA.getDistanceToGround(p) >  3) {
            double OffSet = e.getFrom().getY() - e.getTo().getY();
            long Time = System.currentTimeMillis();
            if (OffSet <= 0.0 || OffSet > 0.16) {
                data.setGlideTicks(0);
                return;
            }
            if (data.getGlideTicks() != 0) {
                Time = data.getGlideTicks();
            }
            long Millis = System.currentTimeMillis() - Time;
            if (Millis > 200L) {
            	if (UtilsB.isInLiquid(p)) {
            		return;
            	}
                getAntiCheat().logCheat(this, p, "[7]", "(Type: A)");
                data.setGlideTicks(0);
            }
            data.setGlideTicks(Time);
        } else {
           data.setGlideTicks(0);
        }

        //Velocity Diff check
        double diffY = Math.abs(from.getY() - to.getY());
        double lastDiffY = data.getLastVelocityFlyY();
        int verboseC = data.getFlyVelocityVerbose();

        double finalDifference = Math.abs(diffY - lastDiffY);

        //Bukkit.broadcastMessage(Math.abs(diffY - lastDiffY) + ", " + PlayerUtils.isOnGround(p));

        if(finalDifference < 0.08
                && e.getFrom().getY() < e.getTo().getY()
                && !UtilsA.isOnGround(p) && !p.getLocation().getBlock().isLiquid() && !UtilsB.isNearLiquid(p)
                && !UtilNewVelocity.didTakeVel(p) && !UtilVelocity.didTakeVelocity(p)) {
            if(++verboseC > 8) {
            	if (!UtilsA.wasOnSlime(p)) {
            		verboseC = 0;
            	}
            	else {
            		getAntiCheat().logCheat(this, p, "[8]", "(Type: A)");
            		verboseC = 0;
            	}
            }
        } else {
            verboseC = verboseC > 0 ? verboseC - 1 : 0;
        }
        data.setLastVelocityFlyY(diffY);
        data.setFlyVelocityVerbose(verboseC);
    }

    private int getDistanceToGround(Player p){
        Location loc = p.getLocation().clone();
        double y = loc.getBlockY();
        int distance = 0;
        for (double i = y; i >= 0; i--){
            loc.setY(i);
            if(loc.getBlock().getType().isSolid() || loc.getBlock().isLiquid())break;
            distance++;
        }
        return distance;
    }
}