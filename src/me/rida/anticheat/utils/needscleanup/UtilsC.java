/*
 * Decompiled with CFR 0_122.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
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
    public static HashSet<Byte> blockPassSet = new HashSet();
    public static HashSet<Byte> blockAirFoliageSet = new HashSet();
    public static HashSet<Byte> fullSolid = new HashSet();
    public static HashSet<Byte> blockUseSet = new HashSet();

    public static Block getLowestBlockAt(Location location) {
        Block block = location.getWorld().getBlockAt((int)location.getX(), 0, (int)location.getZ());
        if (block == null || block.getType().equals((Object)Material.AIR)) {
            block = location.getBlock();
            int n = (int)location.getY();
            while (n > 0) {
                Block block2 = location.getWorld().getBlockAt((int)location.getX(), n, (int)location.getZ());
                Block block3 = block2.getLocation().subtract(0.0, 1.0, 0.0).getBlock();
                if (block3 == null || block3.getType().equals((Object)Material.AIR)) {
                    block = block2;
                }
                --n;
            }
        }
        return block;
    }

    public static boolean containsBlock(Location location, Material material) {
        int n = 0;
        while (n < 256) {
            Block block = location.getWorld().getBlockAt((int)location.getX(), n, (int)location.getZ());
            if (block != null && block.getType().equals((Object)material)) {
                return true;
            }
            ++n;
        }
        return false;
    }

    public static boolean containsBlock(Location location) {
        int n = 0;
        while (n < 256) {
            Block block = location.getWorld().getBlockAt((int)location.getX(), n, (int)location.getZ());
            if (block != null && !block.getType().equals((Object)Material.AIR)) {
                return true;
            }
            ++n;
        }
        return false;
    }

    public static boolean containsBlockBelow(Location location) {
        int n = 0;
        while (n < (int)location.getY()) {
            Block block = location.getWorld().getBlockAt((int)location.getX(), n, (int)location.getZ());
            if (block != null && !block.getType().equals((Object)Material.AIR)) {
                return true;
            }
            ++n;
        }
        return false;
    }

    public static ArrayList<Block> getBlocksAroundCenter(Location location, int n) {
        ArrayList<Block> arrayList = new ArrayList<Block>();
        int n2 = location.getBlockX() - n;
        while (n2 <= location.getBlockX() + n) {
            int n3 = location.getBlockY() - n;
            while (n3 <= location.getBlockY() + n) {
                int n4 = location.getBlockZ() - n;
                while (n4 <= location.getBlockZ() + n) {
                    Location location2 = new Location(location.getWorld(), (double)n2, (double)n3, (double)n4);
                    if (location2.distance(location) <= (double)n) {
                        arrayList.add(location2.getBlock());
                    }
                    ++n4;
                }
                ++n3;
            }
            ++n2;
        }
        return arrayList;
    }

    public static Location stringToLocation(String string) {
        String[] arrstring = string.split(",");
        World world = Bukkit.getWorld((String)arrstring[0]);
        double d = Double.parseDouble(arrstring[1]);
        double d2 = Double.parseDouble(arrstring[2]);
        double d3 = Double.parseDouble(arrstring[3]);
        float f = Float.parseFloat(arrstring[4]);
        float f2 = Float.parseFloat(arrstring[5]);
        return new Location(world, d, d2, d3, f, f2);
    }

    public static String LocationToString(Location location) {
        return String.valueOf(String.valueOf(location.getWorld().getName())) + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getPitch() + "," + location.getYaw();
    }

    public static boolean isStair(Block block) {
        String string = block.getType().name().toLowerCase();
        if (!(string.contains("stair") || string.contains("_step") || string.equals("step"))) {
            return false;
        }
        return true;
    }

    public static boolean isWeb(Block block) {
        if (block.getType() == Material.WEB) {
            return true;
        }
        return false;
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

    public static boolean isIce(Block block) {
        if (block != null && (block.getType() == Material.ICE || block.getType() == Material.PACKED_ICE)) {
            return true;
        }
        return false;
    }

    public static boolean isAny(Block block, Material[] arrmaterial) {
        Material[] arrmaterial2 = arrmaterial;
        int n = arrmaterial2.length;
        int n2 = 0;
        while (n2 < n) {
            Material material = arrmaterial2[n2];
            if (block.getType().equals((Object)material)) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public static boolean isSolid(int n) {
        return UtilsC.isSolid((byte)n);
    }


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

    public static boolean airFoliage(Block block) {
        if (block != null && UtilsC.airFoliage(block.getTypeId())) {
            return true;
        }
        return false;
    }

    public static boolean airFoliage(int n) {
        return UtilsC.airFoliage((byte)n);
    }

    public static boolean airFoliage(byte block) {
        if (blockAirFoliageSet.isEmpty()) {
            blockAirFoliageSet.add((byte) 0);
            blockAirFoliageSet.add((byte) 6);
            blockAirFoliageSet.add((byte) 31);
            blockAirFoliageSet.add((byte) 32);
            blockAirFoliageSet.add((byte) 37);
            blockAirFoliageSet.add((byte) 38);
            blockAirFoliageSet.add((byte) 39);
            blockAirFoliageSet.add((byte) 40);
            blockAirFoliageSet.add((byte) 51);
            blockAirFoliageSet.add((byte) 59);
            blockAirFoliageSet.add((byte) 104);
            blockAirFoliageSet.add((byte) 105);
            blockAirFoliageSet.add((byte) 115);
            blockAirFoliageSet.add((byte) -115);
            blockAirFoliageSet.add((byte) -114);
        }
        return blockAirFoliageSet.contains(block);
    }

    public static boolean fullSolid(Block block) {
        if (block != null && UtilsC.fullSolid(block.getTypeId())) {
            return true;
        }
        return false;
    }

    public static boolean fullSolid(int n) {
        return UtilsC.fullSolid((byte)n);
    }

    public static boolean fullSolid(byte block) {
        if (fullSolid.isEmpty()) {
            fullSolid.add((byte) 1);
            fullSolid.add((byte) 2);
            fullSolid.add((byte) 3);
            fullSolid.add((byte) 4);
            fullSolid.add((byte) 5);
            fullSolid.add((byte) 7);
            fullSolid.add((byte) 12);
            fullSolid.add((byte) 13);
            fullSolid.add((byte) 14);
            fullSolid.add((byte) 15);
            fullSolid.add((byte) 16);
            fullSolid.add((byte) 17);
            fullSolid.add((byte) 19);
            fullSolid.add((byte) 20);
            fullSolid.add((byte) 21);
            fullSolid.add((byte) 22);
            fullSolid.add((byte) 23);
            fullSolid.add((byte) 24);
            fullSolid.add((byte) 25);
            fullSolid.add((byte) 29);
            fullSolid.add((byte) 33);
            fullSolid.add((byte) 35);
            fullSolid.add((byte) 41);
            fullSolid.add((byte) 42);
            fullSolid.add((byte) 43);
            fullSolid.add((byte) 44);
            fullSolid.add((byte) 45);
            fullSolid.add((byte) 46);
            fullSolid.add((byte) 47);
            fullSolid.add((byte) 48);
            fullSolid.add((byte) 49);
            fullSolid.add((byte) 56);
            fullSolid.add((byte) 57);
            fullSolid.add((byte) 58);
            fullSolid.add((byte) 60);
            fullSolid.add((byte) 61);
            fullSolid.add((byte) 62);
            fullSolid.add((byte) 73);
            fullSolid.add((byte) 74);
            fullSolid.add((byte) 79);
            fullSolid.add((byte) 80);
            fullSolid.add((byte) 82);
            fullSolid.add((byte) 84);
            fullSolid.add((byte) 86);
            fullSolid.add((byte) 87);
            fullSolid.add((byte) 88);
            fullSolid.add((byte) 89);
            fullSolid.add((byte) 91);
            fullSolid.add((byte) 95);
            fullSolid.add((byte) 97);
            fullSolid.add((byte) 98);
            fullSolid.add((byte) 99);
            fullSolid.add((byte) 100);
            fullSolid.add((byte) 103);
            fullSolid.add((byte) 110);
            fullSolid.add((byte) 112);
            fullSolid.add((byte) 121);
            fullSolid.add((byte) 123);
            fullSolid.add((byte) 124);
            fullSolid.add((byte) 125);
            fullSolid.add((byte) 126);
            fullSolid.add((byte) -127);
            fullSolid.add((byte) -123);
            fullSolid.add((byte) -119);
            fullSolid.add((byte) -118);
            fullSolid.add((byte) -104);
            fullSolid.add((byte) -103);
            fullSolid.add((byte) -101);
            fullSolid.add((byte) -98);
        }
        return fullSolid.contains(block);
    }

    public static boolean usable(Block block) {
        if (block != null && UtilsC.usable(block.getTypeId())) {
            return true;
        }
        return false;
    }

    public static boolean usable(int n) {
        return UtilsC.usable((byte)n);
    }

    public static boolean usable(byte block) {
        if (blockUseSet.isEmpty()) {
            blockUseSet.add((byte) 23);
            blockUseSet.add((byte) 26);
            blockUseSet.add((byte) 33);
            blockUseSet.add((byte) 47);
            blockUseSet.add((byte) 54);
            blockUseSet.add((byte) 58);
            blockUseSet.add((byte) 61);
            blockUseSet.add((byte) 62);
            blockUseSet.add((byte) 64);
            blockUseSet.add((byte) 69);
            blockUseSet.add((byte) 71);
            blockUseSet.add((byte) 77);
            blockUseSet.add((byte) 93);
            blockUseSet.add((byte) 94);
            blockUseSet.add((byte) 96);
            blockUseSet.add((byte) 107);
            blockUseSet.add((byte) 116);
            blockUseSet.add((byte) 117);
            blockUseSet.add((byte) -126);
            blockUseSet.add((byte) -111);
            blockUseSet.add((byte) -110);
            blockUseSet.add((byte) -102);
            blockUseSet.add((byte) -98);
        }
        return blockUseSet.contains(block);
    }

    public static HashMap<Block, Double> getInRadius(Location location, double d) {
        return UtilsC.getInRadius(location, d, 999.0);
    }

    public static HashMap<Block, Double> getInRadius(Location location, double d, double d2) {
        HashMap<Block, Double> hashMap = new HashMap<Block, Double>();
        int n = (int)d + 1;
        int n2 = - n;
        while (n2 <= n) {
            int n3 = - n;
            while (n3 <= n) {
                int n4 = - n;
                while (n4 <= n) {
                    double d3;
                    Block block;
                    if ((double)Math.abs(n4) <= d2 && (d3 = UtilsC.offset(location, (block = location.getWorld().getBlockAt((int)(location.getX() + (double)n2), (int)(location.getY() + (double)n4), (int)(location.getZ() + (double)n3))).getLocation().add(0.5, 0.5, 0.5))) <= d) {
                        hashMap.put(block, 1.0 - d3 / d);
                    }
                    ++n4;
                }
                ++n3;
            }
            ++n2;
        }
        return hashMap;
    }

    public static HashMap<Block, Double> getInRadius(Block block, double d) {
        HashMap<Block, Double> hashMap = new HashMap<Block, Double>();
        int n = (int)d + 1;
        int n2 = - n;
        while (n2 <= n) {
            int n3 = - n;
            while (n3 <= n) {
                int n4 = - n;
                while (n4 <= n) {
                    Block block2 = block.getRelative(n2, n4, n3);
                    double d2 = UtilsC.offset(block.getLocation(), block2.getLocation());
                    if (d2 <= d) {
                        hashMap.put(block2, 1.0 - d2 / d);
                    }
                    ++n4;
                }
                ++n3;
            }
            ++n2;
        }
        return hashMap;
    }

    public static boolean isBlock(ItemStack itemStack) {
        if (itemStack != null && itemStack.getTypeId() > 0 && itemStack.getTypeId() < 256) {
            return true;
        }
        return false;
    }

    public static Block getHighest(Location location) {
        return UtilsC.getHighest(location, null);
    }

    public static Block getHighest(Location location, HashSet<Material> hashSet) {
        location.setY(0.0);
        int n = 0;
        while (n < 256) {
            location.setY((double)(256 - n));
            if (UtilsC.isSolid(location.getBlock())) break;
            ++n;
        }
        return location.getBlock().getRelative(BlockFace.UP);
    }

    public static boolean isInAir(Player player) {
        boolean bl = false;
        for (Block block : UtilsC.getSurrounding(player.getLocation().getBlock(), true)) {
            if (block.getType() == Material.AIR) continue;
            bl = true;
            break;
        }
        return bl;
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

    public static ArrayList<Block> getSurroundingXZ(Block block) {
        ArrayList<Block> arrayList = new ArrayList<Block>();
        arrayList.add(block.getRelative(BlockFace.NORTH));
        arrayList.add(block.getRelative(BlockFace.NORTH_EAST));
        arrayList.add(block.getRelative(BlockFace.NORTH_WEST));
        arrayList.add(block.getRelative(BlockFace.SOUTH));
        arrayList.add(block.getRelative(BlockFace.SOUTH_EAST));
        arrayList.add(block.getRelative(BlockFace.SOUTH_WEST));
        arrayList.add(block.getRelative(BlockFace.EAST));
        arrayList.add(block.getRelative(BlockFace.WEST));
        return arrayList;
    }

    public static String serializeLocation(Location location) {
        int n = (int)location.getX();
        int n2 = (int)location.getY();
        int n3 = (int)location.getZ();
        int n4 = (int)location.getPitch();
        int n5 = (int)location.getYaw();
        return new String(String.valueOf(String.valueOf(location.getWorld().getName())) + "," + n + "," + n2 + "," + n3 + "," + n4 + "," + n5);
    }

    public static Location deserializeLocation(String string) {
        if (string == null) {
            return null;
        }
        String[] arrstring = string.split(",");
        World world = Bukkit.getServer().getWorld(arrstring[0]);
        Double d = Double.parseDouble(arrstring[1]);
        Double d2 = Double.parseDouble(arrstring[2]);
        Double d3 = Double.parseDouble(arrstring[3]);
        Float f = Float.valueOf(Float.parseFloat(arrstring[4]));
        Float f2 = Float.valueOf(Float.parseFloat(arrstring[5]));
        Location location = new Location(world, d.doubleValue(), d2.doubleValue(), d3.doubleValue());
        location.setPitch(f.floatValue());
        location.setYaw(f2.floatValue());
        return location;
    }

    public static boolean isVisible(Block block) {
        for (Block block2 : UtilsC.getSurrounding(block, false)) {
            if (block2.getType().isOccluding()) continue;
            return true;
        }
        return false;
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


    public static Class<?> getCraftBukkitClass(String string) {
        return UtilsC.getClass(String.valueOf(obcPrefix) + string);
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

    public static Method getMethod(Class<?> class_, String string, Class<?>[] arrclass) {
        Validate.notNull((Object)string);
        Validate.notNull(class_);
        String string2 = String.valueOf(class_.getCanonicalName()) + "@" + string;
        if (methodCache.containsKey(string2)) {
            return methodCache.get(string2);
        }
        Method method = null;
        try {
            method = class_.getMethod(string, arrclass);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            log.log(Level.SEVERE, "[Reflection] Unable to find the the method " + string + " in class " + class_.getSimpleName());
        }
        if (method != null) {
            methodCache.put(string2, method);
        }
        return method;
    }

    public static Method getMethod(String string, Class<?> class_, Class<?>[] arrclass) {
        Validate.notNull((Object)string);
        Validate.notNull(class_);
        String string2 = String.valueOf(class_.getCanonicalName()) + "@" + string;
        if (methodCache.containsKey(string2)) {
            return methodCache.get(string2);
        }
        Method method = null;
        try {
            method = class_.getMethod(string, arrclass);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            log.log(Level.SEVERE, "[Reflection] Unable to find the the method " + string + " in class " + class_.getSimpleName());
        }
        if (method != null) {
            methodCache.put(string2, method);
        }
        return method;
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

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    
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

    public static void sendPacket(Player player, Object object) {
        Object object2 = null;
        try {
            object2 = UtilsC.getEntityHandle((Entity)player);
            Object object3 = object2.getClass().getField("playerConnection").get(object2);
            UtilsC.getMethod("sendPacket", object3.getClass()).invoke(object3, object);
        }
        catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
        catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
        }
        catch (NoSuchFieldException noSuchFieldException) {
            noSuchFieldException.printStackTrace();
        }
    }
    public static void clear(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
        player.getInventory().clear();
        player.setSprinting(false);
        player.setFoodLevel(20);
        player.setSaturation(3.0f);
        player.setExhaustion(0.0f);
        player.setMaxHealth(20.0);
        player.setHealth(player.getMaxHealth());
        player.setFireTicks(0);
        player.setFallDistance(0.0f);
        player.setLevel(0);
        player.setExp(0.0f);
        player.setWalkSpeed(0.2f);
        player.setFlySpeed(0.1f);
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.updateInventory();
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }
    }

    public static Location getEyeLocation(Player player) {
        Location location = player.getLocation();
        location.setY(location.getY() + player.getEyeHeight());
        return location;
    }

    public static boolean isOnClimbable(Player player) {
        for (Block block : UtilsC.getSurrounding(player.getLocation().getBlock(), false)) {
            if (block.getType() != Material.LADDER && block.getType() != Material.VINE) continue;
            return true;
        }
        if (player.getLocation().getBlock().getType() != Material.LADDER && player.getLocation().getBlock().getType() != Material.VINE) {
            return false;
        }
        return true;
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

    public static boolean isOnStairs(Location location, int n) {
        double d = location.getX();
        double d2 = location.getZ();
        double d3 = UtilsC.getFraction(d) > 0.0 ? Math.abs(UtilsC.getFraction(d)) : 1.0 - Math.abs(UtilsC.getFraction(d));
        double d4 = UtilsC.getFraction(d2) > 0.0 ? Math.abs(UtilsC.getFraction(d2)) : 1.0 - Math.abs(UtilsC.getFraction(d2));
        int n2 = location.getBlockX();
        int n3 = location.getBlockY() - n;
        int n4 = location.getBlockZ();
        World world = location.getWorld();
        if (UtilsC.isStair(world.getBlockAt(n2, n3, n4))) {
            return true;
        }
        if (d3 < 0.3) {
            if (UtilsC.isStair(world.getBlockAt(n2 - 1, n3, n4))) {
                return true;
            }
            if (d4 < 0.3) {
                if (UtilsC.isStair(world.getBlockAt(n2 - 1, n3, n4 - 1))) {
                    return true;
                }
                if (UtilsC.isStair(world.getBlockAt(n2, n3, n4 - 1))) {
                    return true;
                }
                if (UtilsC.isStair(world.getBlockAt(n2 + 1, n3, n4 - 1))) {
                    return true;
                }
            } else if (d4 > 0.7) {
                if (UtilsC.isStair(world.getBlockAt(n2 - 1, n3, n4 + 1))) {
                    return true;
                }
                if (UtilsC.isStair(world.getBlockAt(n2, n3, n4 + 1))) {
                    return true;
                }
                if (UtilsC.isStair(world.getBlockAt(n2 + 1, n3, n4 + 1))) {
                    return true;
                }
            }
        } else if (d3 > 0.7) {
            if (UtilsC.isStair(world.getBlockAt(n2 + 1, n3, n4))) {
                return true;
            }
            if (d4 < 0.3) {
                if (UtilsC.isStair(world.getBlockAt(n2 - 1, n3, n4 - 1))) {
                    return true;
                }
                if (UtilsC.isStair(world.getBlockAt(n2, n3, n4 - 1))) {
                    return true;
                }
                if (UtilsC.isStair(world.getBlockAt(n2 + 1, n3, n4 - 1))) {
                    return true;
                }
            } else if (d4 > 0.7) {
                if (UtilsC.isStair(world.getBlockAt(n2 - 1, n3, n4 + 1))) {
                    return true;
                }
                if (UtilsC.isStair(world.getBlockAt(n2, n3, n4 + 1))) {
                    return true;
                }
                if (UtilsC.isStair(world.getBlockAt(n2 + 1, n3, n4 + 1))) {
                    return true;
                }
            }
        } else if (d4 < 0.3 ? UtilsC.isStair(world.getBlockAt(n2, n3, n4 - 1)) : d4 > 0.7 && UtilsC.isStair(world.getBlockAt(n2, n3, n4 + 1))) {
            return true;
        }
        return false;
    }

    public static boolean isOnStairs(Player player, int n) {
        return UtilsC.isOnStairs(player.getLocation(), n);
    }
    private static Object MC_SERVER_OBJ = null;
    private static Field MC_SERVER_TPS_FIELD = null;

    public static List<Entity> getEntities(World world) {
        return new ArrayList<Entity>(world.getEntities());
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

    public static double[] getTps() {
        if (MC_SERVER_OBJ == null) {
            try {
                MC_SERVER_OBJ = UtilsC.getMethod("getServer", UtilsC.getNMSClass("MinecraftServer")).invoke(null, new Object[0]);
            }
            catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
                reflectiveOperationException.printStackTrace();
            }
            MC_SERVER_TPS_FIELD = UtilsC.getField("recentTps", UtilsC.getNMSClass("MinecraftServer"));
        }
        try {
            return (double[])MC_SERVER_TPS_FIELD.get(MC_SERVER_OBJ);
        }
        catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
            return null;
        }
    }

    public static double[] getRoundedTps() {
        double[] arrd = UtilsC.getTps();
        int n = 0;
        while (n < arrd.length) {
            double d = arrd[n];
            arrd[n] = d = (double)Math.round(d * 100.0) / 100.0;
            ++n;
        }
        return arrd;
    }

    public static Random random = new Random();

    public static double getFraction(double d) {
        return d % 1.0;
    }

    public static double round(double d, int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bigDecimal = new BigDecimal(d);
        bigDecimal = bigDecimal.setScale(n, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static double trim(int n, double d) {
        String string = "#.#";
        int n2 = 1;
        while (n2 < n) {
            string = String.valueOf(String.valueOf(string)) + "#";
            ++n2;
        }
        DecimalFormat decimalFormat = new DecimalFormat(string);
        return Double.valueOf(decimalFormat.format(d));
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

    public static double getXZDistance(Location location, Location location2) {
        double d = Math.abs(Math.abs(location.getX()) - Math.abs(location2.getX()));
        double d2 = Math.abs(Math.abs(location.getZ()) - Math.abs(location2.getZ()));
        return Math.sqrt(d * d + d2 * d2);
    }

    public static int r(int n) {
        return random.nextInt(n);
    }

    public static double abs(double d) {
        return d <= 0.0 ? 0.0 - d : d;
    }

    public static float getYawDifference(Location location, Location location2) {
        float f = UtilsC.getYaw(location);
        float f2 = UtilsC.getYaw(location2);
        float f3 = Math.abs(f - f2);
        if (f < 90.0f && f2 > 270.0f || f2 < 90.0f && f > 270.0f) {
            f3 -= 360.0f;
        }
        return Math.abs(f3);
    }

    public static float getYaw(Location location) {
        float f = (location.getYaw() - 90.0f) % 360.0f;
        if (f < 0.0f) {
            f += 360.0f;
        }
        return f;
    }

    public static String arrayToString(String[] arrstring) {
        String string = "";
        String[] arrstring2 = arrstring;
        int n = arrstring2.length;
        int n2 = 0;
        while (n2 < n) {
            String string2 = arrstring2[n2];
            string = String.valueOf(String.valueOf(string)) + string2 + ",";
            ++n2;
        }
        if (string.length() != 0) {
            return string.substring(0, string.length() - 1);
        }
        return null;
    }

    public static String arrayToString(List<String> list) {
        String string = "";
        for (String string2 : list) {
            string = String.valueOf(String.valueOf(string)) + string2 + ",";
        }
        if (string.length() != 0) {
            return string.substring(0, string.length() - 1);
        }
        return null;
    }

    public static String[] stringtoArray(String string, String string2) {
        return string.split(string2);
    }

    public static double offset2d(Entity entity, Entity entity2) {
        return UtilsC.offset2d(entity.getLocation().toVector(), entity2.getLocation().toVector());
    }

    public static double offset2d(Location location, Location location2) {
        return UtilsC.offset2d(location.toVector(), location2.toVector());
    }

    public static double offset2d(Vector vector, Vector vector2) {
        vector.setY(0);
        vector2.setY(0);
        return vector.subtract(vector2).length();
    }

    public static double distanceXZSquared(Location location, Location location2) {
        double d = location.getX() - location2.getX();
        double d2 = location.getZ() - location2.getZ();
        return d * d + d2 * d2;
    }

    public static double offset(Entity entity, Entity entity2) {
        return UtilsC.offset(entity.getLocation().toVector(), entity2.getLocation().toVector());
    }

    public static double offset(Location location, Location location2) {
        return UtilsC.offset(location.toVector(), location2.toVector());
    }

    public static double offset(Vector vector, Vector vector2) {
        return vector.subtract(vector2).length();
    }

    public static Vector getHorizontalVector(Vector vector) {
        vector.setY(0);
        return vector;
    }

    public static Vector getVerticalVector(Vector vector) {
        vector.setX(0);
        vector.setZ(0);
        return vector;
    }

    

    public static long averageLong(List<Long> list) {
        long l = 0;
        for (Long l2 : list) {
            l += l2.longValue();
        }
        return l / (long)list.size();
    }

    public static double averageDouble(List<Double> list) {
        Double d = 0.0;
        for (Double d2 : list) {
            d = d + d2;
        }
        return d / (double)list.size();
    }

    public static float averageFloat(List<Float> list) {
        Float f = Float.valueOf(0.0f);
        for (Float f2 : list) {
            f = Float.valueOf(f.floatValue() + f2.floatValue());
        }
        return f.floatValue() / (float)list.size();
    }
}