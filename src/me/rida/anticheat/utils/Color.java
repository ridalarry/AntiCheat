package me.rida.anticheat.utils;

import org.bukkit.ChatColor;

public class Color {
	public static String Head = ChatColor.AQUA.toString();
	public static String Scramble = ChatColor.MAGIC.toString();
	public static String Strike = ChatColor.STRIKETHROUGH.toString();
	public static String Line = ChatColor.UNDERLINE.toString();
	public static String Reset = ChatColor.WHITE.toString();
	public static String DAqua = ChatColor.DARK_AQUA.toString();
	public static String DBlue = ChatColor.DARK_BLUE.toString();
	public static String DGray = ChatColor.DARK_GRAY.toString();
	public static String DGreen = ChatColor.DARK_GREEN.toString();
	public static String DPurple = ChatColor.DARK_PURPLE.toString();
	public static String DRed = ChatColor.DARK_RED.toString();
	public static String Split = "�";

	public static String Dark_Red = ChatColor.DARK_RED.toString();
	public static String Red = ChatColor.RED.toString();
	public static String Yellow = ChatColor.YELLOW.toString();
	public static String Gold = ChatColor.GOLD.toString();
	public static String Green = ChatColor.GREEN.toString();
	public static String Dark_Green = ChatColor.DARK_GREEN.toString();
	public static String Aqua = ChatColor.AQUA.toString();
	public static String Blue = ChatColor.BLUE.toString();
	public static String Dark_Blue = ChatColor.DARK_BLUE.toString();
	public static String Pink = ChatColor.LIGHT_PURPLE.toString();
	public static String Purple = ChatColor.DARK_PURPLE.toString();
	public static String Gray = ChatColor.GRAY.toString();
	public static String Dark_Gray = ChatColor.DARK_GRAY.toString();
	public static String Black = ChatColor.BLACK.toString();
	public static String Bold = ChatColor.BOLD.toString();
	public static String Italics = ChatColor.ITALIC.toString();
	public static String Underline = ChatColor.UNDERLINE.toString();
	public static String Strikethrough = ChatColor.STRIKETHROUGH.toString();
	public static String White = ChatColor.WHITE.toString();

	public static String translate(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public static String strip(String string) {
		return ChatColor.stripColor(string);
	}
}