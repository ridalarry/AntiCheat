package me.rida.anticheat.utils.lineofsight;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.google.common.collect.Sets;

public class BlockPathFinder {

	private static boolean isValidBlock(LineBlock check, Block end, Set<LineBlock> banned) {
		for (LineBlock block : banned) {
			if (block.equals(check)) {
				return false;
			}
		}
		WrappedMaterial material = WrappedMaterial.get(check.getBlock(end.getWorld()).getType());
		return (material.getY() < 1) || (material.getX() < 1) || (material.getZ() < 1) || material.name().contains("FENCE") || !material.isSolid() || check.equals(end);
	}

	private static Set<LineBlock> line(Block start, Block end) {
		Set<LineBlock> confirmed = Sets.newHashSet();
		Set<LineBlock> banned = Sets.newHashSet();
		LineBlock current = new LineBlock(start, null);
		int iterations = 0;
		while ((current != null) && (iterations++ != 60)) {
			confirmed.add(current);
			if (current.equals(end)) {
				break;
			} else {
				if (current.getX() != end.getX()) {
					if (current.getX() < end.getX()) {
						current = current.add(1, 0, 0);
						if (BlockPathFinder.isValidBlock(current, end, banned)) {
							continue;
						} else {
							banned.add(current);
							current = current.getParent();
						}
					} else {
						current = current.add(-1, 0, 0);
						if (BlockPathFinder.isValidBlock(current, end, banned)) {
							continue;
						} else {
							banned.add(current);
							current = current.getParent();
						}
					}
				}
				if (current.getY() != end.getY()) {
					if (current.getY() < end.getY()) {
						current = current.add(0, 1, 0);
						if (BlockPathFinder.isValidBlock(current, end, banned)) {
							continue;
						} else {
							banned.add(current);
							current = current.getParent();
						}
					} else {
						current = current.add(0, -1, 0);
						if (BlockPathFinder.isValidBlock(current, end, banned)) {
							continue;
						} else {
							banned.add(current);
							current = current.getParent();
						}
					}
				}
				if (current.getZ() != end.getZ()) {
					if (current.getZ() < end.getZ()) {
						current = current.add(0, 0, 1);
						if (BlockPathFinder.isValidBlock(current, end, banned)) {
							continue;
						} else {
							banned.add(current);
							current = current.getParent();
						}
					} else {
						current = current.add(0, 0, -1);
						if (BlockPathFinder.isValidBlock(current, end, banned)) {
							continue;
						} else {
							banned.add(current);
							current = current.getParent();
						}
					}
				}
				banned.add(current);
				confirmed.remove(current);
				current = current.getParent();
			}
		}
		if (iterations == 60) {
			confirmed.add(new LineBlock(end, null));
		}
		return confirmed;
	}

	public static Set<Block> line(Location start, Location end) {
		return BlockPathFinder.toBlocks(start.getWorld(), BlockPathFinder.line(start.getBlock(), end.getBlock()));
	}

	private static Set<Block> toBlocks(World world, Set<LineBlock> lineBlocks) {
		Set<Block> blocks = Sets.newHashSet();
		for (LineBlock lineBlock : lineBlocks) {
			blocks.add(lineBlock.getBlock(world));
		}
		return blocks;
	}
}