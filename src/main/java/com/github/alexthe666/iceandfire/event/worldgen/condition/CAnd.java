package com.github.alexthe666.iceandfire.event.worldgen.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.github.alexthe666.iceandfire.event.worldgen.WorldGenUtil;
import com.github.alexthe666.iceandfire.event.worldgen.WGCondition;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.gson.JsonObject;

public class CAnd implements WGCondition {
	public static final CAnd of(Collection<WGCondition> p1) {
		CAnd v1=new CAnd();
		v1.sub=p1.toArray(new WGCondition[0]);
		return v1;
	}

	public WGCondition[] sub;

	@Override
	public void fromJson(JsonObject args) {
		ArrayList<WGCondition> v1 = WorldGenUtil.JsonDeserialize(args.get("sub").getAsJsonArray(), WGCondition.class,
				ArrayList.class);
		this.sub = v1.toArray(new WGCondition[0]);
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1=new JsonObject();
		v1.add("sub", WorldGenUtil.JsonSerialize(Arrays.asList(this.sub)));
		return v1;
	}

	@Override
	public boolean apply(WGParam args) {
		for(WGCondition v1:this.sub) {
			if(!v1.apply(args)) {
				return false;
			}
		}
		return true;
	}

}
