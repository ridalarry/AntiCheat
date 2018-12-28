package me.rida.anticheat.playerInformation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class playerInformationMain {


	public static void sendInformation(Player playerChecked, Player senderInformation) {
		senderInformation.sendMessage(ChatColor.RED +  "------[Anti-Cheat]------");
		senderInformation.sendMessage(ChatColor.BLUE + "Name >> " + ChatColor.RED + playerChecked.getName());
		senderInformation.sendMessage(ChatColor.BLUE + "IP >> " + ChatColor.RED + playerChecked.getAddress().toString().replaceAll("/", ""));
		try {
			senderInformation.sendMessage(ChatColor.BLUE + "Host Name >> " + ChatColor.RED + ISPCheck.getISP(playerChecked.getAddress()));
		} catch (final Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final boolean checked = checkForProxy.proxyCheck(playerChecked.getName(), playerChecked.getAddress().toString().replaceAll("/", ""), playerChecked.getAddress().getPort());
		if(checked == false){
			senderInformation.sendMessage(ChatColor.BLUE + "VPN/Proxy >> " + ChatColor.GREEN + "No VPN/Proxy Found!");
		}
		if(checked == true){
			senderInformation.sendMessage(ChatColor.BLUE + "VPN/Proxy >> " + ChatColor.RED + "VPN/Proxy Detected!");
		}

		try {
			senderInformation.sendMessage(ChatColor.BLUE + "Country >> " + ChatColor.RED + GeoCheck.getCountry(playerChecked.getAddress()));
		} catch (final Exception e) {

		}


		try {
			senderInformation.sendMessage(ChatColor.BLUE + "City >> " + ChatColor.RED + CityCheck.getCity(playerChecked));
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			senderInformation.sendMessage(ChatColor.BLUE + "Time Zone >> " + ChatColor.RED + CityCheck.getTimeZone(playerChecked));
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Add alt accounts check
		senderInformation.sendMessage(ChatColor.BLUE + "Alt Accounts >> " + ChatColor.RED + "Still in developement");

		senderInformation.sendMessage(ChatColor.RED + "------[Anti-Cheat]------");
	}



	public static void sendInformationToAllStaff(Player playerChecked) {

		for(final Player online : Bukkit.getServer().getOnlinePlayers()) {
			if(online.hasPermission("anticheat.admin") || online.hasPermission("anticheat.staff")) {
				sendInformation(playerChecked, online);
			}

		}

	}



	public static void startDefaults() {
		TorProxies.createTorProxiesList1();
		TorProxies.createTorProxiesList2();
		DeathBotProxies.createDeathBotProxies();
		RandomProxies.createAllProxies();
		VPNPortList.setupVPNPortList();
		ProxyPortList.setupProxyPortList();
	}

}
