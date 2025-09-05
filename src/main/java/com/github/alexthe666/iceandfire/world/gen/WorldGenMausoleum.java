package com.github.alexthe666.iceandfire.world.gen;

import java.util.Random;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.DragonUtils;
import com.github.alexthe666.iceandfire.event.worldgen.WGEventCallback;
import com.github.alexthe666.iceandfire.world.gen.processor.DreadRuinProcessor;
import com.google.gson.JsonObject;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class WorldGenMausoleum extends WorldGenerator implements WGEventCallback {

	@Override
	public void fromJson(JsonObject args) {
		if(args.has("facing")&&args.get("facing").isJsonPrimitive()) {
			this.facing = EnumFacing.byName(args.get("facing").getAsString());
		}else {
			this.facing=null;
		}
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1 = new JsonObject();
		if(null!=this.facing) {
			v1.addProperty("facing", this.facing.getName());
		}
		return WGEventCallback.super.toJson();
	}

	private EnumFacing facing=null;
	private static final ResourceLocation STRUCTURE = new ResourceLocation(IceAndFire.MODID, "dread_mausoleum_forge");

	public WorldGenMausoleum() {
		this(null);
	}
	
	public WorldGenMausoleum(EnumFacing facing) {
		super(false);
		this.facing = facing;
	}

	public static boolean isPartOfRuin(IBlockState state) {
		return DragonUtils.isDreadBlock(state);
	}

	public static Rotation getRotationFromFacing(EnumFacing facing) {
		switch (facing) {
		case EAST:
			return Rotation.CLOCKWISE_90;
		case SOUTH:
			return Rotation.CLOCKWISE_180;
		case WEST:
			return Rotation.COUNTERCLOCKWISE_90;
		case NORTH:
		default:
			return Rotation.NONE;
		}
	}

	public static BlockPos getGround(BlockPos pos, World world) {
		return getGround(pos.getX(), pos.getZ(), world);
	}

	public static BlockPos getGround(int x, int z, World world) {
		BlockPos skyPos = new BlockPos(x, world.getHeight(), z);
		while ((!world.getBlockState(skyPos).isOpaqueCube() || canHeightSkipBlock(skyPos, world))
				&& skyPos.getY() > 1) {
			skyPos = skyPos.down();
		}
		return skyPos;
	}

	private static boolean canHeightSkipBlock(BlockPos pos, World world) {
		IBlockState state = world.getBlockState(pos);
		return state.getBlock() instanceof BlockLog || state.getBlock() instanceof BlockLeaves
				|| state.getBlock() instanceof BlockLiquid;
	}

	public boolean generate(World worldIn, Random rand, BlockPos position) {
		EnumFacing facing = this.facing;
		if (null == facing) {
			switch (rand.nextInt(4)) {
			case 0:
				facing = EnumFacing.EAST;
				break;
			case 1:
				facing = EnumFacing.SOUTH;
			case 2:
				facing = EnumFacing.WEST;
			case 3:
			default:
				facing = EnumFacing.NORTH;
			}
		}
		position = position.add(rand.nextInt(8) - 4, 1, rand.nextInt(8) - 4);
		MinecraftServer server = worldIn.getMinecraftServer();
		BlockPos height = getGround(position, worldIn);
		IBlockState dirt = worldIn.getBlockState(height.down(2));
		TemplateManager templateManager = worldIn.getSaveHandler().getStructureTemplateManager();
		Template template = templateManager.getTemplate(server, STRUCTURE);
		PlacementSettings settings = new PlacementSettings().setRotation(getRotationFromFacing(facing));
		BlockPos pos = height.offset(facing, template.getSize().getZ() / 2).offset(facing.rotateYCCW(),
				template.getSize().getX() / 2);
		if (checkIfCanGenAt(worldIn, pos, template.getSize().getX(), template.getSize().getZ(), facing)) {
			template.addBlocksToWorld(worldIn, pos, new DreadRuinProcessor(position, settings, null), settings, 2);
			for (BlockPos underPos : BlockPos.getAllInBox(
					height.down().offset(facing, -template.getSize().getZ() / 2).offset(facing.rotateYCCW(),
							-template.getSize().getX() / 2),
					height.down(3).offset(facing, template.getSize().getZ() / 2).offset(facing.rotateYCCW(),
							template.getSize().getX() / 2))) {
				if (!worldIn.getBlockState(underPos).isOpaqueCube() && !worldIn.isAirBlock(underPos.up())) {
					worldIn.setBlockState(underPos, dirt);
					worldIn.setBlockState(underPos.down(), dirt);
					worldIn.setBlockState(underPos.down(2), dirt);
					worldIn.setBlockState(underPos.down(3), dirt);
				}
			}
		}
		return true;
	}

	public boolean checkIfCanGenAt(World world, BlockPos middle, int x, int z, EnumFacing facing) {
		return !isPartOfRuin(world.getBlockState(middle.offset(facing, z / 2)))
				&& !isPartOfRuin(world.getBlockState(middle.offset(facing.getOpposite(), z / 2)))
				&& !isPartOfRuin(world.getBlockState(middle.offset(facing.rotateY(), x / 2)))
				&& !isPartOfRuin(world.getBlockState(middle.offset(facing.rotateYCCW(), x / 2)));
	}
}