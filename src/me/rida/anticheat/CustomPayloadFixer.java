package me.rida.anticheat;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FuzzyReflection;
import com.comphenix.protocol.reflect.accessors.Accessors;
import com.comphenix.protocol.reflect.accessors.MethodAccessor;
import com.comphenix.protocol.reflect.fuzzy.FuzzyMethodContract;
import com.comphenix.protocol.utility.ByteBufferInputStream;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtList;
import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;

public class CustomPayloadFixer {
	public static void checkNbtTags(PacketEvent event) throws ExploitException {
		PacketContainer container = event.getPacket();
		ByteBuf buffer = container.getSpecificModifier(ByteBuf.class).read(0).copy();

		try {
			ItemStack itemStack = null;
			try {
				itemStack = deserializeItemStack(buffer);
			} catch (Throwable ex) {
				throw new ExploitException("Unable to deserialize ItemStack", ex);
			}
			if (itemStack == null)
				throw new ExploitException("Unable to deserialize ItemStack");

			NbtCompound root = (NbtCompound) NbtFactory.fromItemTag(itemStack);
			if (root == null)
				throw new ExploitException("No NBT tag?!", itemStack);

			if (!root.containsKey("pages"))
				throw new ExploitException("No 'pages' NBT compound was found", itemStack);

			NbtList<String> pages = root.getList("pages");
			if (pages.size() > 50)
				throw new ExploitException("Too much pages", itemStack);

			if (pages.size() > 0 && "CustomPayloadFixer".equalsIgnoreCase(pages.getValue(0)))
				throw new ExploitException("Testing exploit", itemStack);

		} finally {
			buffer.release();
		}
	}
	public static MethodAccessor READ_ITEM_METHOD;
	public static ItemStack deserializeItemStack(ByteBuf buf) throws IOException {
		Validate.notNull(buf, "input cannot be null!");
		Object nmsItem = null;
		if (MinecraftReflection.isUsingNetty()) {
			if (READ_ITEM_METHOD == null) {
				READ_ITEM_METHOD = Accessors.getMethodAccessor(FuzzyReflection.fromClass(MinecraftReflection.getPacketDataSerializerClass(), true).getMethodByParameters("readItemStack", MinecraftReflection.getItemStackClass(), new Class[0]));
			}

			Object serializer = MinecraftReflection.getPacketDataSerializer(buf);
			nmsItem = READ_ITEM_METHOD.invoke(serializer);
		} else {
			if (READ_ITEM_METHOD == null) {
				READ_ITEM_METHOD = Accessors.getMethodAccessor(FuzzyReflection.fromClass(MinecraftReflection.getPacketClass()).getMethod(FuzzyMethodContract.newBuilder().parameterCount(1).parameterDerivedOf(DataInput.class).returnDerivedOf(MinecraftReflection.getItemStackClass()).build()));
			}

			DataInputStream input = new DataInputStream(new ByteBufferInputStream(buf.nioBuffer()));
			nmsItem = READ_ITEM_METHOD.invoke((Object)null, new Object[]{input});
		}

		return nmsItem != null ? MinecraftReflection.getBukkitItemStack(nmsItem) : null;
	}
	public static void checkChannels(PacketEvent event) throws ExploitException {
		int channelsSize = event.getPlayer().getListeningPluginChannels().size();

		PacketContainer container = event.getPacket();
		ByteBuf buffer = container.getSpecificModifier(ByteBuf.class).read(0).copy();

		try {
			for (int i = 0; i < buffer.toString(Charsets.UTF_8).split("\0").length; i++)
				if (++channelsSize > 124)
					throw new ExploitException("Too much channels");
		} finally {
			buffer.release();
		}
	}
}