package com.github.alexthe666.iceandfire.event.worldgen.condition;

import com.github.alexthe666.iceandfire.event.worldgen.WorldGenUtil;
import com.github.alexthe666.iceandfire.event.worldgen.WGCondition;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.gson.JsonObject;

public class CNot implements WGCondition {	
	public static final CNot of(WGCondition p1) {
		CNot v1=new CNot();
		v1.sub=p1;
		return v1;
	}

	public WGCondition sub;

	@Override
	public void fromJson(JsonObject args) {
		this.sub=WorldGenUtil.JsonDeserialize(args.get("sub").getAsJsonObject(), WGCondition.class);
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1=new JsonObject();
		v1.add("sub", WorldGenUtil.JsonSerialize(this.sub));
		return v1;
	}

	@Override
	public boolean apply(WGParam args) {
		return !this.sub.apply(args);
	}

}
