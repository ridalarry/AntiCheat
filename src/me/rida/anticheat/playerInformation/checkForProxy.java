package me.rida.anticheat.playerInformation;

import org.bukkit.Bukkit;

public class checkForProxy {


	public static boolean proxyCheck(String playerToCheck, String playerIp, int port) {

		if(DeathBotProxies.proxiesList.contains(playerIp) || TorProxies.proxiesList.contains(playerIp) || TorProxies.proxiesList2.contains(playerIp)){

			return true;
		}

		if(RandomProxies.proxiesList.contains(playerIp) || RandomProxies.proxiesList1.contains(playerIp)
				|| RandomProxies.proxiesList2.contains(playerIp) || RandomProxies.proxiesList3.contains(playerIp)
				|| RandomProxies.proxiesList4.contains(playerIp) || RandomProxies.proxiesList5.contains(playerIp)
				|| RandomProxies.proxiesList6.contains(playerIp) || RandomProxies.proxiesList7.contains(playerIp)
				|| RandomProxies.proxiesList8.contains(playerIp) || RandomProxies.proxiesList9.contains(playerIp)){

			return true;
		}

		if(RandomProxies.proxiesList10.contains(playerIp) || RandomProxies.proxiesList11.contains(playerIp)
				|| RandomProxies.proxiesList12.contains(playerIp) || RandomProxies.proxiesList13.contains(playerIp)
				|| RandomProxies.proxiesList14.contains(playerIp) || RandomProxies.proxiesList15.contains(playerIp)
				|| RandomProxies.proxiesList16.contains(playerIp) || RandomProxies.proxiesList17.contains(playerIp)
				|| RandomProxies.proxiesList18.contains(playerIp) || RandomProxies.proxiesList19.contains(playerIp)
				|| RandomProxies.proxiesList20.contains(playerIp)){

			return true;

		}

		if(ProxyPortList.list.contains(port) || VPNPortList.list.contains(port)) {

			return true;
		}

		try {
			if(ProxyCheckWeb1.checkWebHost(Bukkit.getServer().getPlayer(playerToCheck)) == true) {
				return true;
			}
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return false;
	}
}
