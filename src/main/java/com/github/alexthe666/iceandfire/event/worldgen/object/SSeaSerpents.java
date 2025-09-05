package com.github.alexthe666.iceandfire.event.worldgen.object;

import com.github.alexthe666.iceandfire.entity.EntitySeaSerpent;
import com.github.alexthe666.iceandfire.event.worldgen.WGObject;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.gson.JsonObject;

public class SSeaSerpents implements WGObject {

	@Override
	public void generate(WGParam args) {
        EntitySeaSerpent serpent = new EntitySeaSerpent(args.world);
        serpent.onWorldSpawn(args.random);
        serpent.setLocationAndAngles(args.surfacePos.getX() + 0.5F, args.surfacePos.getY() + 0.5F, args.surfacePos.getZ() + 0.5F, 0, 0);
        args.world.spawnEntity(serpent);
	}

	@Override
	public void fromJson(JsonObject args) {}

	@Override
	public JsonObject toJson() {return new JsonObject();}

}
