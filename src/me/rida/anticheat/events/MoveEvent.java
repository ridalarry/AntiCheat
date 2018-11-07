package me.rida.anticheat.events;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.TimerUtils;
import me.rida.anticheat.utils.UtilsA;
import me.rida.anticheat.utils.UtilsB;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

public class MoveEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (e.getFrom().getX() != e.getTo().getX()
                || e.getFrom().getY() != e.getTo().getY()
                || e.getFrom().getZ() != e.getTo().getZ()) {
            DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(player);

            if (data != null) {
                data.onGround = UtilsB.isOnTheGround(player);
                data.onStairSlab = UtilsB.isInStairs(player);
                data.inLiquid = UtilsB.isInLiquid(player);
                data.onIce = UtilsB.isOnIce(player);
                data.onClimbable = UtilsB.isOnClimbable(player);
                data.underBlock = UtilsB.inUnderBlock(player);
                
                if(data.onGround) {
                    data.groundTicks++;
                    data.airTicks = 0;
                } else {
                    data.airTicks++;
                    data.groundTicks = 0;
                }
                
                data.iceTicks = Math.max(0, data.onIce ? data.iceTicks + 1  : data.iceTicks - 1);
                data.liquidTicks = Math.max(0, data.inLiquid ? data.liquidTicks + 1  : data.liquidTicks - 1);
                data.blockTicks = Math.max(0, data.underBlock ? data.blockTicks + 1  : data.blockTicks - 1);
            }
        }

        DataPlayer data = AntiCheat.getInstance().getDataManager().getData(player);

        if (data.isNearIce()) {
            if (TimerUtils.elapsed(data.getIsNearIceTicks(),500L)) {
                if (!UtilsB.isNearIce(player)) {
                    data.setNearIce(false);
                } else {
                    data.setIsNearIceTicks(TimerUtils.nowlong());
                }
            }
        }
        Location l = player.getLocation();
        int x = l.getBlockX();
        int y = l.getBlockY();
        int z = l.getBlockZ();
        Location loc1 = new Location(player.getWorld(), x, y + 1, z);
        if (loc1.getBlock().getType() != Material.AIR) {
            if (!data.isBlockAbove_Set()) {
                data.setBlockAbove_Set(true);
                data.setBlockAbove(TimerUtils.nowlong());
            } else {
                if (TimerUtils.elapsed(data.getBlockAbove(),1000L)) {
                    if (loc1.getBlock().getType() == Material.AIR) {
                        data.setBlockAbove_Set(false);
                    } else {
                        data.setBlockAbove_Set(true);
                        data.setBlockAbove(TimerUtils.nowlong());
                    }
                }
            }
        } else {
            if (data.isBlockAbove_Set()) {
                if (TimerUtils.elapsed(data.getBlockAbove(), 1000L)) {
                    if (loc1.getBlock().getType() == Material.AIR) {
                        data.setBlockAbove_Set(false);
                    } else {
                        data.setBlockAbove_Set(true);
                        data.setBlockAbove(TimerUtils.nowlong());
                    }
                }
            }
        }

        if (UtilsA.hasIceNear(player)) {
            if(data.getIceTicks() < 60) data.setIceTicks(data.getIceTicks() + 1);
        } else if(data.getIceTicks() > 0) {
            data.setIceTicks(data.getIceTicks() - 1);
        }

        if(UtilsA.wasOnSlime(player)) {
            if(data.getSlimeTicks() < 50) {
                data.setSlimeTicks(data.getSlimeTicks() + 1);
            } else if(data.getSlimeTicks() > 0) {
                data.setSlimeTicks(data.getSlimeTicks() - 1);
            }
        }

        if (UtilsB.isHalfBlock(player.getLocation().add(0,-0.50,0).getBlock()) || UtilsB.isNearHalfBlock(player)) {
            if (!data.isHalfBlocks_MS_Set()) {
                data.setHalfBlocks_MS_Set(true);
                data.setHalfBlocks_MS(TimerUtils.nowlong());
            } else {
                if (TimerUtils.elapsed(data.getHalfBlocks_MS(),900L)) {
                    if (UtilsB.isHalfBlock(player.getLocation().add(0,-0.50,0).getBlock()) || UtilsB.isNearHalfBlock(player)) {
                        data.setHalfBlocks_MS_Set(true);
                        data.setHalfBlocks_MS(TimerUtils.nowlong());
                    } else {
                        data.setHalfBlocks_MS_Set(false);
                    }
                }
            }
        } else {
            if (TimerUtils.elapsed(data.getHalfBlocks_MS(),900L)) {
                if (UtilsB.isHalfBlock(player.getLocation().add(0,-0.50,0).getBlock()) || UtilsB.isNearHalfBlock(player)) {
                    data.setHalfBlocks_MS_Set(true);
                    data.setHalfBlocks_MS(TimerUtils.nowlong());
                } else {
                    data.setHalfBlocks_MS_Set(false);
                }
            }
        }
        if (UtilsB.isNearIce(player) && !data.isNearIce()) {
            data.setNearIce(true);
            data.setIsNearIceTicks(TimerUtils.nowlong());
        } else if (UtilsB.isNearIce(player)) {
            data.setIsNearIceTicks(TimerUtils.nowlong());
        }

        double distance = UtilsA.getVerticalDistance(e.getFrom(), e.getTo());

        boolean onGround = UtilsA.isOnGround4(player);
        if(!onGround
                && e.getFrom().getY() > e.getTo().getY()) {
            data.setFallDistance(data.getFallDistance() + distance);
        } else {
            data.setFallDistance(0);
        }

        if(onGround) {
            data.setGroundTicks(data.getGroundTicks() + 1);
            data.setAirTicks(0);
        } else {
            data.setAirTicks(data.getAirTicks() + 1);
            data.setGroundTicks(0);
        }

        if(UtilsA.isOnGround(player.getLocation().add(0, 2, 0))) {
            data.setAboveBlockTicks(data.getAboveBlockTicks() + 2);
        } else if(data.getAboveBlockTicks() > 0) {
            data.setAboveBlockTicks(data.getAboveBlockTicks() - 1);
        }

        if(UtilsA.isInWater(player.getLocation())
                || UtilsA.isInWater(player.getLocation().add(0, 1, 0))) {
            data.setWaterTicks(data.getWaterTicks() + 1);
        } else if(data.getWaterTicks() > 0) {
            data.setWaterTicks(data.getWaterTicks() - 1);
        }
    }
    @EventHandler
    public void onVelocity(PlayerVelocityEvent event) {
        DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());

        if(data == null) {
            return;
        }
        if(event.getVelocity().getY() > -0.078 || event.getVelocity().getY() < -0.08) {
            data.lastVelocityTaken = System.currentTimeMillis();
        }
    }
}
