package com.github.alexthe666.iceandfire.event.worldgen.object;

import com.github.alexthe666.iceandfire.entity.EntityCyclops;
import com.github.alexthe666.iceandfire.event.worldgen.WGObject;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.gson.JsonObject;

import net.minecraft.entity.passive.EntitySheep;

public class SWanderingCyclops implements WGObject {
	public int sheepAmontBase = 3;
	public int sheepAmontRand = 3;
	public boolean sheepExist = true;

	@Override
	public void fromJson(JsonObject args) {
		this.sheepAmontBase = args.get("sheepAmontBase").getAsInt();
		this.sheepAmontRand = args.get("sheepAmontRand").getAsInt();
		this.sheepExist = args.get("sheepExist").getAsBoolean();
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1 = new JsonObject();
		v1.addProperty("sheepAmontBase", this.sheepAmontBase);
		v1.addProperty("sheepAmontRand", this.sheepAmontRand);
		v1.addProperty("sheepExist", this.sheepExist);
		return v1;
	}

	@Override
	public void generate(WGParam args) {
		EntityCyclops cyclops = new EntityCyclops(args.world);
		cyclops.setPosition(args.x, args.surfacePos.getY() + 1, args.z);
		cyclops.setVariant(args.random.nextInt(3));
		args.world.spawnEntity(cyclops);
		if (this.sheepExist) {
			for (int i = 0; i < this.sheepAmontBase + args.random.nextInt(this.sheepAmontRand); i++) {
				EntitySheep sheep = new EntitySheep(args.world);
				sheep.setPosition(args.x, args.surfacePos.getY() + 1, args.z);
				sheep.setFleeceColor(EntitySheep.getRandomSheepColor(args.random));
				args.world.spawnEntity(sheep);
			}
		}
	}

}
