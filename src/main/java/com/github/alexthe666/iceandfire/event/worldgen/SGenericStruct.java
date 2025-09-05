package com.github.alexthe666.iceandfire.event.worldgen;

import com.google.gson.JsonObject;

import net.minecraft.util.math.BlockPos;

public class SGenericStruct implements WGObject {

	public WGCondition validIf = null;
	public WGObject struct = null;
	public boolean flh=false;// avoid true if SkyIsland / SpaceStation
	public boolean ds = false;
	public int dsYm = 1;// SkyIsland / SpaceStation mode skip generate

	@Override
	public void fromJson(JsonObject args) {
		this.validIf = WorldGenUtil.JsonDeserialize(args.get("validIf").getAsJsonObject(), WGCondition.class);
		this.struct = WorldGenUtil.JsonDeserialize(args.get("struct").getAsJsonObject(), WGObject.class);
		this.flh = args.get("flh").getAsBoolean();
		this.ds = args.get("ds").getAsBoolean();
		this.dsYm = args.get("dsYm").getAsInt();
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1 = new JsonObject();
		v1.add("validIf", WorldGenUtil.JsonSerialize(this.validIf));
		v1.add("struct", WorldGenUtil.JsonSerialize(this.struct));
		v1.addProperty("flh", this.flh);
		v1.addProperty("ds", this.ds);
		v1.addProperty("dsYm", this.dsYm);
		return v1;
	}

	@Override
	public void generate(WGParam arg0) {
		WGParam arg1=arg0;
		if(this.flh) {
			int lowestY=arg1.world.getChunksLowestHorizon(arg1.surfacePos.getX(), arg1.surfacePos.getZ());
			BlockPos lowestPos = new BlockPos(arg1.surfacePos.getX(), lowestY, arg1.surfacePos.getZ());
			WGParam arg2=new WGParam(arg1);
			arg2.setSurface(lowestPos);
			arg1=arg2;
		}
		if(this.ds) {
			BlockPos surface = arg1.surfacePos;
			if (this.ds) {
				surface = WorldGenUtil.degradeSurface(arg1.world, arg1.surfacePos, this.dsYm);
				if (null == surface) {
					return;
				}
			}
			WGParam arg2=new WGParam(arg1);
			arg2.setSurface(surface);
			arg1=arg2;
		}
		{
			if (null != this.validIf && (!this.validIf.apply(arg1))) {
				return;
			}
			this.struct.generate(arg1);
		}
	}

}
