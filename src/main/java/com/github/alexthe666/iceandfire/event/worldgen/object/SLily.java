package com.github.alexthe666.iceandfire.event.worldgen.object;

import com.github.alexthe666.iceandfire.event.worldgen.WGObject;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class SLily implements WGObject {


	@Override
	public void fromJson(JsonObject args) {
		this.blockId=args.get("blockId").getAsString();
		this.blockMeta=args.get("blockMeta").getAsInt();
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1=new JsonObject();
		v1.addProperty("blockId", this.blockId);
		v1.addProperty("blockMeta", this.blockMeta);
		return v1;
	}

	public static SLily of(IBlockState p1) {
		Block block=p1.getBlock();
		SLily that=new SLily();
		that.blockState=p1;
		that.blockId=block.getRegistryName().toString();
		that.blockMeta=block.getMetaFromState(p1);
		return that;
	}
	
	public IBlockState blockState=null;
	public String blockId=null;
	public int blockMeta=0;
	
	@Override
	public void generate(WGParam args) {
		if(this.blockState==null) {
			this.blockState=Block.getBlockFromName(this.blockId).getStateFromMeta(blockMeta);
		}
        if (this.blockState.getBlock().canPlaceBlockAt(args.world, args.surfacePos)) {
        	args.world.setBlockState(args.surfacePos, this.blockState);
        }
	}

}
