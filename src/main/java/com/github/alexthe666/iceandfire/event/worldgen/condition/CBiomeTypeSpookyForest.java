package com.github.alexthe666.iceandfire.event.worldgen.condition;

import com.github.alexthe666.iceandfire.event.worldgen.WGCondition;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.gson.JsonObject;

import net.minecraftforge.common.BiomeDictionary.Type;

public class CBiomeTypeSpookyForest implements WGCondition {
	
	public static CBiomeTypeSpookyForest of() {
		return of(Type.FOREST, Type.SPOOKY, Type.MAGICAL);
	}

	public static CBiomeTypeSpookyForest of(Type forest, Type spooky, Type magical) {
		CBiomeTypeSpookyForest v1 = new CBiomeTypeSpookyForest();

		v1.forest_t = forest;
		v1.spooky_t = spooky;
		v1.magical_t = magical;

		v1.forest_n=forest.getName();
		v1.spooky_n=spooky.getName();
		v1.magical_n=magical.getName();

		return v1;
	}
	
	public static CBiomeTypeSpookyForest of(String forest, String spooky, String magical) {
		CBiomeTypeSpookyForest v1 = new CBiomeTypeSpookyForest();

		v1.forest_n = forest;
		v1.spooky_n = spooky;
		v1.magical_n = magical;

		return v1;
	}

	public Type forest_t;
	public Type spooky_t;
	public Type magical_t;
	
	public String forest_n;
	public String spooky_n;
	public String magical_n;

	@Override
	public void fromJson(JsonObject args) {
		this.forest_n=args.get("forest").getAsString();
		this.spooky_n=args.get("spooky").getAsString();
		this.magical_n=args.get("magical").getAsString();
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1=new JsonObject();
		v1.addProperty("forest", null==this.forest_t?this.forest_n:this.forest_t.getName());
		v1.addProperty("spooky", null==this.spooky_t?this.spooky_n:this.spooky_t.getName());
		v1.addProperty("magical", null==this.magical_t?this.magical_n:this.magical_t.getName());
		return v1;
	}

	@Override
	public boolean apply(WGParam args) {
		if(null==this.forest_t) {
			this.forest_t=Type.getType(this.forest_n);
		}
		if(null==this.spooky_t) {
			this.spooky_t=Type.getType(this.spooky_n);
		}
		if(null==this.magical_t) {
			this.magical_t=Type.getType(this.magical_n);
		}
		return args.biomeTypes.contains(this.forest_t) && 
				(
						args.biomeTypes.contains(this.spooky_t) || 
						args.biomeTypes.contains(this.magical_t) 
				);
	}

}
