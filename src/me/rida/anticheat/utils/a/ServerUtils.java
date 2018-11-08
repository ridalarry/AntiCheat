package me.rida.anticheat.utils.a;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.data.DataPlayer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class ServerUtils {

	private static final String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	public static final Class<?> EntityPlayer = ServerUtils.getNMSClass("EntityPlayer");
	public static final Class<?> Entity = ServerUtils.getNMSClass("Entity");
	private static final Class<?> CraftPlayer = ServerUtils.getCBClass("entity.CraftPlayer");
	private static final Class<?> CraftWorld = ServerUtils.getCBClass("CraftWorld");
	private static final Class<?> World = ServerUtils.getNMSClass("World");
	private static final Method getCubes = ServerUtils.getMethod(World, "a", ServerUtils.getNMSClass("AxisAlignedBB"));

	public static Object getEntityPlayer(Player player) {
		return ServerUtils.getMethodValue(ServerUtils.getMethod(CraftPlayer, "getHandle"), player);
	}

	public static Object getBoundingBox(Player player) {
		return ServerUtils.isBukkitVerison("1_7") ? ServerUtils.getFieldValue(ServerUtils.getFieldByName(Entity, "boundingBox"), getEntityPlayer(player)) : ServerUtils.getMethodValue(ServerUtils.getMethod(EntityPlayer, "getBoundingBox"), getEntityPlayer(player));
	}

	public static boolean isBukkitVerison(String version) {
		String bukkit = Bukkit.getServer().getClass().getPackage().getName().substring(23);
		
		return bukkit.contains(version);
	}

	public static Class<?> getCBClass(String string) {
		return getClass("org.bukkit.craftbukkit." + version + "." + string);
	}

	public static Class<?> getClass(String string) {
		try {
			return Class.forName(string);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static Field getFieldByName(Class<?> clazz, String fieldName) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean inBlock(Player player, Object axisAlignedBB) {
		Object world = ServerUtils.getMethodValue(ServerUtils.getMethod(CraftWorld, "getHandle"), player.getWorld());
		return ((Collection<?>)ServerUtils.getMethodValue(getCubes, world, axisAlignedBB)).size() > 0;
	}

	public static Collection<?> getCollidingBlocks(Player player, Object axisAlignedBB) {
		Object world = ServerUtils.getMethodValue(ServerUtils.getMethod(CraftWorld, "getHandle"), player.getWorld());
		return ((Collection<?>)ServerUtils.getMethodValue(getCubes, world, axisAlignedBB));
	}


	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... args) {
		try {
			Method method = clazz.getMethod(methodName, args);
			method.setAccessible(true);
			return method;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object getMethodValue(Method method, Object object, Object... args) {
		try {
			return method.invoke(object, args);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object getFieldValue(Field field, Object object) {
		try {
			field.setAccessible(true);
			return field.get(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Class<?> getNMSClass(String string) {
		return getClass("net.minecraft.server." + version + "." + string);
	}
	
    
}