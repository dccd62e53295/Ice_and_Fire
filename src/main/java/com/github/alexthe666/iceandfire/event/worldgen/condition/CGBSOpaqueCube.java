package com.github.alexthe666.iceandfire.event.worldgen.condition;

import com.github.alexthe666.iceandfire.event.worldgen.WGCondition;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.gson.JsonObject;

public class CGBSOpaqueCube implements WGCondition {

	@Override
	public void fromJson(JsonObject args) {}

	@Override
	public boolean apply(WGParam args) {
		return args.groundBlock.isOpaqueCube();
	}

	@Override
	public JsonObject toJson() {
		return new JsonObject();
	}

}
