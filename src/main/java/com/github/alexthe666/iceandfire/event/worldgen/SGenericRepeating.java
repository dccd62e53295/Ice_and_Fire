package com.github.alexthe666.iceandfire.event.worldgen;

import com.google.gson.JsonObject;

import net.minecraft.util.math.BlockPos;

public class SGenericRepeating implements WGObject{

	@Override
	public void fromJson(JsonObject args) {
		this.sub=WorldGenUtil.JsonDeserialize(args.get("sub").getAsJsonObject(), WGObject.class);
		
		this.amountBase=args.get("amountBase").getAsInt();
		this.amountRand=args.get("amountRand").getAsInt();
		this.posRand=args.get("posRand").getAsBoolean();
		this.posXZRand=args.get("posXZRand").getAsInt();
		this.posYBase=args.get("posYBase").getAsInt();
		this.posYRand=args.get("posYRand").getAsInt();
		this.posYMp=args.get("posYMp").getAsInt();
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1=new JsonObject();
		v1.add("sub",WorldGenUtil.JsonSerialize(this.sub));
		
		v1.addProperty("amountBase", this.amountBase);
		v1.addProperty("amountRand", this.amountRand);
		v1.addProperty("posRand", this.posRand);
		v1.addProperty("posXZRand", this.posXZRand);
		v1.addProperty("posYBase", this.posYBase);
		v1.addProperty("posYRand", this.posYRand);
		v1.addProperty("posYMp", this.posYMp);
		return v1;
	}
	
	public WGObject sub=null;
	
	public int amountBase=1;
	public int amountRand=0;
	public boolean posRand=false;
	public int posXZRand=7;
	public int posYBase=0;
	public int posYRand=0;
	public float posYMp=1;

	@Override
	public void generate(WGParam args) {
		int amont=this.amountBase;
		if(this.amountRand>0) {
			amont+=args.random.nextInt(this.amountRand);
		}
		if(amont<1) {
			return;
		}
		if(this.posRand) {
			for(int i=0;i<amont;++i) {
				int x=args.x;
				int z=args.z;
				int y=(int) Math.floor(args.surfacePos.getY()*this.posYMp);
				if(this.posXZRand>0) {
					x=-this.posXZRand;
					x+=args.random.nextInt((this.posXZRand*2)+1);
					z=-this.posXZRand;
					z+=args.random.nextInt((this.posXZRand*2)+1);
				}else if(this.posXZRand==-1) {
					x=(args.chunkX*16)+args.random.nextInt(16);
					z=(args.chunkZ*16)+args.random.nextInt(16);
				}
				y+=this.posYBase;
				if(this.posYRand>0) {
				y+=args.random.nextInt(this.posYRand);
				}
				WGParam arg2=new WGParam(args);
				arg2.setSurface(new BlockPos(x,y,z));
				this.sub.generate(arg2);
			}
		}else {
			for(int i=0;i<amont;++i) {
				this.sub.generate(args);
			}
		}
		
		
	}

}
