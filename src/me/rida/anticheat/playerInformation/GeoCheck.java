package me.rida.anticheat.playerInformation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URL;

public class GeoCheck {

	@SuppressWarnings("unused")
	public static String getCountry(InetSocketAddress ip) throws Exception {
		final String name;
		final String country;
		final String ISP;
		final URL url = new URL("http://ip-api.com/json/" + ip.getHostName());
		final BufferedReader stream = new BufferedReader(new InputStreamReader(
				url.openStream()));
		final StringBuilder entirePage = new StringBuilder();
		String inputLine;
		while ((inputLine = stream.readLine()) != null) {
			entirePage.append(inputLine);
		}
		stream.close();
		if(!(entirePage.toString().contains("\"country_name\":\""))){

		}


		return entirePage.toString().split("\"country_name\":\"")[1].split("\",")[0];
	}




}