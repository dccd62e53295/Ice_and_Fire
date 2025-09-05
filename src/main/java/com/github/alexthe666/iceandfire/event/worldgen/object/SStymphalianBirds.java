package com.github.alexthe666.iceandfire.event.worldgen.object;

import com.github.alexthe666.iceandfire.entity.EntityStymphalianBird;
import com.github.alexthe666.iceandfire.event.worldgen.WGObject;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.gson.JsonObject;

public class SStymphalianBirds implements WGObject {

	@Override
	public void fromJson(JsonObject args) {
		this.allowColliding=args.get("allowColliding").getAsBoolean();
		this.variantBound=args.get("variantBound").getAsInt();
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1=new JsonObject();
		v1.addProperty("allowColliding", this.allowColliding);
		v1.addProperty("variantBound", this.variantBound);
		return v1;
	}
	
	public boolean allowColliding=false;
	public int variantBound=6;// origin is 5

	@Override
	public void generate(WGParam args) {
        EntityStymphalianBird bird = new EntityStymphalianBird(args.world);
        bird.setLocationAndAngles(args.surfacePos.getX() + 0.5F, args.surfacePos.getY() + 0.5F, args.surfacePos.getZ() + 0.5F, 0, 0);
        if (bird.isNotColliding()) {
        	args.world.spawnEntity(bird);
        }
	}

}
