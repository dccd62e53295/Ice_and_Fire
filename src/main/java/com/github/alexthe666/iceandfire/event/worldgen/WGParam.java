package com.github.alexthe666.iceandfire.event.worldgen;

import java.util.Random;
import java.util.Set;

import com.github.alexthe666.iceandfire.IceAndFire;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;

public class WGParam {

	public WGParam(WGParam that) {
		this.random = that.random;
		this.chunkX = that.chunkX;
		this.chunkZ = that.chunkZ;
		this.world = that.world;
		this.chunkGenerator = that.chunkGenerator;
		this.chunkProvider = that.chunkProvider;

		//this.spawnCheck = that.spawnCheck;
		this.x = that.x;
		this.z = that.z;
		this.surfacePos = that.surfacePos;
		this.surfaceBlock = that.surfaceBlock;
		this.biome = that.biome;
		this.groundPos = that.groundPos;
		this.groundBlock = that.groundBlock;
		this.biomeTypes = that.biomeTypes;
	}

	public WGParam(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		this.random = random;
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.world = world;
		this.chunkGenerator = chunkGenerator;
		this.chunkProvider = chunkProvider;

		//this.spawnCheck = IceAndFire.CONFIG.worldGenDistance * IceAndFire.CONFIG.worldGenDistance;
		this.x = (chunkX << 4) | 8;
		this.z = (chunkZ << 4) | 8;
		this.setSurface(world.getHeight(new BlockPos(this.x, 0, this.z)));
	}

	public void setSurface(BlockPos p1) {
		this.surfacePos = p1;
		this.surfaceBlock = world.getBlockState(this.surfacePos);
		this.biome = world.getBiome(this.surfacePos);
		this.biomeTypes = BiomeDictionary.getTypes(this.biome);
		this.groundPos = this.surfacePos.down();
		this.groundBlock = world.getBlockState(this.groundPos);
	}

	public Random random;// chunk generator random instance
	public int chunkX;
	public int chunkZ;
	public World world;
	public IChunkGenerator chunkGenerator;
	public IChunkProvider chunkProvider;

	//public double spawnCheck;
	public int x;
	public int z;
	public BlockPos surfacePos;
	public IBlockState surfaceBlock;
	public Biome biome;
	public BlockPos groundPos;
	public IBlockState groundBlock;
	public Set<BiomeDictionary.Type> biomeTypes;
}
