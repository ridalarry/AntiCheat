package me.rida.anticheat.utils;

import java.lang.reflect.Constructor;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class JsonMessage {
	private final List<AMText> Text = new ArrayList<>();

	public static enum ClickableType {
		RunCommand("run_command"), SuggestCommand("suggest_command"), OpenURL("open_url");

		public String Action;

		private ClickableType(String Action) {
			this.Action = Action;
		}
	}

	public class AMText {
		private String Message = "";
		private final Map<String, Map.Entry<String, String>> Modifiers = new HashMap<>();

		public AMText(String Text) {
			this.Message = Text;
		}

		public String getMessage() {
			return this.Message;
		}

		public String getFormattedMessage() {
			String Chat = "{\"text\":\"" + this.Message + "\"";
			for (final String Event : this.Modifiers.keySet()) {
				final Map.Entry<String, String> Modifier = this.Modifiers.get(Event);
				Chat = Chat + ",\"" + Event + "\":{\"action\":\"" + Modifier.getKey() + "\",\"value\":"
						+ Modifier.getValue() + "}";
			}
			Chat = Chat + "}";
			return Chat;
		}

		public AMText addHoverText(String... Text) {
			final String Event = "hoverEvent";
			final String Key = "show_text";
			String Value = "";
			if (Text.length == 1) {
				Value = "{\"text\":\"" + Text[0] + "\"}";
			} else {
				Value = "{\"text\":\"\",\"extra\":[";
				String[] arrayOfString;
				final int j = (arrayOfString = Text).length;
				for (int i = 0; i < j; i++) {
					final String Message = arrayOfString[i];
					Value = Value + "{\"text\":\"" + Message + "\"},";
				}
				Value = Value.substring(0, Value.length() - 1);
				Value = Value + "]}";
			}
			final Map.Entry<String, String> Values = new AbstractMap.SimpleEntry<>(Key, Value);
			this.Modifiers.put(Event, Values);
			return this;
		}

		public AMText addHoverItem(org.bukkit.inventory.ItemStack Item) {
			try {
				final String Event = "hoverEvent";
				final String Key = "show_item";
				final Object craftItemStack = getCBClass("CraftItemStack");
				final Class<?> items = Class.forName("org.bukkit.inventory.ItemStack");
				final Object NMS = craftItemStack.getClass().getMethod("asNMSCopy", items).invoke(Item);
				final String Value = NMS.getClass().getMethod("getTag").toString();
				final Map.Entry<String, String> Values = new AbstractMap.SimpleEntry<>(Key, Value);
				this.Modifiers.put(Event, Values);
				return this;
			} catch (final Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		public AMText setClickEvent(JsonMessage.ClickableType Type, String Value) {
			final String Event = "clickEvent";
			final String Key = Type.Action;
			final Map.Entry<String, String> Values = new AbstractMap.SimpleEntry<>(Key, "\"" + Value + "\"");
			this.Modifiers.put(Event, Values);
			return this;
		}
	}

	public AMText addText(String Message) {
		final AMText Text = new AMText(Message);
		this.Text.add(Text);
		return Text;
	}

	public String getFormattedMessage() {
		String Chat = "[\"\",";
		for (final AMText Text : this.Text) {
			Chat = Chat + Text.getFormattedMessage() + ",";
		}
		Chat = Chat.substring(0, Chat.length() - 1);
		Chat = Chat + "]";
		return Chat;
	}

	public void sendToPlayer(Player player) {
		try {
			final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
			Object base = null;

			final Constructor<?> titleConstructor = getNMSClass("PacketPlayOutChat")
					.getConstructor(getNMSClass("IChatBaseComponent"));
			if (version.contains("1_7") || version.contains("1_8_R1")) {
				base = getNMSClass("ChatSerializer").getMethod("a", String.class).invoke(null, getFormattedMessage());
			} else {
				base = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
						.invoke(null, getFormattedMessage());
			}

			final Object packet = titleConstructor.newInstance(base);

			sendPacket(player, packet);
		}

		catch (final Exception e1) {
			e1.printStackTrace();
		}
	}

	public void sendPacket(Player player, Object packet) {
		try {
			final Object handle = player.getClass().getMethod("getHandle").invoke(player);
			final Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
		}

		catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public Class<?> getNMSClass(String name) {
		final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
		}

		catch (final ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Class<?> getCBClass(String name) {
		final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
		}

		catch (final ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}