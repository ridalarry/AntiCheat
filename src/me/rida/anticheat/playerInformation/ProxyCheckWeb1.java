package me.rida.anticheat.playerInformation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;

import org.bukkit.entity.Player;

public class ProxyCheckWeb1 {
	
	public static boolean checkWebHost(Player player) throws Exception{
		
		String APIKey = "";
		Random rand = new Random();
		int value = rand.nextInt(4);
		
		if(value == 0){
			APIKey = "cx4jARbZXyrq34HAqWaUlPLMJCSy5RX3";
		}
		if(value == 1){
			APIKey = "WTJnlQrmG7KrMqcw8MGiZVS4UxEYKzDE";
		}
		if(value == 2){
			APIKey = "HnyRe68WOF0tu0UKpH2e6wyGlBmRa74i";
		}
		if(value == 3) {
			APIKey = "7RWIERSmc03CJUbmY5g1AvOgDrmdLSpD";
		}
		
		
		//https://ipqualityscore.com/api/json/ip/cx4jARbZXyrq34HAqWaUlPLMJCSy5RX3/USER_IP_HERE
        URL url = new URL("https://ipqualityscore.com/api/json/ip/" + APIKey + "/" + player.getAddress().toString().replaceAll("/", ""));
        BufferedReader stream = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder entirePage = new StringBuilder();
        String inputLine;
        while ((inputLine = stream.readLine()) != null)
            entirePage.append(inputLine);
        stream.close(); //"proxy":false
        if(entirePage.toString().contains("\"proxy\":true") || entirePage.toString().contains("\"vpn\":true") 
        		|| entirePage.toString().contains("\"tor\":true")){
            return true;
        }

        return false;
    }
	
	

}
