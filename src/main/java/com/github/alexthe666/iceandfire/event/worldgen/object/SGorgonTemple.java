package com.github.alexthe666.iceandfire.event.worldgen.object;

import com.github.alexthe666.iceandfire.event.worldgen.WGObject;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.github.alexthe666.iceandfire.world.gen.WorldGenGorgonTemple;
import com.google.gson.JsonObject;

import net.minecraft.util.EnumFacing;

public class SGorgonTemple implements WGObject {

	@Override
	public void generate(WGParam args) {
		new WorldGenGorgonTemple(EnumFacing.byHorizontalIndex(args.random.nextInt(3))).generate(args.world, args.random,
				args.surfacePos);
	}
	
	@Override
	public void fromJson(JsonObject args) {}

	@Override
	public JsonObject toJson() {return new JsonObject();}

}
