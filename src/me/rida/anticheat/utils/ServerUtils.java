package me.rida.anticheat.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
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
	
    

    public static boolean isOnGround(Location location, int n) {
        double d = location.getX();
        double d2 = location.getZ();
        double d3 = MathUtils.getFraction(d) > 0.0 ? Math.abs(MathUtils.getFraction(d)) : 1.0 - Math.abs(MathUtils.getFraction(d));
        double d4 = MathUtils.getFraction(d2) > 0.0 ? Math.abs(MathUtils.getFraction(d2)) : 1.0 - Math.abs(MathUtils.getFraction(d2));
        int n2 = location.getBlockX();
        int n3 = location.getBlockY() - n;
        int n4 = location.getBlockZ();
        World world = location.getWorld();
        if (BlockUtils.isSolid(world.getBlockAt(n2, n3, n4))) {
            return true;
        }
        if (d3 < 0.3) {
            if (BlockUtils.isSolid(world.getBlockAt(n2 - 1, n3, n4))) {
                return true;
            }
            if (d4 < 0.3) {
                if (BlockUtils.isSolid(world.getBlockAt(n2 - 1, n3, n4 - 1))) {
                    return true;
                }
                if (BlockUtils.isSolid(world.getBlockAt(n2, n3, n4 - 1))) {
                    return true;
                }
                if (BlockUtils.isSolid(world.getBlockAt(n2 + 1, n3, n4 - 1))) {
                    return true;
                }
            } else if (d4 > 0.7) {
                if (BlockUtils.isSolid(world.getBlockAt(n2 - 1, n3, n4 + 1))) {
                    return true;
                }
                if (BlockUtils.isSolid(world.getBlockAt(n2, n3, n4 + 1))) {
                    return true;
                }
                if (BlockUtils.isSolid(world.getBlockAt(n2 + 1, n3, n4 + 1))) {
                    return true;
                }
            }
        } else if (d3 > 0.7) {
            if (BlockUtils.isSolid(world.getBlockAt(n2 + 1, n3, n4))) {
                return true;
            }
            if (d4 < 0.3) {
                if (BlockUtils.isSolid(world.getBlockAt(n2 - 1, n3, n4 - 1))) {
                    return true;
                }
                if (BlockUtils.isSolid(world.getBlockAt(n2, n3, n4 - 1))) {
                    return true;
                }
                if (BlockUtils.isSolid(world.getBlockAt(n2 + 1, n3, n4 - 1))) {
                    return true;
                }
            } else if (d4 > 0.7) {
                if (BlockUtils.isSolid(world.getBlockAt(n2 - 1, n3, n4 + 1))) {
                    return true;
                }
                if (BlockUtils.isSolid(world.getBlockAt(n2, n3, n4 + 1))) {
                    return true;
                }
                if (BlockUtils.isSolid(world.getBlockAt(n2 + 1, n3, n4 + 1))) {
                    return true;
                }
            }
        } else if (d4 < 0.3 ? BlockUtils.isSolid(world.getBlockAt(n2, n3, n4 - 1)) : d4 > 0.7 && BlockUtils.isSolid(world.getBlockAt(n2, n3, n4 + 1))) {
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
        double d3 = MathUtils.getFraction(d) > 0.0 ? Math.abs(MathUtils.getFraction(d)) : 1.0 - Math.abs(MathUtils.getFraction(d));
        double d4 = MathUtils.getFraction(d2) > 0.0 ? Math.abs(MathUtils.getFraction(d2)) : 1.0 - Math.abs(MathUtils.getFraction(d2));
        int n2 = location.getBlockX();
        int n3 = location.getBlockY() - n;
        int n4 = location.getBlockZ();
        World world = location.getWorld();
        if (BlockUtils.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4))) {
            return true;
        }
        if (d3 < 0.3) {
            if (BlockUtils.containsBlockType(arrmaterial, world.getBlockAt(n2 - 1, n3, n4))) {
                return true;
            }
            if (d4 < 0.3) {
                if (BlockUtils.containsBlockType(arrmaterial, world.getBlockAt(n2 - 1, n3, n4 - 1))) {
                    return true;
                }
                if (BlockUtils.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 - 1))) {
                    return true;
                }
                if (BlockUtils.containsBlockType(arrmaterial, world.getBlockAt(n2 + 1, n3, n4 - 1))) {
                    return true;
                }
            } else if (d4 > 0.7) {
                if (BlockUtils.containsBlockType(arrmaterial, world.getBlockAt(n2 - 1, n3, n4 + 1))) {
                    return true;
                }
                if (BlockUtils.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 + 1))) {
                    return true;
                }
                if (BlockUtils.containsBlockType(arrmaterial, world.getBlockAt(n2 + 1, n3, n4 + 1))) {
                    return true;
                }
            }
        } else if (d3 > 0.7) {
            if (BlockUtils.containsBlockType(arrmaterial, world.getBlockAt(n2 + 1, n3, n4))) {
                return true;
            }
            if (d4 < 0.3) {
                if (BlockUtils.containsBlockType(arrmaterial, world.getBlockAt(n2 - 1, n3, n4 - 1))) {
                    return true;
                }
                if (BlockUtils.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 - 1))) {
                    return true;
                }
                if (BlockUtils.containsBlockType(arrmaterial, world.getBlockAt(n2 + 1, n3, n4 - 1))) {
                    return true;
                }
            } else if (d4 > 0.7) {
                if (BlockUtils.containsBlockType(arrmaterial, world.getBlockAt(n2 - 1, n3, n4 + 1))) {
                    return true;
                }
                if (BlockUtils.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 + 1))) {
                    return true;
                }
                if (BlockUtils.containsBlockType(arrmaterial, world.getBlockAt(n2 + 1, n3, n4 + 1))) {
                    return true;
                }
            }
        } else if (d4 < 0.3 ? BlockUtils.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 - 1)) : d4 > 0.7 && BlockUtils.containsBlockType(arrmaterial, world.getBlockAt(n2, n3, n4 + 1))) {
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
        double d3 = MathUtils.getFraction(d) > 0.0 ? Math.abs(MathUtils.getFraction(d)) : 1.0 - Math.abs(MathUtils.getFraction(d));
        double d4 = MathUtils.getFraction(d2) > 0.0 ? Math.abs(MathUtils.getFraction(d2)) : 1.0 - Math.abs(MathUtils.getFraction(d2));
        int n2 = location.getBlockX();
        int n3 = location.getBlockY() - n;
        int n4 = location.getBlockZ();
        World world = location.getWorld();
        if (BlockUtils.isLiquid(world.getBlockAt(n2, n3, n4))) {
            return true;
        }
        if (d3 < 0.3) {
            if (BlockUtils.isLiquid(world.getBlockAt(n2 - 1, n3, n4))) {
                return true;
            }
            if (d4 < 0.3) {
                if (BlockUtils.isLiquid(world.getBlockAt(n2 - 1, n3, n4 - 1))) {
                    return true;
                }
                if (BlockUtils.isLiquid(world.getBlockAt(n2, n3, n4 - 1))) {
                    return true;
                }
                if (BlockUtils.isLiquid(world.getBlockAt(n2 + 1, n3, n4 - 1))) {
                    return true;
                }
            } else if (d4 > 0.7) {
                if (BlockUtils.isLiquid(world.getBlockAt(n2 - 1, n3, n4 + 1))) {
                    return true;
                }
                if (BlockUtils.isLiquid(world.getBlockAt(n2, n3, n4 + 1))) {
                    return true;
                }
                if (BlockUtils.isLiquid(world.getBlockAt(n2 + 1, n3, n4 + 1))) {
                    return true;
                }
            }
        } else if (d3 > 0.7) {
            if (BlockUtils.isLiquid(world.getBlockAt(n2 + 1, n3, n4))) {
                return true;
            }
            if (d4 < 0.3) {
                if (BlockUtils.isLiquid(world.getBlockAt(n2 - 1, n3, n4 - 1))) {
                    return true;
                }
                if (BlockUtils.isLiquid(world.getBlockAt(n2, n3, n4 - 1))) {
                    return true;
                }
                if (BlockUtils.isLiquid(world.getBlockAt(n2 + 1, n3, n4 - 1))) {
                    return true;
                }
            } else if (d4 > 0.7) {
                if (BlockUtils.isLiquid(world.getBlockAt(n2 - 1, n3, n4 + 1))) {
                    return true;
                }
                if (BlockUtils.isLiquid(world.getBlockAt(n2, n3, n4 + 1))) {
                    return true;
                }
                if (BlockUtils.isLiquid(world.getBlockAt(n2 + 1, n3, n4 + 1))) {
                    return true;
                }
            }
        } else if (d4 < 0.3 ? BlockUtils.isLiquid(world.getBlockAt(n2, n3, n4 - 1)) : d4 > 0.7 && BlockUtils.isLiquid(world.getBlockAt(n2, n3, n4 + 1))) {
            return true;
        }
        return false;
    }

    public static boolean isHoveringOverWater(Player player, int n) {
        return isHoveringOverWater(player.getLocation(), n);
    }
    

}