package me.rida.anticheat.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.google.common.collect.Lists;

public class BoundingBox {

	public float minX, minY, minZ, maxX, maxY, maxZ;

	public BoundingBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	public BoundingBox(Vector min, Vector max) {
		this.minX = (float) Math.min(min.getX(), max.getX());
		this.minY = (float) Math.min(min.getY(), max.getY());
		this.minZ = (float) Math.min(min.getZ(), max.getZ());
		this.maxX = (float) Math.max(min.getX(), max.getX());
		this.maxY = (float) Math.max(min.getY(), max.getY());
		this.maxZ = (float) Math.max(min.getZ(), max.getZ());
	}

	public BoundingBox add(float x, float y, float z) {
		final float newMinX = minX + x;
		final float newMaxX = maxX + x;
		final float newMinY = minY + y;
		final float newMaxY = maxY + y;
		final float newMinZ = minZ + z;
		final float newMaxZ = maxZ + z;

		return new BoundingBox(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
	}

	public BoundingBox add(Vector vector) {
		final float x = (float) vector.getX(), y = (float) vector.getY(), z = (float) vector.getZ();

		final float newMinX = minX + x;
		final float newMaxX = maxX + x;
		final float newMinY = minY + y;
		final float newMaxY = maxY + y;
		final float newMinZ = minZ + z;
		final float newMaxZ = maxZ + z;

		return new BoundingBox(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
	}

	public BoundingBox grow(float x, float y, float z) {
		final float newMinX = minX - x;
		final float newMaxX = maxX + x;
		final float newMinY = minY - y;
		final float newMaxY = maxY + y;
		final float newMinZ = minZ - z;
		final float newMaxZ = maxZ + z;

		return new BoundingBox(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
	}

	public BoundingBox shrink(float x, float y, float z) {
		final float newMinX = minX + x;
		final float newMaxX = maxX - x;
		final float newMinY = minY + y;
		final float newMaxY = maxY - y;
		final float newMinZ = minZ + z;
		final float newMaxZ = maxZ - z;

		return new BoundingBox(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
	}

	public BoundingBox add(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		return new BoundingBox(this.minX + minX, this.minY + minY, this.minZ + minZ, this.maxX + maxX, this.maxY + maxY, this.maxZ + maxZ);
	}

	public BoundingBox subtract(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		return new BoundingBox(this.minX - minX, this.minY - minY, this.minZ - minZ, this.maxX - maxX, this.maxY - maxY, this.maxZ - maxZ);
	}

	public boolean intersectsWithBox(Vector vector) {
		return (vector.getX() > this.minX && vector.getX() < this.maxX) && ((vector.getY() > this.minY && vector.getY() < this.maxY) && (vector.getZ() > this.minZ && vector.getZ() < this.maxZ));
	}

	public List<BoundingBox> getCollidingBlockBoxes(Player player) {
		final List<BoundingBox> toReturn = new ArrayList<>();
		final int minX = MathUtil.floor(this.minX);
		final int maxX = MathUtil.floor(this.maxX + 1);
		final int minY = MathUtil.floor(this.minY);
		final int maxY = MathUtil.floor(this.maxY + 1);
		final int minZ = MathUtil.floor(this.minZ);
		final int maxZ = MathUtil.floor(this.maxZ + 1);


		for (int x = minX; x < maxX; x++) {
			for (int z = minZ; z < maxZ; z++) {
				for (int y = minY - 1; y < maxY; y++) {
					final Block block = new Location(player.getWorld(), x, y, z).getBlock();
					if (BlockUtil.isSolid(block)) {
						if (BlockUtil.collisionBoundingBoxes.containsKey(block.getType())) {
							for (final BoundingBox box : BlockUtil.getBlockBoundingBox(block)) {
								if (intersectsWithBox(box)) {
									toReturn.add(box);
									break;
								}
							}
						} else {
							final BoundingBox box = ReflectionUtil.getBlockBoundingBox(block);
							if (intersectsWithBox(box)) {
								toReturn.add(box);
							}
						}
					}
				}
			}
		}
		return toReturn;
	}

	public Vector getMinimum() {
		return new Vector(minX, minY, minZ);
	}

	public Vector getMaximum() {
		return new Vector(maxX, maxY, maxZ);
	}

	public List<Block> getCollidingBlocks(Player player) {
		final List<Block> toReturn = new ArrayList<>();
		final int minX = MathUtil.floor(this.minX);
		final int maxX = MathUtil.floor(this.maxX + 1);
		final int minY = MathUtil.floor(this.minY);
		final int maxY = MathUtil.floor(this.maxY + 1);
		final int minZ = MathUtil.floor(this.minZ);
		final int maxZ = MathUtil.floor(this.maxZ + 1);


		for (int x = minX; x < maxX; x++) {
			for (int z = minZ; z < maxZ; z++) {
				for (int y = minY - 1; y < maxY; y++) {
					final Block block = new Location(player.getWorld(), x, y, z).getBlock();
					if (BlockUtil.isSolid(block)) {
						if (BlockUtil.collisionBoundingBoxes.containsKey(block.getType())) {
							for (final BoundingBox box : BlockUtil.getBlockBoundingBox(block)) {
								if (intersectsWithBox(box)) {
									toReturn.add(block);
								}
							}
						} else {
							final BoundingBox box = ReflectionUtil.getBlockBoundingBox(block);
							if (intersectsWithBox(box)) {
								toReturn.add(block);
							}
						}
					}
				}
			}
		}
		return toReturn;
	}


	public List<Block> getAllBlocks(Player player) {
		final Location min = new Location(player.getWorld(), minX, minY, minZ).getBlock().getLocation();
		final Location max = new Location(player.getWorld(), maxX, maxY, maxZ).getBlock().getLocation();
		final List<Block> all = Lists.newArrayList();
		for (float x = (float) min.getX(); x < max.getX(); x++) {
			for (float y = (float) min.getY(); y < max.getY(); y++) {
				for (float z = (float) min.getZ(); z < max.getZ(); z++) {
					final Block block = new Location(player.getWorld(), x, y, z).getBlock();
					if (!block.getType().equals(Material.AIR)) {
						all.add(new Location(player.getWorld(), x, y, z).getBlock());
					}
				}
			}
		}
		return all;
	}

	public boolean inBlock(Player player) {
		return getCollidingBlocks(player).size() > 0;
	}

	public boolean intersectsWithBox(Object other) {
		if (other instanceof BoundingBox) {
			final BoundingBox otherBox = (BoundingBox) other;
			return otherBox.maxX > this.minX && otherBox.minX < this.maxX && otherBox.maxY > this.minY && otherBox.minY < this.maxY && otherBox.maxZ > this.minZ && otherBox.minZ < this.maxZ;
		} else {
			final BoundingBox otherBox = ReflectionUtil.toBoundingBox(other);
			return otherBox.maxX > this.minX && otherBox.minX < this.maxX && otherBox.maxY > this.minY && otherBox.minY < this.maxY && otherBox.maxZ > this.minZ && otherBox.minZ < this.maxZ;
		}
	}

	public Object toAxisAlignedBB() {
		return ReflectionUtil.newBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public String toString() {
		return "[" + minX + ", " + minY + ", " + minZ + ", " + maxX + ", " + maxY + ", " + maxZ + "]";
	}
}