package com.github.alexthe666.iceandfire.event.worldgen.condition;

import com.github.alexthe666.iceandfire.event.worldgen.WGCondition;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.gson.JsonObject;

import net.minecraftforge.common.BiomeDictionary;

public class CBiomeType implements WGCondition{
	public static final CBiomeType of(BiomeDictionary.Type p1) {
		CBiomeType v1=new CBiomeType();
		v1.type=p1;
		v1.name=p1.getName();
		return v1;
	}
	
	public static final CBiomeType of(String p1) {
		CBiomeType v1=new CBiomeType();
		v1.name=p1;
		return v1;
	}

	public BiomeDictionary.Type type;
	public String name;

	@Override
	public void fromJson(JsonObject args) {
		this.name=args.get("type").getAsString();
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1=new JsonObject();
		v1.addProperty("type", null==this.type?this.name:this.type.getName());
		return v1;
	}

	@Override
	public boolean apply(WGParam args) {
		if(null==this.type) {
			this.type=BiomeDictionary.Type.getType(this.name);
		}
		return args.biomeTypes.contains(this.type);
	}

}
