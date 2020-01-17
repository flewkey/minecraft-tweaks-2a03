package party._2a03.mc.server;

import com.google.common.collect.Maps;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import org.json.JSONObject;
import org.json.JSONArray;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import party._2a03.mc.MinecraftTweaks2a03;
import party._2a03.mc.server.PlayerData;
import party._2a03.mc.server.PlayerPosition;

public class Config {
	private static final Logger LOGGER = LogManager.getLogger();
	private static JSONObject json;
	private static File config;
	
	public static void initConfig(File configDir) {
		config = new File(configDir, "2a03.json");
	}
	
	public static void loadConfig() throws Exception {
		LOGGER.info("Loading 2a03.party configuration");
		if (config.exists()) {
			InputStream is = new FileInputStream(config);
			String jsonRaw = IOUtils.toString(is, "UTF-8");
			json = new JSONObject(jsonRaw);
		} else {
			LOGGER.info("Config not found, creating one");
			json = new JSONObject("{\"disableTntExplosions\":false,\"spawn\":[0,0,0,0,0,-2],\"members\":[]}");
			saveConfig();
		}
		LOGGER.info("Configuration loaded");
	}

	public static PlayerData getPlayer(String uuid) {
		JSONArray members = json.getJSONArray("members");
		PlayerPosition home = null;
		for (int i = 0; i < members.length(); ++i) {
			JSONObject item = members.getJSONObject(i);
			String item_uuid = item.getString("uuid");
			if (item_uuid.equals(uuid)) {
				home = new PlayerPosition(item.getJSONArray("home"));
			}
		}
		if (home == null) {
			home = new PlayerPosition();
		}
		return new PlayerData(uuid, home);
	}

	public static void setPlayer(PlayerData player) {
		JSONArray members = json.getJSONArray("members");
		int playerIndex = -1;
		for (int i = 0; i < members.length(); ++i) {
			JSONObject item = members.getJSONObject(i);
			String item_uuid = item.getString("uuid");
			if (item_uuid.equals(player.getUUID())) {
				playerIndex = i;
			}
		}
		if (playerIndex >= 0) {
			members.remove(playerIndex);
		}
		members.put(player.getJSON());
		json.put("members", members);
		saveConfig();
	}

	public static JSONArray getData(String key) {
		return json.getJSONArray(key);
	}

	public static void setData(String key, JSONArray data) {
		json.put(key, data);
		saveConfig();
	}

	private static void saveConfig() {
		try (FileWriter file = new FileWriter(config)) {
			file.write(JSONObject.valueToString(json));
		} catch (Exception e) {
			LOGGER.error("Failed to save config file");
		}
	}
}