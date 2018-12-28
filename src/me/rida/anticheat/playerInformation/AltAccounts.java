package me.rida.anticheat.playerInformation;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class AltAccounts{

	/* This part of the plugin will work as follows
	 * Alts will be detected by using IP Address lookup and UUID lookup
	 * In the custom config file, there will many different lists.
	 * List 1 will contain a list of all players that have joined from a single IP Address
	 * List 2 will contain a list of all IP Addresses a person has joined from
	 * List 1 and 2 will be used together to determine all alts, even from VPN's
	 * It will be implemented as follows: Find all players from single IP address (list 1)
	 * From this, we will find all the other IP addresses that the alts have joined from to determine other possible alts
	 */


	// The check will check UUID and IP Address
	public static boolean hasAltAccounts(Player player) {

		return false;
	}

	// Will return all alt accounts, checking UUID s
	public static ArrayList<String> getAlts(Player player){
		final ArrayList<String> altAccounts = new ArrayList<>();


		return altAccounts;

	}

	// This will add the alt account to a custom config file when a new alt joins the server
	public static void addNewAltAccount(Player player) {

	}
}
