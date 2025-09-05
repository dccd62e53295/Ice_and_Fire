package com.github.alexthe666.iceandfire.event.worldgen;

import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;

public class WGDimEmpty implements WGDim {
	public int[] dim;
	public boolean isDefault;

	@Override
	public void fromJson(JsonObject p1) {
		{
			JsonArray v1 = p1.getAsJsonArray("dim");
			Set<Integer> v2 = new IntOpenHashSet();
			for (JsonElement v3 : v1) {
				v2.add(v3.getAsInt());
			}
			this.dim = v2.stream().mapToInt(Integer::intValue).toArray();
		}
		this.isDefault = p1.getAsJsonPrimitive("default").getAsBoolean();
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1 = new JsonObject();
		{
			JsonArray v2 = new JsonArray();
			for (int v3 : this.dim) {
				v2.add(v3);
			}
			v1.add("dim", v2);
		}
		v1.addProperty("default", this.isDefault);
		return v1;
	}

	@Override
	public int[] getDims() {
		return this.dim;
	}

	@Override
	public boolean isDefault() {
		return this.isDefault;
	}

	@Override
	public void generate(WGParam args) {
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

}
