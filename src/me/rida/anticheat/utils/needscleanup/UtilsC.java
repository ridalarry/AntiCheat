
package me.rida.anticheat.utils.needscleanup;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class UtilsC {



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


    public static ArrayList<Block> getSurrounding(Block block, boolean bl) {
        ArrayList<Block> arrayList = new ArrayList<Block>();
        if (bl) {
            int n = -1;
            while (n <= 1) {
                int n2 = -1;
                while (n2 <= 1) {
                    int n3 = -1;
                    while (n3 <= 1) {
                        if (n != 0 || n2 != 0 || n3 != 0) {
                            arrayList.add(block.getRelative(n, n2, n3));
                        }
                        ++n3;
                    }
                    ++n2;
                }
                ++n;
            }
        } else {
            arrayList.add(block.getRelative(BlockFace.UP));
            arrayList.add(block.getRelative(BlockFace.DOWN));
            arrayList.add(block.getRelative(BlockFace.NORTH));
            arrayList.add(block.getRelative(BlockFace.SOUTH));
            arrayList.add(block.getRelative(BlockFace.EAST));
            arrayList.add(block.getRelative(BlockFace.WEST));
        }
        return arrayList;
    }

    private static Logger log = Logger.getLogger("BoxUtils");
    private static HashMap<String, Class<?>> classCache = new HashMap(128);
    private static HashMap<String, Field> fieldCache = new HashMap(128);
    private static HashMap<String, Method> methodCache = new HashMap(128);
    private static HashMap<String, Constructor> constructorCache = new HashMap(128);
    private static String obcPrefix = null;
    private static String nmsPrefix = null;

    static {
        try {
            nmsPrefix = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
            obcPrefix = "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        }
        catch (Exception exception) {
            nmsPrefix = "net.minecraft.server.";
            obcPrefix = "org.bukkit.craftbukkit.";
        }
    }
    public static Class<?> getNMSClass(String string) {
        return UtilsC.getClass(String.valueOf(nmsPrefix) + string);
    }

    public static Class<?> getClass(String string) {
        Validate.notNull((Object)string);
        if (classCache.containsKey(string)) {
            return classCache.get(string);
        }
        Class class_ = null;
        try {
            class_ = Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            log.log(Level.SEVERE, "[Reflection] Unable to find the the class " + string);
        }
        if (class_ != null) {
            classCache.put(string, class_);
        }
        return class_;
    }

    public static Field getField(String string, Class<?> class_) {
        Validate.notNull((Object)string);
        Validate.notNull(class_);
        String string2 = String.valueOf(class_.getCanonicalName()) + "@" + string;
        if (fieldCache.containsKey(string2)) {
            return fieldCache.get(string2);
        }
        Field field = null;
        try {
            field = class_.getField(string);
        }
        catch (NoSuchFieldException noSuchFieldException) {
            log.log(Level.SEVERE, "[Reflection] Unable to find the the field " + string + " in class " + class_.getSimpleName());
        }
        if (field != null) {
            fieldCache.put(string2, field);
        }
        return field;
    }

    public static Method getMethod(String string, Class<?> class_) {
        Validate.notNull((Object)string);
        Validate.notNull(class_);
        String string2 = String.valueOf(class_.getCanonicalName()) + "@" + string;
        if (methodCache.containsKey(string2)) {
            return methodCache.get(string2);
        }
        Method method = null;
        try {
            method = class_.getMethod(string, new Class[0]);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            log.log(Level.SEVERE, "[Reflection] Unable to find the the method " + string + " in class " + class_.getSimpleName());
        }
        if (method != null) {
            methodCache.put(string2, method);
        }
        return method;
    }
    
    public static Object getEntityHandle(Entity entity) {
        try {
            Method method = UtilsC.getMethod("getHandle", entity.getClass());
            return method.invoke((Object)entity, new Object[0]);
        }
        catch (Exception exception) {
            log.log(Level.SEVERE, "[Reflection] Unable to getHandle of " + (Object)entity.getType());
            return null;
        }
    }

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


    public static int getPing(Player player) {
        Object object = UtilsC.getEntityHandle((Entity)player);
        if (object != null) {
            Field field = UtilsC.getField("ping", object.getClass());
            try {
                return field.getInt(object);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return 150;
    }
    public static double getFraction(double d) {
        return d % 1.0;
    }

}