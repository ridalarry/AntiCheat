package me.rida.anticheat.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.Step;
import org.bukkit.material.WoodenStep;
import org.bukkit.util.Vector;

public class ReflectionUtil {
	private static String version = Bukkit.getServer().getClass().getPackage().getName().substring(23);
	private static Class<?> iBlockData;
	private static Class<?> blockPosition;
	private static Class<?> worldServer = getNMSClass("WorldServer");
	private static Class<?> vanillaBlock = getNMSClass("Block");

	public static Class<?> EntityPlayer = getNMSClass("EntityPlayer");
	public static Class<?> Entity = getNMSClass("Entity");
	public static Class<?> CraftPlayer = getCBClass("entity.CraftPlayer");
	public static final Class<?> CraftWorld = getCBClass("CraftWorld");
	public static final Class<?> World = getNMSClass("World");
	private static final Method getBlocks = getMethod(World, "a", getNMSClass("AxisAlignedBB"));
	public static final Method getBlocks1_12 = getMethod(World, "getCubes", getNMSClass("Entity"), getNMSClass("AxisAlignedBB"));

	public static float getFriction(Block block) {
		Object blockNMS = getVanillaBlock(block);
		return (float) getFieldValue(getFieldByName(vanillaBlock, "frictionFactor"), blockNMS);
	}
	public static Method getMethod(Class<?> object, String method, Class<?>... args) {
		try {
			Method methodObject = object.getMethod(method, args);

			methodObject.setAccessible(true);

			return methodObject;

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getInvokedMethod(Method method, Object object, Object... args) {
		try {
			method.setAccessible(true);
			return method.invoke(object, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Field getField(Class<?> object, String field) {
		try {
			Field fieldObject = object.getField(field);
			fieldObject.setAccessible(true);
			return fieldObject;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getInvokedField(Field field, Object object) {
		try {
			field.setAccessible(true);
			return field.get(object);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Class<?> getNMSClass(String string) {
		return getClass("net.minecraft.server." + version + "." + string);
	}


	public static Boolean getCollidingBlocks(Player player, Object axisAlignedBB) {
		Object world = getInvokedMethod(getMethod(CraftWorld, "getHandle"), player.getWorld());
		return (Boolean) (isNewVersion()
				? getInvokedMethod(getBlocks1_12, world, null, axisAlignedBB)
						: getInvokedMethod(getBlocks, world, axisAlignedBB));
	}

	public static Object getBoundingBox(Player player) {
		return isBukkitVerison("1_7") ? getInvokedField(getField(Entity, "boundingBox"), getEntityPlayer(player)) : getInvokedMethod(getMethod(EntityPlayer, "getBoundingBox"), getEntityPlayer(player));
	}

	public static Object expandBoundingBox(Object box, double x, double y, double z) {
		return getInvokedMethod(getMethod(box.getClass(), "grow", double.class, double.class, double.class), box, x, y, z);
	}

	public static Object modifyBoundingBox(Object box, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		double newminX = (double) getInvokedField(getField(box.getClass(), "a"), box) + minX;
		double newminY = (double) getInvokedField(getField(box.getClass(), "b"), box) + minY;
		double newminZ = (double) getInvokedField(getField(box.getClass(), "c"), box) + minZ;
		double newmaxX = (double) getInvokedField(getField(box.getClass(), "d"), box) + maxX;
		double newmaxY = (double) getInvokedField(getField(box.getClass(), "e"), box) + maxY;
		double newmaxZ = (double) getInvokedField(getField(box.getClass(), "f"), box) + maxZ;
		try {
			return getNMSClass("AxisAlignedBB").getConstructor(double.class, double.class, double.class, double.class, double.class, double.class).newInstance(newminX, newminY, newminZ, newmaxX, newmaxY, newmaxZ);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getEntityPlayer(Player player) {
		return getInvokedMethod(getMethod(CraftPlayer, "getHandle"), player);
	}
	@SuppressWarnings("deprecation")
	public static BoundingBox getBlockBoundingBox(Block block) {
		try {
			if (!isBukkitVerison("1_7") && blockPosition != null) {
				Object bPos = blockPosition.getConstructor(int.class, int.class, int.class).newInstance(block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ());
				Object world = getWorldHandle(block.getWorld());
				Object data = getMethodValue(getMethod(world.getClass(), "getType", blockPosition), world, bPos);
				Object blockNMS = getMethodValue(getMethod(getNMSClass("IBlockData"), "getBlock"), data);
				if (!isNewVersion()) {
					if (getMethodValueNoST(getMethodNoST(blockNMS.getClass(), "a", World, blockPosition, iBlockData), blockNMS, world, bPos, data) != null
							&& !BlockUtil.isSlab(block)) {
						BoundingBox box = toBoundingBox(getMethodValue(getMethod(blockNMS.getClass(), "a", World, blockPosition, iBlockData), blockNMS, world, bPos, data));
						if (block.getType().equals(Material.LEGACY_STEP)) {
							Step slab = (Step) block.getType().getNewData(block.getData());
							box.minY = block.getY();
							box.maxY = block.getY();
							if (slab.isInverted()) {
								box = box.add(0, 0.5f, 0, 0, 1f, 0);
							} else {
								box = box.add(0, 0f, 0, 0, 0.5f, 0);
							}
						} else if (block.getType().equals(Material.LEGACY_WOOD_STEP)) {
							WoodenStep slab = (WoodenStep) block.getType().getNewData(block.getData());

							box.minY = block.getY();
							box.maxY = block.getY();
							if (slab.isInverted()) {
								box = box.add(0, 0.5f, 0, 0, 1f, 0);
							} else {
								box = box.add(0, 0f, 0, 0, 0.5f, 0);
							}
						}
						return box;
					}
				} else {
					if (getMethodValueNoST(getMethodNoST(blockNMS.getClass(), "a", iBlockData, getNMSClass("IBlockAccess"), blockPosition), blockNMS, data, world, bPos) != null) {
						return toBoundingBox(getMethodValue(getMethod(blockNMS.getClass(), "a", iBlockData, getNMSClass("IBlockAccess"), blockPosition), blockNMS, data, world, bPos)).add(block.getX(), block.getY(), block.getZ(), block.getX(), block.getY(), block.getZ());
					} else if (getMethodValueNoST(getMethodNoST(vanillaBlock, "a", iBlockData, getNMSClass("IBlockAccess"), blockPosition), blockNMS, data, world, bPos) != null) {
						return toBoundingBox(getMethodValue(getMethod(vanillaBlock, "a", iBlockData, getNMSClass("IBlockAccess"), blockPosition), blockNMS, data, world, bPos)).add(block.getX(), block.getY(), block.getZ(), block.getX(), block.getY(), block.getZ());
					} else {
						return new BoundingBox(block.getX(), block.getY(), block.getZ(), block.getX(), block.getY(), block.getZ());
					}
				}
			} else {
				Object blockNMS = getVanillaBlock(block);
				Object world = getWorldHandle(block.getWorld());
				if (getMethodValueNoST(getMethodNoST(vanillaBlock, "a", getNMSClass("World"), int.class, int.class, int.class), blockNMS, world, block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ()) != null) {
					return toBoundingBox(getMethodValue(getMethod(vanillaBlock, "a", getNMSClass("World"), int.class, int.class, int.class), blockNMS, world, block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ()));
				} else {
					//Bukkit.broadcastMessage(block.getType().name());
					return new BoundingBox(block.getX(), block.getY(), block.getZ(), block.getX(), block.getY(), block.getZ());
				}
			}
		} catch (Exception e) {
			Bukkit.getLogger().log(Level.SEVERE, "Error occured with block: " + block.getType().toString());
			e.printStackTrace();
		}
		return null;
	}

	private static Object getVanillaBlock(Block block) {

		if (!isBukkitVerison("1_7") && iBlockData != null) {
			Object getType = getBlockData(block);
			return getMethodValue(getMethod(iBlockData, "getBlock"), getType);
		} else {
			Object world = getWorldHandle(block.getWorld());
			return getMethodValue(getMethod(worldServer, "getType", int.class, int.class, int.class), world, block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ());
		}
	}

	private static Object getWorldHandle(org.bukkit.World world) {
		return getMethodValue(getMethod(CraftWorld, "getHandle"), world);
	}


	@SuppressWarnings("unused")
	private static Object getBlockData(Block block) {
		Location loc = block.getLocation();
		try {
			if (!isBukkitVerison("1_7")) {
				Object bPos = blockPosition.getConstructor(int.class, int.class, int.class).newInstance(block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ());
				Object world = getWorldHandle(block.getWorld());
				return getMethodValue(getMethod(worldServer, "getType", blockPosition), world, bPos);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	private static boolean isBukkitVerison(String version) {
		String bukkit = Bukkit.getServer().getClass().getPackage().getName().substring(23);

		return bukkit.contains(version);
	}

	private static boolean isNewVersion() {
		return isBukkitVerison("1_9") || isBukkitVerison("1_1");
	}

	private static Class<?> getCBClass(String string) {
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



	public static Object newBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		try {
			return isBukkitVerison("1_7") ? getMethodValue(getMethod(getNMSClass("AxisAlignedBB"), "a", double.class, double.class, double.class, double.class, double.class, double.class), null, minX, minY, minZ, maxX, maxY, maxZ) : getNMSClass("AxisAlignedBB").getConstructor(double.class, double.class, double.class, double.class, double.class, double.class).newInstance(minX, minY, minZ, maxX, maxY, maxZ);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Object getMethodValue(Method method, Object object, Object... args) {
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

	private static Object getMethodValueNoST(Method method, Object object, Object... args) {
		try {
			return method.invoke(object, args);
		} catch (Exception e) {
			return null;
		}
	}

	private static Vector getBoxMin(Object box) {
		double x = (double) getFieldValue(getFieldByName(box.getClass(), "a"), box);
		double y = (double) getFieldValue(getFieldByName(box.getClass(), "b"), box);
		double z = (double) getFieldValue(getFieldByName(box.getClass(), "c"), box);
		return new Vector(x, y, z);
	}

	private static Vector getBoxMax(Object box) {
		double x = (double) getFieldValue(getFieldByName(box.getClass(), "d"), box);
		double y = (double) getFieldValue(getFieldByName(box.getClass(), "e"), box);
		double z = (double) getFieldValue(getFieldByName(box.getClass(), "f"), box);
		return new Vector(x, y, z);
	}

	public static BoundingBox toBoundingBox(Object aaBB) {
		Vector min = getBoxMin(aaBB);
		Vector max = getBoxMax(aaBB);

		return new BoundingBox((float) min.getX(), (float) min.getY(), (float) min.getZ(), (float) max.getX(), (float) max.getY(), (float) max.getZ());
	}

	private static Method getMethodNoST(Class<?> clazz, String methodName, Class<?>... args) {
		try {
			Method method = clazz.getMethod(methodName, args);
			method.setAccessible(true);
			return method;
		} catch (Exception e) {
		}
		return null;
	}
}