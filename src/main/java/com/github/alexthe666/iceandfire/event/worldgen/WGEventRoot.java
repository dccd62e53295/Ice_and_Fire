package com.github.alexthe666.iceandfire.event.worldgen;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.world.World;

public class WGEventRoot {
	public static final Gson gson = new Gson();

	@Nonnull
	public static Map<Integer, WGDim> dimList;
	@Nullable
	public static WGDim dimDefault;

	public static void postInit(File p1) throws JsonSyntaxException, JsonIOException, IOException {
		if (p1.isFile()) {
			JsonArray v1 = gson.fromJson(new FileReader(p1), JsonArray.class);
			Set<WGDim> v5 = WorldGenUtil.JsonDeserialize(v1, WGDim.class, HashSet.class);//fromJson(v1);
			loadObject(v5);
			IceAndFire.logger.debug("WG event config loaded");
		} else {
			Set<WGDim> v5;
			try {
				IceAndFire.logger.debug("generating default WG event config");
				v5 = WGDefaultValue.build();
			} catch (NoSuchMethodException | SecurityException e) {
				IceAndFire.logger.catching(e);
				IceAndFire.logger.fatal("Unable to generate default config for WorldGen");
				v5=new HashSet<>();
			}
			loadObject(v5);
			Collection<WGDim> v2=saveObject();
			
			try (FileWriter v6 = new FileWriter(p1)) {
				gson.toJson(WorldGenUtil.JsonSerialize(v2), v6);
				v6.flush();
			} catch (IOException e) {
			    e.printStackTrace();
			}

			
			IceAndFire.logger.debug("saved default WG event config");
		}
		
	}
	
	private static void loadObject(Collection<WGDim> p1) {
		Map<Integer, WGDim> v3 = new Int2ObjectOpenHashMap<>();
		dimDefault = null;
		for (WGDim v1 : p1) {
			if (v1.isDefault()) {
				if (null != dimDefault) {
					IceAndFire.logger.fatal(
							"WGEventRoot config contains duplicate default attribute, aborting.");
					throw new RuntimeException("WGEventRoot config contains duplicate default attribute");
				}
				dimDefault = v1;
			} else {
				for (int v2 : v1.getDims()) {
					if (v3.containsKey(v2)) {
						IceAndFire.logger.fatal(
								"WGEventRoot config contains duplicate dimension {}, aborting.", v2);
						throw new RuntimeException("WGEventRoot config contains duplicate dimension");
					}
					v3.put(v2, v1);
				}
			}
		}
		dimList = v3;
	}
	
	private static Collection<WGDim> saveObject(){
		Collection<WGDim> v1=new HashSet<>();
		if(null!=dimDefault) {
			v1.add(dimDefault);
		}
		v1.addAll(dimList.values());
		return v1;
	}

	public static WGDim fetchConfig(World w) {
		Integer v1 = w.provider.getDimension();
		if (dimList.containsKey(v1)) {
			return dimList.get(v1);
		} else {
			return dimDefault;
		}
	}

}
