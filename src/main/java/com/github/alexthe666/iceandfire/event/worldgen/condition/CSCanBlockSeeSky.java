package com.github.alexthe666.iceandfire.event.worldgen.condition;

import com.github.alexthe666.iceandfire.event.worldgen.WGCondition;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.gson.JsonObject;

public class CSCanBlockSeeSky implements WGCondition {

	@Override
	public boolean apply(WGParam args) {
		return args.world.canBlockSeeSky(args.surfacePos);
	}
	
	@Override
	public void fromJson(JsonObject args) {}

	@Override
	public JsonObject toJson() {return new JsonObject();}

}
