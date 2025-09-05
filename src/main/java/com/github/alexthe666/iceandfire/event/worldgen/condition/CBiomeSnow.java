package com.github.alexthe666.iceandfire.event.worldgen.condition;

import com.github.alexthe666.iceandfire.event.worldgen.WGCondition;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.gson.JsonObject;

public class CBiomeSnow implements WGCondition{
	public static final CBiomeSnow of(boolean p1) {
		CBiomeSnow v1=new CBiomeSnow();
		v1.sub=p1;
		return v1;
	}
	
	public boolean sub=false;

	@Override
	public void fromJson(JsonObject args) {
		this.sub=args.get("sub").getAsBoolean();
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1=new JsonObject();
		v1.addProperty("sub", this.sub);
		return v1;
	}

	@Override
	public boolean apply(WGParam args) {
		return this.sub==args.biome.getEnableSnow();
	}

}
