package com.github.alexthe666.iceandfire;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.alexthe666.iceandfire.entity.DimensionGriefing;
import com.github.alexthe666.iceandfire.entity.DragonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

public class IceAndFireConfigExtra {
    public static final Gson gson = new Gson();

    public static File file_json = null;
    public static JsonObject config_json = null;

    public static void preInit(File p1) throws IOException {
    	file_json = p1;
        if (file_json.exists()) {
        	config_json = gson.fromJson(new FileReader(file_json), JsonObject.class);
        	IceAndFire.logger.debug("IceAndFireConfigExtra.preInit load done");
        }else {
        	config_json=IceAndFireConfigExtra.generateDefaultConfigJson();
        	try (FileWriter writer = new FileWriter(file_json)) {
        	    gson.toJson(config_json, writer);
        	    writer.flush();
        	} catch (IOException e) {
        	    e.printStackTrace();
        	}
        	IceAndFire.logger.debug("IceAndFireConfigExtra.preInit create done");
        }
    }
    
    private static final JsonObject generateDefaultConfigJson() {
    	JsonObject v1=new JsonObject();
    	{
    		JsonObject v2 = new JsonObject();
            JsonArray v3 = new JsonArray();
            {
                JsonObject v4 = new JsonObject();
                v4.addProperty("w", 0);
                v4.addProperty("g", 0);
                v3.add(v4);
            }
            {
                JsonObject v4 = new JsonObject();
                v4.addProperty("w", 1);
                v4.addProperty("g", 0);
                v3.add(v4);
            }
            {
                JsonObject v4 = new JsonObject();
                v4.addProperty("w", -1);
                v4.addProperty("g", 0);
                v3.add(v4);
            }
            v2.add("dim", v3);
            v2.addProperty("default", 0);
            v2.addProperty("comment",
                    "Dragon griefing - 2 is no griefing, 1 is breaking weak blocks, 0 is default; w => dimension, g => mobGrief");
            v1.add("DragonGriefing", v2);
        }
    	{
    		JsonObject v2 = new JsonObject();
    		{
            JsonArray v3 = new JsonArray();
            	v3.add("minecraft:stone");
                v3.add("minecraft:dirt");
                v3.add("minecraft:grass");
                v2.add("list", v3);
    		}
    		v2.addProperty("disallowmode", true);
    		v2.addProperty("comment",
"Blocks that will not drop as items when broken by a dragon. Ex. \"minecraft:chest\" or \"rats:rat_crafting_table\""
    				);
    		v1.add("NoDropBreakBlocks", v2);
    	}
    	{
    		JsonObject v2 = new JsonObject();
    		{
            JsonArray v3 = new JsonArray();
                String[] v4=new String[] { "minecraft:barrier", "minecraft:obsidian", "minecraft:end_stone",
                    "minecraft:bedrock", "minecraft:end_portal", "minecraft:end_portal_frame",
                    "minecraft:command_block", "minecraft:repeating_command_block",
                    "minecraft:chain_command_block", "minecraft:iron_bars", "minecraft:end_gateway" };
            	for(String v5:v4) {
            		v3.add(v5);
            	}
                v2.add("list", v3);
    		}
    		v2.addProperty("allowmode", false);
    		v2.addProperty("comment",
"Blacklist for blocks that dragons are not to break or burn. Ex. \"minecraft:sponge\" or \"rats:rat_crafting_table\""
    				);
    		v1.add("NotBlacklistedBlock", v2);
    	}
    	return v1;
    }

    private static void reloadAdditionalConfig() {
        {
        	JsonObject v1=config_json.getAsJsonObject("DragonGriefing");
            Map<Integer, Integer> v3 = new Int2IntOpenHashMap();
            JsonArray v4 = v1.getAsJsonArray("dim");
            for (JsonElement v6 : v4) {
                if (v6.isJsonObject()) {
                    JsonObject v7 = v6.getAsJsonObject();
                    int v8 = v7.get("w").getAsInt();
                    int v9 = v7.get("g").getAsInt();
                    v3.put(v8, v9);
                }
            }
            int v2 = v1.get("default").getAsInt();
            DimensionGriefing.init(v2, v3);
        }
        {
        	JsonObject v1=config_json.getAsJsonObject("NoDropBreakBlocks");
        	Set<String> v2=new HashSet<>();
        	{
        		JsonArray v3=v1.getAsJsonArray("list");
        		for(JsonElement v4:v3) {
        			String v5=v4.getAsString();
        			v2.add(v5);
        		}
        	}
        	boolean v3=v1.get("disallowmode").getAsBoolean();
            DragonUtils.canDropFromDragonBlockBreak.load(v2,v3);
        }
        {
        	JsonObject v1=config_json.getAsJsonObject("NotBlacklistedBlock");
        	Set<String> v2=new HashSet<>();
        	{
        		JsonArray v3=v1.getAsJsonArray("list");
        		for(JsonElement v4:v3) {
        			String v5=v4.getAsString();
        			v2.add(v5);
        		}
        	}
        	boolean v3=v1.get("allowmode").getAsBoolean();
            DragonUtils.notBlacklistedBlock.load(v2,v3);
        }
    }

	public static void postInit() {
		reloadAdditionalConfig();
	}

}
