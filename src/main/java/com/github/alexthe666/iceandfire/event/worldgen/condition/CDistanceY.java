package com.github.alexthe666.iceandfire.event.worldgen.condition;

import com.github.alexthe666.iceandfire.event.worldgen.WGCondition;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
/* y range */
public class CDistanceY implements WGCondition {

	public int minValue;
	public int maxValue;
	
	public boolean minExist=false;
	public boolean maxExist=false;
	
	public boolean minEqual=false;
	public boolean maxEqual=false;
	
	@Override
	public void fromJson(JsonObject args) {
		if(args.has("min")&&args.get("min").isJsonObject()) {
			this.setMin(true,
					args.get("min").getAsJsonObject().get("equal").getAsBoolean(),
					args.get("min").getAsJsonObject().get("value").getAsInt()
			);
		}else {
			this.setMin(false,false,-1);
		}
		if(args.has("max")&&args.get("max").isJsonObject()) {
			this.setMax(true,
					args.get("max").getAsJsonObject().get("equal").getAsBoolean(),
					args.get("max").getAsJsonObject().get("value").getAsInt()
			);
		}else {
			this.setMax(false,false,-1);
		}
	}
	
	@Override
	public JsonObject toJson() {
		JsonObject v1=new JsonObject();
		if(this.minExist) {
			JsonObject v2=new JsonObject();
			v2.addProperty("equal", this.minEqual);
			v2.addProperty("value", this.minValue);
			v1.add("min", v2);
		}else {
			v1.add("min", JsonNull.INSTANCE);
		}
		
		if(this.maxExist) {
			JsonObject v2=new JsonObject();
			v2.addProperty("equal", this.maxEqual);
			v2.addProperty("value", this.maxValue);
			v1.add("max", v2);
		}else {
			v1.add("max", JsonNull.INSTANCE);
		}
		return v1;
	}
	
	public void setMin(boolean exist,boolean canEq,int val) {
		if(exist) {
			this.minExist=true;
			this.minValue=val;
			this.minEqual=canEq;
		}else {
			this.minExist=false;
			this.minValue=0;
			this.minEqual=false;
		}
	}
	
	public void setMax(boolean exist,boolean canEq,int val) {
		if(exist) {
			this.maxValue=val;
			this.maxEqual=canEq;
			this.maxExist=true;
		}else {
			this.maxValue=-1;
			this.maxEqual=false;
			this.maxExist=false;
		}
	}
	
	protected boolean checkDefault(int v1) {
        if(this.minExist) {
        	if(v1<this.minValue) {
        		return false;
        	}
        	if(!this.minEqual&&v1==this.minValue) {
        		return false;
        	}
        }
        if(this.maxExist) {
        	if(v1>this.maxValue) {
        		return false;
        	}
        	if(!this.maxEqual&&v1==this.maxValue) {
        		return false;
        	}
        }
        return true;
	}

	public boolean apply(WGParam args) {
        int y = args.surfacePos.getY();
        return this.checkDefault(y);
	}

}
