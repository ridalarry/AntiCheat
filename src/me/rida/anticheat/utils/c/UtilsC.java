package me.rida.anticheat.utils.c;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class UtilsC {
    public static HashSet<Byte> blockPassSet = new HashSet();
    public static HashSet<Byte> blockAirFoliageSet = new HashSet();
    public static HashSet<Byte> fullSolid = new HashSet();
    public static HashSet<Byte> blockUseSet = new HashSet();

    public static boolean isSolid(final byte block) {
        if (UtilsC.blockPassSet.isEmpty()) {
            UtilsC.blockPassSet.add((byte) 0);
            UtilsC.blockPassSet.add((byte) 6);
            UtilsC.blockPassSet.add((byte) 8);
            UtilsC.blockPassSet.add((byte) 9);
            UtilsC.blockPassSet.add((byte) 10);
            UtilsC.blockPassSet.add((byte) 11);
            UtilsC.blockPassSet.add((byte) 27);
            UtilsC.blockPassSet.add((byte) 28);
            UtilsC.blockPassSet.add((byte) 30);
            UtilsC.blockPassSet.add((byte) 31);
            UtilsC.blockPassSet.add((byte) 32);
            UtilsC.blockPassSet.add((byte) 37);
            UtilsC.blockPassSet.add((byte) 38);
            UtilsC.blockPassSet.add((byte) 39);
            UtilsC.blockPassSet.add((byte) 40);
            UtilsC.blockPassSet.add((byte) 50);
            UtilsC.blockPassSet.add((byte) 51);
            UtilsC.blockPassSet.add((byte) 55);
            UtilsC.blockPassSet.add((byte) 59);
            UtilsC.blockPassSet.add((byte) 63);
            UtilsC.blockPassSet.add((byte) 66);
            UtilsC.blockPassSet.add((byte) 68);
            UtilsC.blockPassSet.add((byte) 69);
            UtilsC.blockPassSet.add((byte) 70);
            UtilsC.blockPassSet.add((byte) 72);
            UtilsC.blockPassSet.add((byte) 75);
            UtilsC.blockPassSet.add((byte) 76);
            UtilsC.blockPassSet.add((byte) 77);
            UtilsC.blockPassSet.add((byte) 78);
            UtilsC.blockPassSet.add((byte) 83);
            UtilsC.blockPassSet.add((byte) 90);
            UtilsC.blockPassSet.add((byte) 104);
            UtilsC.blockPassSet.add((byte) 105);
            UtilsC.blockPassSet.add((byte) 115);
            UtilsC.blockPassSet.add((byte) 119);
            UtilsC.blockPassSet.add((byte) (-124));
            UtilsC.blockPassSet.add((byte) (-113));
            UtilsC.blockPassSet.add((byte) (-81));
            UtilsC.blockPassSet.add((byte) (-85));
        }
        return !UtilsC.blockPassSet.contains(block);
    }


    public static boolean containsBlockType(Material[] arrmaterial, Block block) {
        Material[] arrmaterial2 = arrmaterial;
        int n = arrmaterial2.length;
        int n2 = 0;
        while (n2 < n) {
            Material material = arrmaterial2[n2];
            if (material == block.getType()) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public static boolean isLiquid(Block block) {
        if (block != null && (block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER || block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA)) {
            return true;
        }
        return false;
    }

    public static boolean isSolid(Block block) {
        if (block != null && UtilsC.isSolid(block.getTypeId())) {
            return true;
        }
        return false;
    }

    public static boolean isSolid(int n) {
        return UtilsC.isSolid((byte)n);
    }
    private static Logger log = Logger.getLogger("BoxUtils");
    private static HashMap<String, Class<?>> classCache = new HashMap(128);
    private static HashMap<String, Field> fieldCache = new HashMap(128);
    private static HashMap<String, Method> methodCache = new HashMap(128);
    private static HashMap<String, Constructor> constructorCache = new HashMap(128);
    private static String obcPrefix = null;
    private static String nmsPrefix = null;
    
    public static boolean isOnGround(Location location, int n) {
        double d = location.getX();
        double d2 = location.getZ();
        double d3 = UtilsC.getFraction(d) > 0.0 ? Math.abs(UtilsC.getFraction(d)) : 1.0 - Math.abs(UtilsC.getFraction(d));
        double d4 = UtilsC.getFraction(d2) > 0.0 ? Math.abs(UtilsC.getFraction(d2)) : 1.0 - Math.abs(UtilsC.getFraction(d2));
        int n2 = location.getBlockX();
        int n3 = location.getBlockY() - n;
        int n4 = location.getBlockZ();
        World world = location.getWorld();
        if (UtilsC.isSolid(world.getBlockAt(n2, n3, n4))) {
            return true;
        }
        if (d3 < 0.3) {
            if (UtilsC.isSolid(world.getBlockAt(n2 - 1, n3, n4))) {
                return true;
            }
            if (d4 < 0.3) {
                if (UtilsC.isSolid(world.getBlockAt(n2 - 1, n3, n4 - 1))) {
                    return true;
                }
                if (UtilsC.isSolid(world.getBlockAt(n2, n3, n4 - 1))) {
                    return true;
                }
                if (UtilsC.isSolid(world.getBlockAt(n2 + 1, n3, n4 - 1))) {
                    return true;
                }
            } else if (d4 > 0.7) {
                if (UtilsC.isSolid(world.getBlockAt(n2 - 1, n3, n4 + 1))) {
                    return true;
                }
                if (UtilsC.isSolid(world.getBlockAt(n2, n3, n4 + 1))) {
                    return true;
                }
                if (UtilsC.isSolid(world.getBlockAt(n2 + 1, n3, n4 + 1))) {
                    return true;
                }
            }
        } else if (d3 > 0.7) {
            if (UtilsC.isSolid(world.getBlockAt(n2 + 1, n3, n4))) {
                return true;
            }
            if (d4 < 0.3) {
                if (UtilsC.isSolid(world.getBlockAt(n2 - 1, n3, n4 - 1))) {
                    return true;
                }
                if (UtilsC.isSolid(world.getBlockAt(n2, n3, n4 - 1))) {
                    return true;
                }
                if (UtilsC.isSolid(world.getBlockAt(n2 + 1, n3, n4 - 1))) {
                    return true;
                }
            } else if (d4 > 0.7) {
                if (UtilsC.isSolid(world.getBlockAt(n2 - 1, n3, n4 + 1))) {
                    return true;
                }
                if (UtilsC.isSolid(world.getBlockAt(n2, n3, n4 + 1))) {
                    return true;
                }
                if (UtilsC.isSolid(world.getBlockAt(n2 + 1, n3, n4 + 1))) {
                    return true;
                }
            }
        } else if (d4 < 0.3 ? UtilsC.isSolid(world.getBlockAt(n2, n3, n4 - 1)) : d4 > 0.7 && UtilsC.isSolid(world.getBlockAt(n2, n3, n4 + 1))) {
            return true;
        }
        return false;
    }

    public static boolean isOnGround(Player player, int n) {
        return UtilsC.isOnGround(player.getLocation(), n);
    }

    public static boolean isOnBlock(Location location, int n, Material[] arrmaterial) {
        double d = location.getX();
        double d2 = location.getZ();
        double d3 = UtilsC.getFraction(d) > 0.0 ? Math.abs(UtilsC.getFraction(d)) : 1.0 - Math.abs(UtilsC.getFraction(d));
        double d4 = UtilsC.getFraction(d2) > 0.0 ? Math.abs(UtilsC.getFraction(d2)) : 1.0 - Math.abs(UtilsC.getFraction(d2));
        int n2 = location.getBlockX();
        int n3 = location.getBlockY() - n;
        int n4 = location.getBlockZ();
        World world = location.getWorld();
        if (UtilsC.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4))) {
            return true;
        }
        if (d3 < 0.3) {
            if (UtilsC.containsBlockType(arrmaterial, world.getBlockAt(n2 - 1, n3, n4))) {
                return true;
            }
            if (d4 < 0.3) {
                if (UtilsC.containsBlockType(arrmaterial, world.getBlockAt(n2 - 1, n3, n4 - 1))) {
                    return true;
                }
                if (UtilsC.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 - 1))) {
                    return true;
                }
                if (UtilsC.containsBlockType(arrmaterial, world.getBlockAt(n2 + 1, n3, n4 - 1))) {
                    return true;
                }
            } else if (d4 > 0.7) {
                if (UtilsC.containsBlockType(arrmaterial, world.getBlockAt(n2 - 1, n3, n4 + 1))) {
                    return true;
                }
                if (UtilsC.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 + 1))) {
                    return true;
                }
                if (UtilsC.containsBlockType(arrmaterial, world.getBlockAt(n2 + 1, n3, n4 + 1))) {
                    return true;
                }
            }
        } else if (d3 > 0.7) {
            if (UtilsC.containsBlockType(arrmaterial, world.getBlockAt(n2 + 1, n3, n4))) {
                return true;
            }
            if (d4 < 0.3) {
                if (UtilsC.containsBlockType(arrmaterial, world.getBlockAt(n2 - 1, n3, n4 - 1))) {
                    return true;
                }
                if (UtilsC.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 - 1))) {
                    return true;
                }
                if (UtilsC.containsBlockType(arrmaterial, world.getBlockAt(n2 + 1, n3, n4 - 1))) {
                    return true;
                }
            } else if (d4 > 0.7) {
                if (UtilsC.containsBlockType(arrmaterial, world.getBlockAt(n2 - 1, n3, n4 + 1))) {
                    return true;
                }
                if (UtilsC.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 + 1))) {
                    return true;
                }
                if (UtilsC.containsBlockType(arrmaterial, world.getBlockAt(n2 + 1, n3, n4 + 1))) {
                    return true;
                }
            }
        } else if (d4 < 0.3 ? UtilsC.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 - 1)) : d4 > 0.7 && UtilsC.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 + 1))) {
            return true;
        }
        return false;
    }

    public static boolean isOnBlock(Player player, int n, Material[] arrmaterial) {
        return UtilsC.isOnBlock(player.getLocation(), n, arrmaterial);
    }

    public static boolean isHoveringOverWater(Location location, int n) {
        double d = location.getX();
        double d2 = location.getZ();
        double d3 = UtilsC.getFraction(d) > 0.0 ? Math.abs(UtilsC.getFraction(d)) : 1.0 - Math.abs(UtilsC.getFraction(d));
        double d4 = UtilsC.getFraction(d2) > 0.0 ? Math.abs(UtilsC.getFraction(d2)) : 1.0 - Math.abs(UtilsC.getFraction(d2));
        int n2 = location.getBlockX();
        int n3 = location.getBlockY() - n;
        int n4 = location.getBlockZ();
        World world = location.getWorld();
        if (UtilsC.isLiquid(world.getBlockAt(n2, n3, n4))) {
            return true;
        }
        if (d3 < 0.3) {
            if (UtilsC.isLiquid(world.getBlockAt(n2 - 1, n3, n4))) {
                return true;
            }
            if (d4 < 0.3) {
                if (UtilsC.isLiquid(world.getBlockAt(n2 - 1, n3, n4 - 1))) {
                    return true;
                }
                if (UtilsC.isLiquid(world.getBlockAt(n2, n3, n4 - 1))) {
                    return true;
                }
                if (UtilsC.isLiquid(world.getBlockAt(n2 + 1, n3, n4 - 1))) {
                    return true;
                }
            } else if (d4 > 0.7) {
                if (UtilsC.isLiquid(world.getBlockAt(n2 - 1, n3, n4 + 1))) {
                    return true;
                }
                if (UtilsC.isLiquid(world.getBlockAt(n2, n3, n4 + 1))) {
                    return true;
                }
                if (UtilsC.isLiquid(world.getBlockAt(n2 + 1, n3, n4 + 1))) {
                    return true;
                }
            }
        } else if (d3 > 0.7) {
            if (UtilsC.isLiquid(world.getBlockAt(n2 + 1, n3, n4))) {
                return true;
            }
            if (d4 < 0.3) {
                if (UtilsC.isLiquid(world.getBlockAt(n2 - 1, n3, n4 - 1))) {
                    return true;
                }
                if (UtilsC.isLiquid(world.getBlockAt(n2, n3, n4 - 1))) {
                    return true;
                }
                if (UtilsC.isLiquid(world.getBlockAt(n2 + 1, n3, n4 - 1))) {
                    return true;
                }
            } else if (d4 > 0.7) {
                if (UtilsC.isLiquid(world.getBlockAt(n2 - 1, n3, n4 + 1))) {
                    return true;
                }
                if (UtilsC.isLiquid(world.getBlockAt(n2, n3, n4 + 1))) {
                    return true;
                }
                if (UtilsC.isLiquid(world.getBlockAt(n2 + 1, n3, n4 + 1))) {
                    return true;
                }
            }
        } else if (d4 < 0.3 ? UtilsC.isLiquid(world.getBlockAt(n2, n3, n4 - 1)) : d4 > 0.7 && UtilsC.isLiquid(world.getBlockAt(n2, n3, n4 + 1))) {
            return true;
        }
        return false;
    }

    public static boolean isHoveringOverWater(Player player, int n) {
        return UtilsC.isHoveringOverWater(player.getLocation(), n);
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