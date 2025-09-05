package com.github.alexthe666.iceandfire.event.worldgen.condition;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.event.worldgen.WGCondition;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public class CBiomes implements WGCondition{
	public static final CBiomes of(String p1) {
		CBiomes v1=new CBiomes();
		v1.name=p1;
		return v1;
	}
	
	public static final CBiomes of(Biome p1) {
		CBiomes v1=new CBiomes();
		v1.value=p1;
		v1.name=v1.value.getRegistryName().toString();
		return v1;
	}
	
	public String name="ocean";
	public Biome value=null;
	
	public void load() {
		try {
		if(null==this.value||this.name!=this.value.getRegistryName().toString()) {
			Biome v1=Biome.REGISTRY.getObject(new ResourceLocation(this.name));
			if(v1!=null) {
				this.value=v1;
				this.name=v1.getRegistryName().toString();
			}else {
				IceAndFire.logger.warn("Biome not found: "+this.name);
			}
		}
		}catch(Throwable e) {
			IceAndFire.logger.catching(e);
		}
	}

	@Override
	public void fromJson(JsonObject args) {
		this.name=args.get("sub").getAsString();
	}

	@Override
	public JsonObject toJson() {
		this.load();
		JsonObject v1=new JsonObject();
		v1.addProperty("sub", this.name);
		return v1;
	}

	@Override
	public boolean apply(WGParam args) {
		if(null==this.value) {
			this.load();
		}
		return this.value==args.biome;
	}

}
