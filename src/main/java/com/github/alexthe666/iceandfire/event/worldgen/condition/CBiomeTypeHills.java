package com.github.alexthe666.iceandfire.event.worldgen.condition;

import com.github.alexthe666.iceandfire.event.worldgen.WGCondition;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.gson.JsonObject;

import net.minecraftforge.common.BiomeDictionary.Type;

public class CBiomeTypeHills implements WGCondition {
	public static CBiomeTypeHills of() {
		return of(Type.HILLS, Type.MOUNTAIN, Type.SNOWY);
	}

	public static CBiomeTypeHills of(Type hills, Type mountain, Type snowy) {
		CBiomeTypeHills v1 = new CBiomeTypeHills();

		v1.hills_t = hills;
		v1.mountain_t = mountain;
		v1.snowy_t = snowy;

		v1.hills_n = hills.getName();
		v1.mountain_n = mountain.getName();
		v1.snowy_n = snowy.getName();

		return v1;
	}
	
	public static CBiomeTypeHills of(String hills, String mountain, String snowy) {
		CBiomeTypeHills v1 = new CBiomeTypeHills();

		v1.hills_n = hills;
		v1.mountain_n = mountain;
		v1.snowy_n = snowy;

		return v1;
	}

	public Type hills_t = Type.HILLS;
	public Type mountain_t = Type.MOUNTAIN;
	public Type snowy_t = Type.SNOWY;

	public String hills_n = Type.HILLS.getName();
	public String mountain_n = Type.MOUNTAIN.getName();
	public String snowy_n = Type.SNOWY.getName();

	@Override
	public void fromJson(JsonObject args) {
		this.hills_n = args.get("hills").getAsString();
		this.mountain_n = args.get("mountain").getAsString();
		this.snowy_n = args.get("snowy").getAsString();
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1 = new JsonObject();
		v1.addProperty("hills", null == this.hills_t ? this.hills_n : this.hills_t.getName());
		v1.addProperty("mountain", null == this.mountain_t ? this.mountain_n : this.mountain_t.getName());
		v1.addProperty("snowy", null == this.snowy_t ? this.snowy_n : this.snowy_t.getName());
		return v1;
	}

	@Override
	public boolean apply(WGParam args) {
		if(null==this.hills_t) {
			this.hills_t=Type.getType(this.hills_n);
		}
		if(null==this.mountain_t) {
			this.mountain_t=Type.getType(this.mountain_n);
		}
		if(null==this.snowy_t) {
			this.snowy_t=Type.getType(this.snowy_n);
		}
		return args.biomeTypes.contains(this.hills_t) || (
						args.biomeTypes.contains(this.mountain_t) && 
						(!args.biomeTypes.contains(this.snowy_t))
						);
	}

}
