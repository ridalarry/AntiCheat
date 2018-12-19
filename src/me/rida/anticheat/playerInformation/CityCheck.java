package me.rida.anticheat.playerInformation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.bukkit.entity.Player;

public class CityCheck {

	   public static String getCity(Player player) throws Exception{
		   
		   
	        URL url = new URL("https://ip-api.io/json/" + player.getAddress().toString().replaceAll("/", ""));
	        BufferedReader stream = new BufferedReader(new InputStreamReader(
	                url.openStream()));
	        StringBuilder entirePage = new StringBuilder();
	        String inputLine;
	        while ((inputLine = stream.readLine()) != null)
	            entirePage.append(inputLine);
	        stream.close();
	        if(entirePage.toString().contains("\"city\":\"")){
	        	
	        	return " ";
	        }else{
	        	
	        }
			return entirePage.toString().split("\"city\":\"")[1].split("\",")[0];
	            
	      
		   
		   
	    }
	   
}

