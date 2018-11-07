package me.rida.anticheat.checks.movement;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.TimerUtils;
import me.rida.anticheat.utils.UtilNewVelocity;
import me.rida.anticheat.utils.UtilVelocity;
import me.rida.anticheat.utils.UtilsA;
import me.rida.anticheat.utils.UtilsB;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedA extends Check {

    public SpeedA(AntiCheat AntiCheat) {
        super("SpeedA", "Speed", AntiCheat);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
        Location to = e.getTo();
        Location from = e.getFrom();

        if (((to.getX() == from.getX() && to.getY() == from.getY() && to.getZ() == from.getZ()))
                || p.getAllowFlight()
                || p.getGameMode().equals(GameMode.CREATIVE)
                || e.getPlayer().getVehicle() != null
        		|| UtilsA.isNearIce(p)
		        || UtilsB.isNearSlime(p)
        		|| UtilsA.wasOnSlime(p)){
            return;
        }

        if (data != null) {

            if (data.isSpeed_PistonExpand_Set()) {
                if (TimerUtils.elapsed(data.getSpeed_PistonExpand_MS(), 9900L)) {
                    data.setSpeed_PistonExpand_Set(false);
                }
            }
            double speed = UtilsA.getHorizontalDistance(from, to);
           if(UtilsA.elapsed(data.getLastVelMS(), 3000)) {
               int verbose = data.getSpeedAVerbose();
               double speedEffect = UtilsA.getPotionEffectLevel(p, PotionEffectType.SPEED);
               double speedAThreshold = (data.getAirTicks() > 0 ? data.getAirTicks() >= 6
                       ? data.getAirTicks() == 13 ? 0.466 : 0.35 : (0.345 * Math.pow(0.986938064, data.getAirTicks()))
                       : data.getGroundTicks() > 5 ? 0.362 : data.getGroundTicks() == 3 ? 0.62 : 0.4)
                       + (data.getAirTicks() > 0 ? (-0.001 * data.getAirTicks() + 0.014) : (0.018 - (data.getGroundTicks() >= 6 ? 0 : data.getGroundTicks() * 0.001)) * speedEffect);

               speedAThreshold = data.getAboveBlockTicks() > 0 ? speedAThreshold + 0.25 : speedAThreshold;
               speedAThreshold = data.getIceTicks() > 0 ? speedAThreshold + 0.14 : speedAThreshold;
               speedAThreshold = data.getSlimeTicks() > 0 ? speedAThreshold + 0.1 : speedAThreshold;
               speedAThreshold = data.getIceTicks() > 0 && data.getAboveBlockTicks() > 0 ? speedAThreshold + 0.24 : speedAThreshold;

               if(UtilsA.isOnStair(p.getLocation())
                       || UtilsA.isOnSlab(p.getLocation())) {
                   speedAThreshold+= 0.12;
               }


               if (speed > speedAThreshold) {
                   verbose += 8;
               } else {
                   verbose = verbose > 0 ? verbose - 1 : 0;
               }

               if (verbose > 40) {

                   if (((to.getX() == from.getX() && to.getY() == from.getY() && to.getZ() == from.getZ()))
                           	|| p.getAllowFlight()
                           	|| p.getGameMode().equals(GameMode.CREATIVE)
                           	|| e.getPlayer().getVehicle() != null
                   			|| UtilsA.isNearIce(p)
                   			|| UtilsB.isNearSlime(p)
                   			|| UtilsA.wasOnSlime(p)
                   			|| p.isSprinting()){
                       return;
                   }
                   getAntiCheat().logCheat(this, p, "[0] - Player Moved Too Fast.", "(Type: A)");
                   verbose = 0;
               }

               data.setSpeedAVerbose(verbose);
           } else {
               data.setSpeedAVerbose(0);
           }

            Location l = p.getLocation();
            int x = l.getBlockX();
            int y = l.getBlockY();
            int z = l.getBlockZ();
            Location blockLoc = new Location(p.getWorld(), x, y - 1, z);
            Location loc = new Location(p.getWorld(), x, y, z);
            Location loc2 = new Location(p.getWorld(), x, y + 1, z);
            Location above = new Location(p.getWorld(), x, y + 2, z);
            Location above3 = new Location(p.getWorld(), x - 1, y + 2, z - 1);
            double MaxAirSpeed = 0.4;
            double maxSpeed = 0.42;
            double MaxSpeedNEW = 0.75;
            if (data.isNearIce()) {
                MaxSpeedNEW = 1.0;
            }
            double Max = 0.28;
            if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                int level = UtilsA.getPotionEffectLevel(p, PotionEffectType.SPEED);
                if (level > 0) {
                    maxSpeed = (maxSpeed * (((level * 20) * 0.011) + 1));
                    MaxAirSpeed = (MaxAirSpeed * (((level * 20) * 0.011) + 1));
                    maxSpeed = (maxSpeed * (((level * 20) * 0.011) + 1));
                    MaxSpeedNEW = (MaxSpeedNEW * (((level * 20) * 0.011) + 1));
                }
            }
            MaxAirSpeed += p.getWalkSpeed() > 0.2 ? p.getWalkSpeed() * 0.8 : 0;
            maxSpeed += p.getWalkSpeed() > 0.2 ? p.getWalkSpeed() * 0.8 : 0;
            if (!UtilsA.isOnGround4(p) && speed >= MaxAirSpeed && !data.isNearIce()
                    && blockLoc.getBlock().getType() != Material.ICE && !blockLoc.getBlock().isLiquid()
                    && !loc.getBlock().isLiquid() && blockLoc.getBlock().getType() != Material.PACKED_ICE
                    && above.getBlock().getType() == Material.AIR && above3.getBlock().getType() == Material.AIR
                    && blockLoc.getBlock().getType() != Material.AIR && !UtilNewVelocity.didTakeVel(p) && !UtilsA.isNearStiar(p)) {
                if (!UtilNewVelocity.didTakeVel(p) && UtilsA.getDistanceToGround(p) > 4 == false) {
                    if (data.getSpeed2Verbose() >= 8 || p.getNoDamageTicks() == 0 == false && !UtilVelocity.didTakeVelocity(p) && !UtilNewVelocity.didTakeVel(p)
                            && p.getLocation().add(0, 1.94, 0).getBlock().getType() != Material.AIR) {
                        getAntiCheat().logCheat(this, p, "[1] - Player Moved Too Fast.", "(Type: A)");
                    } else {
                        data.setSpeed2Verbose(data.getSpeed2Verbose() + 1);
                    }
                } else {
                    data.setSpeed2Verbose(0);
                }
            }

            double onGroundDiff = (to.getY() - from.getY());
            if (speed > Max && !UtilsA.isAir(p) && onGroundDiff <= -0.4 && p.getFallDistance() <= 0.4 && blockLoc.getBlock().getType() != Material.ICE
                    && e.getTo().getY() != e.getFrom().getY() && blockLoc.getBlock().getType() != Material.PACKED_ICE
                    && loc2.getBlock().getType() != Material.TRAP_DOOR && above.getBlock().getType() == Material.AIR
                    && above3.getBlock().getType() == Material.AIR && data.getAboveBlockTicks() != 0) {
                getAntiCheat().logCheat(this, p, "[2] - Player Moved Too Fast.", "(Type: A)");
            }

            if (speed > 0.7 && !UtilsA.isAir(p) && onGroundDiff <= -0.4 && p.getFallDistance() <= 0.4 && blockLoc.getBlock().getType() != Material.ICE
                    && e.getTo().getY() != e.getFrom().getY() && blockLoc.getBlock().getType() != Material.PACKED_ICE
                    && loc2.getBlock().getType() != Material.TRAP_DOOR && above.getBlock().getType() == Material.AIR
                    && above3.getBlock().getType() == Material.AIR && !UtilNewVelocity.didTakeVel(p) && !UtilVelocity.didTakeVelocity(p) && !UtilsA.hasPistonNear(p) &&
                    p.getLocation().getBlock().getType() != Material.PISTON_MOVING_PIECE && p.getLocation().getBlock().getType() != Material.PISTON_BASE
                    && p.getLocation().getBlock().getType() != Material.PISTON_STICKY_BASE && !UtilsA.isNearPistion(p) && !data.isSpeed_PistonExpand_Set()) {
                if (!data.isSpeed_PistonExpand_Set()) {
                    if (data.getSpeed_C_3_Verbose() > 1) {
                        getAntiCheat().logCheat(this, p, "[3] - Player Moved Too Fast.", "(Type: A)");
                    } else {
                        data.setSpeed_C_3_Verbose(data.getSpeed_C_3_Verbose() + 1);
                    }

                }
                if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                    int level = UtilsA.getPotionEffectLevel(p, PotionEffectType.SPEED);
                    if (level > 0) {
                        maxSpeed = (maxSpeed * (((level * 20) * 0.011) + 1));
                        MaxAirSpeed = (MaxAirSpeed * (((level * 20) * 0.011) + 1));
                        maxSpeed = (maxSpeed * (((level * 20) * 0.011) + 1));
                        MaxSpeedNEW = (MaxSpeedNEW * (((level * 20) * 0.011) + 1));
                    }
                }
                MaxAirSpeed += p.getWalkSpeed() > 0.2 ? p.getWalkSpeed() * 0.8 : 0;
                maxSpeed += p.getWalkSpeed() > 0.2 ? p.getWalkSpeed() * 0.8 : 0;
                if (!UtilsA.isOnGround4(p) && speed >= MaxAirSpeed && !data.isNearIce()
                        && blockLoc.getBlock().getType() != Material.ICE && !blockLoc.getBlock().isLiquid()
                        && !loc.getBlock().isLiquid() && blockLoc.getBlock().getType() != Material.PACKED_ICE
                        && above.getBlock().getType() == Material.AIR && above3.getBlock().getType() == Material.AIR
                        && blockLoc.getBlock().getType() != Material.AIR && !UtilNewVelocity.didTakeVel(p) && !UtilsA.isNearStiar(p)) {
                    if (!UtilNewVelocity.didTakeVel(p) && UtilsA.getDistanceToGround(p) > 4 == false) {
                        if (data.getSpeed2Verbose() >= 8 || p.getNoDamageTicks() == 0 == false && !UtilVelocity.didTakeVelocity(p) && !UtilNewVelocity.didTakeVel(p)
                                && p.getLocation().add(0, 1.94, 0).getBlock().getType() != Material.AIR) {
                            getAntiCheat().logCheat(this, p, "[4] - Player Moved Too Fast.", "(Type: A)");
                        } else {
                            data.setSpeed2Verbose(data.getSpeed2Verbose() + 1);
                        }
                    } else {
                        data.setSpeed2Verbose(0);
                    }
                    boolean speedPot = false;
                    for (PotionEffect effect : p.getActivePotionEffects()) {
                        if (effect.getType().equals(PotionEffectType.SPEED)) {
                            speedPot = true;
                        }
                    }
                    if (speed > 0.29 && UtilsA.isOnGround(p) && !data.isNearIce() && !UtilsA.isNearStiar(p) && !UtilNewVelocity.didTakeVel(p) && !speedPot) {
                        if (data.getSpeed_OnGround_Verbose() >= 5) {
                        }

                        if (speed > Max && !UtilsA.isAir(p) && onGroundDiff <= -0.4 && p.getFallDistance() <= 0.4 && blockLoc.getBlock().getType() != Material.ICE
                                && e.getTo().getY() != e.getFrom().getY() && blockLoc.getBlock().getType() != Material.PACKED_ICE
                                && loc2.getBlock().getType() != Material.TRAP_DOOR && above.getBlock().getType() == Material.AIR
                                && above3.getBlock().getType() == Material.AIR && data.getIceTicks() == 0 && !UtilsA.hasIceNear(p)) {
                            getAntiCheat().logCheat(this, p, "[5] - Player Moved Too Fast.", "(Type: A)");
                        }

                        if (speed > 0.7 && !UtilsA.isAir(p) && onGroundDiff <= -0.4 && p.getFallDistance() <= 0.4 && blockLoc.getBlock().getType() != Material.ICE
                                && e.getTo().getY() != e.getFrom().getY() && blockLoc.getBlock().getType() != Material.PACKED_ICE
                                && loc2.getBlock().getType() != Material.TRAP_DOOR && above.getBlock().getType() == Material.AIR
                                && above3.getBlock().getType() == Material.AIR && !UtilNewVelocity.didTakeVel(p) && !UtilVelocity.didTakeVelocity(p) && !UtilsA.hasPistonNear(p) &&
                                p.getLocation().getBlock().getType() != Material.PISTON_MOVING_PIECE && p.getLocation().getBlock().getType() != Material.PISTON_BASE
                                && p.getLocation().getBlock().getType() != Material.PISTON_STICKY_BASE && !UtilsA.isNearPistion(p)) {
                            getAntiCheat().logCheat(this, p, "[6] - Player Moved Too Fast.", "(Type: A)");

                        }


                    }
                }
            }
        }
    }
}