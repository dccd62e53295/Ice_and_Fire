package com.github.alexthe666.iceandfire.event.worldgen.object;

import com.github.alexthe666.iceandfire.event.worldgen.WGObject;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.common.base.Predicate;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class SOreVein implements WGObject {

	@Override
	public void fromJson(JsonObject args) {
		this.blockId=args.get("blockId").getAsString();
		this.blockMeta=args.get("blockMeta").getAsInt();
		this.amountBase=args.get("amountBase").getAsInt();
		this.amountRand=args.get("amountRand").getAsInt();
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1=new JsonObject();
		v1.addProperty("blockId", this.blockId);
		v1.addProperty("blockMeta", this.blockMeta);
		v1.addProperty("amountBase", this.amountBase);
		v1.addProperty("amountRand", this.amountRand);
		return v1;
	}

	public static SOreVein of(int base,int rand,IBlockState p1) {
		Block block=p1.getBlock();
		SOreVein that=new SOreVein();
		that.blockState=p1;
		that.blockId=block.getRegistryName().toString();
		that.blockMeta=block.getMetaFromState(p1);
		that.amountBase=base;
		that.amountRand=rand;
		return that;
	}
	
	public IBlockState blockState=null;
	public String blockId=null;
	public int blockMeta=0;
	
	public int amountBase=0;
	public int amountRand=0;

	@Override
	public void generate(WGParam args) {
		if(this.blockState==null) {
			this.blockState=Block.getBlockFromName(this.blockId).getStateFromMeta(blockMeta);
		}
		int amount=this.amountBase;
		if(this.amountRand>0) {
			amount+=args.random.nextInt(this.amountRand);
		}
		if(amount<=0) {
			return;
		}else if(amount==1){
            if (args.surfaceBlock.getBlock().isReplaceableOreGen(args.surfaceBlock, args.world, args.surfacePos, StonePredicateInstance)){
            	args.world.setBlockState(args.surfacePos, this.blockState, 2);
            }
		}else {
            new WorldGenMinable(this.blockState, amount).generate(args.world, args.random, args.surfacePos);
		}
	}

	public static final StonePredicate StonePredicateInstance=new StonePredicate();
    static class StonePredicate implements Predicate<IBlockState>
    {
        private StonePredicate()
        {
        }

        public boolean apply(IBlockState p_apply_1_)
        {
            if (p_apply_1_ != null && p_apply_1_.getBlock() == Blocks.STONE)
            {
                BlockStone.EnumType blockstone$enumtype = (BlockStone.EnumType)p_apply_1_.getValue(BlockStone.VARIANT);
                return blockstone$enumtype.isNatural();
            }
            else
            {
                return false;
            }
        }
    }
	
}
