package com.github.alexthe666.iceandfire.event.worldgen.condition;

import com.github.alexthe666.iceandfire.event.worldgen.WGCondition;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import net.minecraft.util.math.BlockPos;

/* isFarEnoughFromSpawn */
public class CDistanceXZ implements WGCondition {
	
	public double minValue=-1;
	public double maxValue=-1;
	
	public boolean minExist=false;
	public boolean maxExist=false;
	
	public boolean minEqual=false;
	public boolean maxEqual=false;

	public double minSq=0;
	public double maxSq=0;
	
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

	public void setMin(boolean exist,boolean eq,int val) {
		if(exist) {
			this.minValue=val;
			this.minExist=true;
			this.minEqual=eq;
			this.minSq=this.minValue*this.minValue;
		}else {
			this.minValue=-1;
			this.minExist=false;
			this.minEqual=false;
			this.minSq=-1;
		}
	}
	
	public void setMax(boolean exist,boolean eq,int val) {
		if(exist) {
			this.maxValue=val;
			this.maxExist=true;
			this.maxEqual=eq;
			this.maxSq=this.maxValue*this.maxValue;
		}else {
			this.maxValue=-1;
			this.maxExist=false;
			this.maxEqual=false;
			this.maxSq=-1;
		}
	}

	protected boolean checkDefault(double v1) {
        if(this.minExist) {
        	if(v1<this.minSq) {
        		return false;
        	}
        	if((!this.minEqual)&&v1==this.minSq) {
        		return false;
        	}
        }
        if(this.maxExist) {
        	if(v1>this.maxSq) {
        		return false;
        	}
        	if((!this.maxEqual)&&v1==this.maxSq) {
        		return false;
        	}
        }
        return true;
	}
	
	protected static int calcDistance(BlockPos a1,BlockPos a2) {
		int b=0;
		{
			int c=a1.getX()-a2.getX();
			b+=c*c;
		}
		{
			int c=a1.getY()-a2.getY();
			b+=c*c;
		}
		{
			int c=a1.getZ()-a2.getZ();
			b+=c*c;
		}
		return b;
	}
	
	@Override
	public boolean apply(WGParam args) {
    	BlockPos spawnDefault=args.world.getSpawnPoint();
        BlockPos spawnRelative = new BlockPos(spawnDefault.getX(), args.surfacePos.getY(), spawnDefault.getZ());
        return this.checkDefault(calcDistance(spawnRelative,args.surfacePos));
	}

}
