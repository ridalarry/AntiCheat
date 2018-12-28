package me.rida.anticheat.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ServerUtil {

	private static final String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	public static final Class<?> EntityPlayer = getNMSClass("EntityPlayer");
	public static final Class<?> Entity = getNMSClass("Entity");
	private static final Class<?> CraftPlayer = getCBClass("entity.CraftPlayer");
	private static final Class<?> CraftWorld = getCBClass("CraftWorld");
	private static final Class<?> World = getNMSClass("World");
	private static final Method getCubes = getMethod(World, "a", getNMSClass("AxisAlignedBB"));
	public static Object getEntityPlayer(Player player) {
		return getMethodValue(getMethod(CraftPlayer, "getHandle"), player);
	}

	public static Object getBoundingBox(Player player) {
		return isBukkitVerison("1_7") ? getFieldValue(getFieldByName(Entity, "boundingBox"), getEntityPlayer(player)) : getMethodValue(getMethod(EntityPlayer, "getBoundingBox"), getEntityPlayer(player));
	}

	public static boolean isBukkitVerison(String version) {
		final String bukkit = Bukkit.getServer().getClass().getPackage().getName().substring(23);

		return bukkit.contains(version);
	}

	public static Class<?> getCBClass(String string) {
		return getClass("org.bukkit.craftbukkit." + version + "." + string);
	}

	public static Class<?> getClass(String string) {
		try {
			return Class.forName(string);
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static Field getFieldByName(Class<?> clazz, String fieldName) {
		try {
			final Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public static Collection<?> getCollidingBlocks(Player player, Object axisAlignedBB) {
		final Object world = getMethodValue(getMethod(CraftWorld, "getHandle"), player.getWorld());
		return ((Collection<?>)getMethodValue(getCubes, world, axisAlignedBB));
	}


	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... args) {
		try {
			final Method method = clazz.getMethod(methodName, args);
			method.setAccessible(true);
			return method;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object getMethodValue(Method method, Object object, Object... args) {
		try {
			return method.invoke(object, args);
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object getFieldValue(Field field, Object object) {
		try {
			field.setAccessible(true);
			return field.get(object);
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Class<?> getNMSClass(String string) {
		return getClass("net.minecraft.server." + version + "." + string);
	}



	public static boolean isOnGround(Location location, int n) {
		final double d = location.getX();
		final double d2 = location.getZ();
		final double d3 = MathUtil.getFraction(d) > 0.0 ? Math.abs(MathUtil.getFraction(d)) : 1.0 - Math.abs(MathUtil.getFraction(d));
		final double d4 = MathUtil.getFraction(d2) > 0.0 ? Math.abs(MathUtil.getFraction(d2)) : 1.0 - Math.abs(MathUtil.getFraction(d2));
		final int n2 = location.getBlockX();
		final int n3 = location.getBlockY() - n;
		final int n4 = location.getBlockZ();
		final World world = location.getWorld();
		if (BlockUtil.isSolid(world.getBlockAt(n2, n3, n4))) {
			return true;
		}
		if (d3 < 0.3) {
			if (BlockUtil.isSolid(world.getBlockAt(n2 - 1, n3, n4))) {
				return true;
			}
			if (d4 < 0.3) {
				if (BlockUtil.isSolid(world.getBlockAt(n2 - 1, n3, n4 - 1))) {
					return true;
				}
				if (BlockUtil.isSolid(world.getBlockAt(n2, n3, n4 - 1))) {
					return true;
				}
				if (BlockUtil.isSolid(world.getBlockAt(n2 + 1, n3, n4 - 1))) {
					return true;
				}
			} else if (d4 > 0.7) {
				if (BlockUtil.isSolid(world.getBlockAt(n2 - 1, n3, n4 + 1))) {
					return true;
				}
				if (BlockUtil.isSolid(world.getBlockAt(n2, n3, n4 + 1))) {
					return true;
				}
				if (BlockUtil.isSolid(world.getBlockAt(n2 + 1, n3, n4 + 1))) {
					return true;
				}
			}
		} else if (d3 > 0.7) {
			if (BlockUtil.isSolid(world.getBlockAt(n2 + 1, n3, n4))) {
				return true;
			}
			if (d4 < 0.3) {
				if (BlockUtil.isSolid(world.getBlockAt(n2 - 1, n3, n4 - 1))) {
					return true;
				}
				if (BlockUtil.isSolid(world.getBlockAt(n2, n3, n4 - 1))) {
					return true;
				}
				if (BlockUtil.isSolid(world.getBlockAt(n2 + 1, n3, n4 - 1))) {
					return true;
				}
			} else if (d4 > 0.7) {
				if (BlockUtil.isSolid(world.getBlockAt(n2 - 1, n3, n4 + 1))) {
					return true;
				}
				if (BlockUtil.isSolid(world.getBlockAt(n2, n3, n4 + 1))) {
					return true;
				}
				if (BlockUtil.isSolid(world.getBlockAt(n2 + 1, n3, n4 + 1))) {
					return true;
				}
			}
		} else if (d4 < 0.3 ? BlockUtil.isSolid(world.getBlockAt(n2, n3, n4 - 1)) : d4 > 0.7 && BlockUtil.isSolid(world.getBlockAt(n2, n3, n4 + 1))) {
			return true;
		}
		return false;
	}

	public static boolean isOnGround(Player player, int n) {
		return isOnGround(player.getLocation(), n);
	}

	public static boolean isOnBlock(Location location, int n, Material[] arrmaterial) {
		final double d = location.getX();
		final double d2 = location.getZ();
		final double d3 = MathUtil.getFraction(d) > 0.0 ? Math.abs(MathUtil.getFraction(d)) : 1.0 - Math.abs(MathUtil.getFraction(d));
		final double d4 = MathUtil.getFraction(d2) > 0.0 ? Math.abs(MathUtil.getFraction(d2)) : 1.0 - Math.abs(MathUtil.getFraction(d2));
		final int n2 = location.getBlockX();
		final int n3 = location.getBlockY() - n;
		final int n4 = location.getBlockZ();
		final World world = location.getWorld();
		if (BlockUtil.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4))) {
			return true;
		}
		if (d3 < 0.3) {
			if (BlockUtil.containsBlockType(arrmaterial, world.getBlockAt(n2 - 1, n3, n4))) {
				return true;
			}
			if (d4 < 0.3) {
				if (BlockUtil.containsBlockType(arrmaterial, world.getBlockAt(n2 - 1, n3, n4 - 1))) {
					return true;
				}
				if (BlockUtil.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 - 1))) {
					return true;
				}
				if (BlockUtil.containsBlockType(arrmaterial, world.getBlockAt(n2 + 1, n3, n4 - 1))) {
					return true;
				}
			} else if (d4 > 0.7) {
				if (BlockUtil.containsBlockType(arrmaterial, world.getBlockAt(n2 - 1, n3, n4 + 1))) {
					return true;
				}
				if (BlockUtil.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 + 1))) {
					return true;
				}
				if (BlockUtil.containsBlockType(arrmaterial, world.getBlockAt(n2 + 1, n3, n4 + 1))) {
					return true;
				}
			}
		} else if (d3 > 0.7) {
			if (BlockUtil.containsBlockType(arrmaterial, world.getBlockAt(n2 + 1, n3, n4))) {
				return true;
			}
			if (d4 < 0.3) {
				if (BlockUtil.containsBlockType(arrmaterial, world.getBlockAt(n2 - 1, n3, n4 - 1))) {
					return true;
				}
				if (BlockUtil.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 - 1))) {
					return true;
				}
				if (BlockUtil.containsBlockType(arrmaterial, world.getBlockAt(n2 + 1, n3, n4 - 1))) {
					return true;
				}
			} else if (d4 > 0.7) {
				if (BlockUtil.containsBlockType(arrmaterial, world.getBlockAt(n2 - 1, n3, n4 + 1))) {
					return true;
				}
				if (BlockUtil.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 + 1))) {
					return true;
				}
				if (BlockUtil.containsBlockType(arrmaterial, world.getBlockAt(n2 + 1, n3, n4 + 1))) {
					return true;
				}
			}
		} else if (d4 < 0.3 ? BlockUtil.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 - 1)) : d4 > 0.7 && BlockUtil.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 + 1))) {
			return true;
		}
		return false;
	}

	public static boolean isOnBlock(Player player, int n, Material[] arrmaterial) {
		return isOnBlock(player.getLocation(), n, arrmaterial);
	}

	public static boolean isHoveringOverWater(Location location, int n) {
		final double d = location.getX();
		final double d2 = location.getZ();
		final double d3 = MathUtil.getFraction(d) > 0.0 ? Math.abs(MathUtil.getFraction(d)) : 1.0 - Math.abs(MathUtil.getFraction(d));
		final double d4 = MathUtil.getFraction(d2) > 0.0 ? Math.abs(MathUtil.getFraction(d2)) : 1.0 - Math.abs(MathUtil.getFraction(d2));
		final int n2 = location.getBlockX();
		final int n3 = location.getBlockY() - n;
		final int n4 = location.getBlockZ();
		final World world = location.getWorld();
		if (BlockUtil.isLiquid(world.getBlockAt(n2, n3, n4))) {
			return true;
		}
		if (d3 < 0.3) {
			if (BlockUtil.isLiquid(world.getBlockAt(n2 - 1, n3, n4))) {
				return true;
			}
			if (d4 < 0.3) {
				if (BlockUtil.isLiquid(world.getBlockAt(n2 - 1, n3, n4 - 1))) {
					return true;
				}
				if (BlockUtil.isLiquid(world.getBlockAt(n2, n3, n4 - 1))) {
					return true;
				}
				if (BlockUtil.isLiquid(world.getBlockAt(n2 + 1, n3, n4 - 1))) {
					return true;
				}
			} else if (d4 > 0.7) {
				if (BlockUtil.isLiquid(world.getBlockAt(n2 - 1, n3, n4 + 1))) {
					return true;
				}
				if (BlockUtil.isLiquid(world.getBlockAt(n2, n3, n4 + 1))) {
					return true;
				}
				if (BlockUtil.isLiquid(world.getBlockAt(n2 + 1, n3, n4 + 1))) {
					return true;
				}
			}
		} else if (d3 > 0.7) {
			if (BlockUtil.isLiquid(world.getBlockAt(n2 + 1, n3, n4))) {
				return true;
			}
			if (d4 < 0.3) {
				if (BlockUtil.isLiquid(world.getBlockAt(n2 - 1, n3, n4 - 1))) {
					return true;
				}
				if (BlockUtil.isLiquid(world.getBlockAt(n2, n3, n4 - 1))) {
					return true;
				}
				if (BlockUtil.isLiquid(world.getBlockAt(n2 + 1, n3, n4 - 1))) {
					return true;
				}
			} else if (d4 > 0.7) {
				if (BlockUtil.isLiquid(world.getBlockAt(n2 - 1, n3, n4 + 1))) {
					return true;
				}
				if (BlockUtil.isLiquid(world.getBlockAt(n2, n3, n4 + 1))) {
					return true;
				}
				if (BlockUtil.isLiquid(world.getBlockAt(n2 + 1, n3, n4 + 1))) {
					return true;
				}
			}
		} else if (d4 < 0.3 ? BlockUtil.isLiquid(world.getBlockAt(n2, n3, n4 - 1)) : d4 > 0.7 && BlockUtil.isLiquid(world.getBlockAt(n2, n3, n4 + 1))) {
			return true;
		}
		return false;
	}

	public static boolean isHoveringOverWater(Player player, int n) {
		return isHoveringOverWater(player.getLocation(), n);
	}
}