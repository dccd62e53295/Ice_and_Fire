package com.github.alexthe666.iceandfire.event.worldgen.condition;

import com.github.alexthe666.iceandfire.event.worldgen.WGCondition;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;

public class CSBSBlock implements WGCondition {
	@Override
	public void fromJson(JsonObject args) {
		this.blockId=args.get("block").getAsString();
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1=new JsonObject();
		v1.addProperty("block", this.blockId);
		return v1;
	}

	public static CSBSBlock of(Block p1) {
		CSBSBlock that=new CSBSBlock();
		that.blockState=p1;
		that.blockId=p1.getRegistryName().toString();
		return that;
	}
	
	public static CSBSBlock of(String p1) {
		CSBSBlock that=new CSBSBlock();
		that.blockId=p1;
		return that;
	}
	
	public Block blockState=null;
	public String blockId=null;

	@Override
	public boolean apply(WGParam args) {
		if(this.blockState==null) {
			this.blockState=Block.getBlockFromName(this.blockId);
		}
		return args.surfaceBlock.getBlock()==this.blockState;
	}

}
