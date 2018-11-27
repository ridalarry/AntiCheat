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
	public static final Class<?> EntityPlayer = ServerUtil.getNMSClass("EntityPlayer");
	public static final Class<?> Entity = ServerUtil.getNMSClass("Entity");
	private static final Class<?> CraftPlayer = ServerUtil.getCBClass("entity.CraftPlayer");
	private static final Class<?> CraftWorld = ServerUtil.getCBClass("CraftWorld");
	private static final Class<?> World = ServerUtil.getNMSClass("World");
	private static final Method getCubes = ServerUtil.getMethod(World, "a", ServerUtil.getNMSClass("AxisAlignedBB"));

	public static Object getEntityPlayer(Player player) {
		return ServerUtil.getMethodValue(ServerUtil.getMethod(CraftPlayer, "getHandle"), player);
	}

	public static Object getBoundingBox(Player player) {
		return ServerUtil.isBukkitVerison("1_7") ? ServerUtil.getFieldValue(ServerUtil.getFieldByName(Entity, "boundingBox"), getEntityPlayer(player)) : ServerUtil.getMethodValue(ServerUtil.getMethod(EntityPlayer, "getBoundingBox"), getEntityPlayer(player));
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
		Object world = ServerUtil.getMethodValue(ServerUtil.getMethod(CraftWorld, "getHandle"), player.getWorld());
		return ((Collection<?>)ServerUtil.getMethodValue(getCubes, world, axisAlignedBB)).size() > 0;
	}

	public static Collection<?> getCollidingBlocks(Player player, Object axisAlignedBB) {
		Object world = ServerUtil.getMethodValue(ServerUtil.getMethod(CraftWorld, "getHandle"), player.getWorld());
		return ((Collection<?>)ServerUtil.getMethodValue(getCubes, world, axisAlignedBB));
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
	
    

    public static boolean isOnGround(Location location, int n) {
        double d = location.getX();
        double d2 = location.getZ();
        double d3 = MathUtil.getFraction(d) > 0.0 ? Math.abs(MathUtil.getFraction(d)) : 1.0 - Math.abs(MathUtil.getFraction(d));
        double d4 = MathUtil.getFraction(d2) > 0.0 ? Math.abs(MathUtil.getFraction(d2)) : 1.0 - Math.abs(MathUtil.getFraction(d2));
        int n2 = location.getBlockX();
        int n3 = location.getBlockY() - n;
        int n4 = location.getBlockZ();
        World world = location.getWorld();
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
        double d = location.getX();
        double d2 = location.getZ();
        double d3 = MathUtil.getFraction(d) > 0.0 ? Math.abs(MathUtil.getFraction(d)) : 1.0 - Math.abs(MathUtil.getFraction(d));
        double d4 = MathUtil.getFraction(d2) > 0.0 ? Math.abs(MathUtil.getFraction(d2)) : 1.0 - Math.abs(MathUtil.getFraction(d2));
        int n2 = location.getBlockX();
        int n3 = location.getBlockY() - n;
        int n4 = location.getBlockZ();
        World world = location.getWorld();
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
        double d = location.getX();
        double d2 = location.getZ();
        double d3 = MathUtil.getFraction(d) > 0.0 ? Math.abs(MathUtil.getFraction(d)) : 1.0 - Math.abs(MathUtil.getFraction(d));
        double d4 = MathUtil.getFraction(d2) > 0.0 ? Math.abs(MathUtil.getFraction(d2)) : 1.0 - Math.abs(MathUtil.getFraction(d2));
        int n2 = location.getBlockX();
        int n3 = location.getBlockY() - n;
        int n4 = location.getBlockZ();
        World world = location.getWorld();
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