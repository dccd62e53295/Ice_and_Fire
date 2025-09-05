package com.github.alexthe666.iceandfire.event.worldgen;

import java.util.Random;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.google.gson.JsonObject;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface WGEventCallback extends WGObject {

	public default boolean func_180709_b(World worldIn, Random rand, BlockPos position) {
		IceAndFire.logger.error("current object class must have to extends from net.minecraft.world.gen.feature.WorldGenerator");
		IceAndFire.logger.error("current class: "+(this.getClass().getName()));
		return false;
		/*
		 warn: mapping to net.minecraft.world.gen.feature.WorldGenerator.generate(World worldIn, Random rand, BlockPos position)
		 */
	};
	
	public default void generate(WGParam p1) {
		if(null==p1) {
			return;
		}
		this.func_180709_b(p1.world, p1.random, p1.surfacePos);
	}
	
	public default void fromJson(JsonObject args) {}
	
	public default JsonObject toJson() {
		return new JsonObject();
	}
	
}
