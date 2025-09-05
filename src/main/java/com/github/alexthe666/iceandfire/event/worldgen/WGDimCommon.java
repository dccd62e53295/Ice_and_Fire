package com.github.alexthe666.iceandfire.event.worldgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;

public class WGDimCommon implements WGDim {
	public int[] dim;
	public boolean isDefault;
	
	public WGObject[] objs;

	@Override
	public void fromJson(JsonObject p1) {
		{
			ArrayList<WGObject> v2=WorldGenUtil.JsonDeserialize(p1.getAsJsonArray("object"),WGObject.class,ArrayList.class);
			this.objs=v2.toArray(new WGObject[0]);
		}
		{
		JsonArray v1=p1.getAsJsonArray("dim");
		Set<Integer> v2=new IntOpenHashSet();
		for(JsonElement v3:v1) {
			v2.add(v3.getAsInt());
		}
		this.dim=v2.stream().mapToInt(Integer::intValue).toArray();
		}
		this.isDefault=p1.getAsJsonPrimitive("default").getAsBoolean();
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1=new JsonObject();
		v1.add("object", WorldGenUtil.JsonSerialize(Arrays.asList(this.objs)));
		{
			JsonArray v2=new JsonArray();
			for(int v3:this.dim) {
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
		for(WGObject v1:this.objs) {
			v1.generate(args);
		}
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

}
