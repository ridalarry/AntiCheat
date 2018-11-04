package me.rida.anticheat.utils.lineofsight;

import org.bukkit.World;
import org.bukkit.block.Block;

public class LineBlock {
    private final LineBlock parent;
    private int x;
    private int y;
    private int z;

    public LineBlock(Block block, LineBlock parent) {
        this(block.getX(), block.getY(), block.getZ(), parent);
    }

    public LineBlock(int x, int y, int z, LineBlock parent) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.parent = parent;
    }

    public LineBlock add(int x, int y, int z) {
        return new LineBlock(this.x + x, this.y + y, this.z + z, this);
    }

    public boolean equals(Block block) {
        return (this.getX() == block.getX()) && (this.getY() == block.getY()) && (this.getZ() == block.getZ());
    }

    public boolean equals(LineBlock block) {
        return (this.getX() == block.getX()) && (this.getY() == block.getY()) && (this.getZ() == block.getZ());
    }

    public Block getBlock(World world) {
        return world.getBlockAt(this.x, this.y, this.z);
    }

    public LineBlock getParent() {
        return this.parent;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }
}
