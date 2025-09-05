package com.github.alexthe666.iceandfire.event.worldgen;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class WorldGenUtil {
	private WorldGenUtil() {}

	public static <T extends JsonSerializable> T JsonDeserialize(JsonObject p1, Class<T> p2) {
		if(null==p1||!p1.isJsonObject()) {
			IceAndFire.logger.fatal("object for "+p2.getName()+" is not a JsonObject.");
			return null;
		}
		String v2=null;
		try {
			v2=p1.get(JsonSerializable.TypeKey).getAsString();
		}catch(Exception e) {
			IceAndFire.logger.error("unable to read object {} field",JsonSerializable.TypeKey);
			IceAndFire.logger.catching(e);
		}
		if(null==v2||v2.isEmpty()) {
			IceAndFire.logger.error("unable to read object {} field, got: {}",JsonSerializable.TypeKey,v2);
			return null;
		}
		T v3=null;
		try {
			Object v1=Class.forName(v2).getDeclaredConstructor().newInstance();
			if(p2.isInstance(v1)) {
				v3= p2.cast(v1);
			}else {
				IceAndFire.logger.error("{} is not instanceof {}",v2,p2.getName());
			}
		}catch(Exception e) {
			IceAndFire.logger.error("unable to init {}",v2);
			IceAndFire.logger.catching(e);
			return null;
		}
		try {
			v3.fromJson(p1);
		}catch(Exception e) {
			IceAndFire.logger.error("unable to load data on {}",v2);
			IceAndFire.logger.catching(e);
			return null;
		}
		return v3;
	}
	
	public static JsonObject JsonSerialize(JsonSerializable p1) {
		if(null==p1) {
			IceAndFire.logger.fatal("seralize object is null.");
			return null;
		}
		JsonObject v1;
		String v2=p1.getClass().getName();
		try {
			v1=p1.toJson();// origin data
		}catch(Exception e) {
			IceAndFire.logger.error("unable to serialize {}",v2);
			IceAndFire.logger.catching(e);
			return null;
		}
		try {
			v1.addProperty(JsonSerializable.TypeKey, v2);
		}catch(Exception e) {
			IceAndFire.logger.error("unable add {} field on {}",JsonSerializable.TypeKey,v2);
			IceAndFire.logger.catching(e);
			return null;
		}
		return v1;
	}
	
	public static <T extends JsonSerializable,A extends Collection<T>> A JsonDeserialize(JsonArray p1, Class<T> p2,Class<A> p3) {
		if(null==p1||!p1.isJsonArray()) {
			IceAndFire.logger.fatal("array for "+p2.getName()+" is not a JsonArray.");
			return null;
		}
		try {
			A v1=p3.getDeclaredConstructor().newInstance();
			for(JsonElement v2:p1) {
				if(!v2.isJsonObject()) {
					continue;
				}
				T v3=JsonDeserialize((JsonObject)v2,p2);
				if(p2.isInstance(v3)) {
					v1.add(v3);
				}
			}
			return v1;
		}catch(Exception e) {
			IceAndFire.logger.catching(e);
			return null;
		}
	}
	
	public static JsonArray JsonSerialize(Collection<? extends JsonSerializable> p1) {
		if(null==p1) {
			IceAndFire.logger.fatal("seralize array is null.");
			return null;
		}
		try {
			JsonArray v1=new JsonArray();
			for(JsonSerializable v2:p1) {
				JsonObject v3=JsonSerialize(v2);
				if(null!=v3&&v3.isJsonObject()) {
					v1.add(v3);
				}
			}
			return v1;
		}catch(Exception e) {
			IceAndFire.logger.catching(e);
			return null;
		}
	}

	public static <T extends JsonSerializable,A extends Map<String,T>> A JsonDeserialize(JsonObject p1, Class<T> p2,Class<A> p3) {
		if(null==p1||!p1.isJsonObject()) {
			IceAndFire.logger.fatal("map for "+p2.getName()+" is not a JsonObject.");
			return null;
		}
		try {
			A v1=p3.getDeclaredConstructor().newInstance();
			for(Entry<String, JsonElement> v2:p1.entrySet()) {
				if(!v2.getValue().isJsonObject()) {
					continue;
				}
				T v3=JsonDeserialize((JsonObject)v2.getValue(),p2);
				if(p2.isInstance(v3)) {
					v1.put(v2.getKey(),v3);
				}
			}
			return v1;
		}catch(Exception e) {
			IceAndFire.logger.catching(e);
			return null;
		}
	}
	
	public static JsonObject JsonSerialize(Map<String,? extends JsonSerializable> p1) {
		if(null==p1) {
			IceAndFire.logger.fatal("seralize map is null.");
			return null;
		}
		try {
			JsonObject v1=new JsonObject();
			for(Entry<String, ? extends JsonSerializable> v2:p1.entrySet()) {
				JsonObject v3=JsonSerialize(v2.getValue());
				if(null!=v3&&v3.isJsonObject()) {
					v1.add(v2.getKey(),v3);
				}
			}
			return v1;
		}catch(Exception e) {
			IceAndFire.logger.catching(e);
			return null;
		}
	}
	
	/* WorldGenEvents split */
	
	public static boolean canHeightSkipBlock(BlockPos pos, World world) {
        Block sb=world.getBlockState(pos).getBlock();
        return sb instanceof BlockLog || sb instanceof BlockLiquid;
    }
	
	public static BlockPos degradeSurface(World world, BlockPos p2_surface) {
		return degradeSurface(world,p2_surface,1);
	}
	
    public static BlockPos degradeSurface(World world, BlockPos p2_surface,int ymin) {
    	int y=p2_surface.getY();
    	int x=p2_surface.getX();
    	int z=p2_surface.getZ();
    	while(true) {
    		if(y<ymin) {
    			return null;
    		}
    		BlockPos pos=new BlockPos(x,y,z);
    		IBlockState state = world.getBlockState(pos);
    		if(state.isOpaqueCube()) {
    			Block sb=state.getBlock();
    			if((!(sb instanceof BlockLog))&&(!(sb instanceof BlockLiquid))){
    			    return pos;// must find surface for generate
    			}
    		}
    		--y;
    	}
    	/*
        while (
        		(
        				!world.getBlockState(surface).isOpaqueCube() || canHeightSkipBlock(surface, world)
        				) && surface.getY() > 1) {
            surface = surface.down();
        }
        return surface;
        */
    }
	
    public boolean isFarEnoughFromSpawn(World world, BlockPos pos) {
    	BlockPos spawnDefault=world.getSpawnPoint();
        BlockPos spawnRelative = new BlockPos(spawnDefault.getX(), pos.getY(), spawnDefault.getZ());
        // public int dangerousWorldGenDistanceLimit = 200;
        boolean spawnCheck = spawnRelative.distanceSq(pos) > IceAndFire.CONFIG.dangerousWorldGenDistanceLimit * IceAndFire.CONFIG.dangerousWorldGenDistanceLimit;
        return spawnCheck;
    }
    
    public Random setRandomSeed(int seedX, int seedY, int seedZ,World world){
        long j2 = (long)seedX * 341873128712L + (long)seedY * 132897987541L + world.getWorldInfo().getSeed() + (long)seedZ;
        return new Random(j2);
    }
    
    public BlockPos getNetherHeight(World world, BlockPos pos) {
        for (int i = 0; i < 255; i++) {
            BlockPos ground = pos.up(i);
            if (world.getBlockState(ground).getBlock() == Blocks.NETHERRACK && world.isAirBlock(ground.up())) {
                return ground;
            }
        }
        return null;
    }
    
}
