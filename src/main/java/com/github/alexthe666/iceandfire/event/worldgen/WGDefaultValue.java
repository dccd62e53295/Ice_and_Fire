package com.github.alexthe666.iceandfire.event.worldgen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import com.github.alexthe666.iceandfire.event.worldgen.condition.CAnd;
import com.github.alexthe666.iceandfire.event.worldgen.condition.CBiomeSnow;
import com.github.alexthe666.iceandfire.event.worldgen.condition.CBiomeTemperature;
import com.github.alexthe666.iceandfire.event.worldgen.condition.CBiomeType;
import com.github.alexthe666.iceandfire.event.worldgen.condition.CBiomeTypeHills;
import com.github.alexthe666.iceandfire.event.worldgen.condition.CBiomeTypeSpookyForest;
import com.github.alexthe666.iceandfire.event.worldgen.condition.CBiomes;
import com.github.alexthe666.iceandfire.event.worldgen.condition.CDistanceXZ;
import com.github.alexthe666.iceandfire.event.worldgen.condition.CDistanceY;
import com.github.alexthe666.iceandfire.event.worldgen.condition.CGBSOpaqueCube;
import com.github.alexthe666.iceandfire.event.worldgen.condition.CNot;
import com.github.alexthe666.iceandfire.event.worldgen.condition.COr;
import com.github.alexthe666.iceandfire.event.worldgen.condition.CSBSBlock;
import com.github.alexthe666.iceandfire.event.worldgen.condition.CSCanBlockSeeSky;
import com.github.alexthe666.iceandfire.event.worldgen.object.SEntityDragonDead;
import com.github.alexthe666.iceandfire.event.worldgen.object.SGorgonTemple;
import com.github.alexthe666.iceandfire.event.worldgen.object.SHippocampus;
import com.github.alexthe666.iceandfire.event.worldgen.object.SLily;
import com.github.alexthe666.iceandfire.event.worldgen.object.SOreVein;
import com.github.alexthe666.iceandfire.event.worldgen.object.SSeaSerpents;
import com.github.alexthe666.iceandfire.event.worldgen.object.SStymphalianBirds;
import com.github.alexthe666.iceandfire.event.worldgen.object.SWanderingCyclops;
import com.github.alexthe666.iceandfire.world.gen.WorldGenCyclopsCave;
import com.github.alexthe666.iceandfire.world.gen.WorldGenFireDragonCave;
import com.github.alexthe666.iceandfire.world.gen.WorldGenFireDragonRoosts;
import com.github.alexthe666.iceandfire.world.gen.WorldGenHydraCave;
import com.github.alexthe666.iceandfire.world.gen.WorldGenIceDragonCave;
import com.github.alexthe666.iceandfire.world.gen.WorldGenIceDragonRoosts;
import com.github.alexthe666.iceandfire.world.gen.WorldGenMausoleum;
import com.github.alexthe666.iceandfire.world.gen.WorldGenMyrmexHive;
import com.github.alexthe666.iceandfire.world.gen.WorldGenSirenIsland;
import com.github.alexthe666.iceandfire.world.village.MapGenPixieVillage;
import com.github.alexthe666.iceandfire.world.village.MapGenSnowVillage;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.BiomeDictionary;

public class WGDefaultValue {

	public static final Set<WGDim> build() throws NoSuchMethodException, SecurityException {
		Set<WGDim> v1 = new HashSet<>();

		{
			WGDimCommon v2 = new WGDimCommon();
			v2.dim = new int[] { DimensionType.OVERWORLD.getId() };
			v2.isDefault = false;
			List<WGObject> v3 = new ArrayList<>();
			{
				WGWeightedObject.Builder v4 = new WGWeightedObject.Builder();

				{
					SGenericStruct v5 = new SGenericStruct();
					v5.validIf = CBiomeType.of(BiomeDictionary.Type.BEACH);
					v5.ds = true;
					v5.struct = new SGorgonTemple();
					v4.push(v5, 5712);
				}
				{
					SGenericStruct v5 = new SGenericStruct();
					v5.struct = new WorldGenSirenIsland();
					List<WGCondition> v7 = new ArrayList<>();
					v7.add(CBiomeType.of(BiomeDictionary.Type.OCEAN));
					v7.add(CNot.of(CBiomeType.of(BiomeDictionary.Type.COLD)));
					v7.add(isFarEnoughFromSpawn());
					v5.ds = false;
					v5.validIf = CAnd.of(v7);
					v4.push(v5, 1428);
				}
				{
					SGenericStruct v5 = new SGenericStruct();
					v5.struct = new WorldGenCyclopsCave();
					List<WGCondition> v7 = new ArrayList<>();
					v7.add(CBiomeType.of(BiomeDictionary.Type.BEACH));
					v7.add(isFarEnoughFromSpawn());
					v7.add(new CGBSOpaqueCube());
					v5.ds = false;
					v5.validIf = CAnd.of(v7);
					v4.push(v5, 2520);
				}
				{
					SGenericStruct v5 = new SGenericStruct();
					List<WGCondition> v7 = new ArrayList<>();
					v7.add(CBiomeType.of(BiomeDictionary.Type.PLAINS));
					v7.add(isFarEnoughFromSpawn());
					v5.validIf = CAnd.of(v7);
					v5.ds = false;
					v5.struct = new SWanderingCyclops();
					v4.push(v5, 476);
				}
				{
					SGenericStruct v5 = new SGenericStruct();
					v5.struct = new MapGenPixieVillage();
					List<WGCondition> v7 = new ArrayList<>();
					v7.add(isFarEnoughFromSpawn());
					v7.add(CBiomeTypeSpookyForest.of());
					v5.ds = false;
					v5.validIf = CAnd.of(v7);
					v4.push(v5, 7140);
				}
				{
					SGenericStruct v5 = new SGenericStruct();
					v5.struct = new WorldGenFireDragonRoosts();
					List<WGCondition> v7 = new ArrayList<>();
					v7.add(isFarEnoughFromSpawn());
					{
						CBiomeTemperature v8 = new CBiomeTemperature();
						v8.setMin(true, false, 0);
						v8.setMax(false, false, -1);
						v7.add(v8);
					}
					v7.add(CBiomeTypeHills.of());
					List<WGCondition> v8 = new ArrayList<>();
					{
						v8.add(CBiomeSnow.of(true));
						v8.add(CBiomes.of("ice_flats"));
						v8.add(CBiomeType.of(BiomeDictionary.Type.COLD));
						v8.add(CBiomeType.of(BiomeDictionary.Type.SNOWY));
						v8.add(CBiomeType.of(BiomeDictionary.Type.WET));
						v8.add(CBiomeType.of(BiomeDictionary.Type.OCEAN));
						v8.add(CBiomeType.of(BiomeDictionary.Type.RIVER));
					}
					v7.add(CNot.of(COr.of(v8)));
					v5.validIf = CAnd.of(v7);
					v5.ds = true;
					v4.push(v5, 1190);
				}
				{
					SGenericStruct v5 = new SGenericStruct();
					v5.struct = new WorldGenFireDragonRoosts();
					List<WGCondition> v7 = new ArrayList<>();
					v7.add(isFarEnoughFromSpawn());
					{
						CBiomeTemperature v8 = new CBiomeTemperature();
						v8.setMin(true, false, 0);
						v8.setMax(false, false, -1);
						v7.add(v8);
					}
					List<WGCondition> v8 = new ArrayList<>();
					{
						v8.add(CBiomeSnow.of(true));
						v8.add(CBiomes.of("ice_flats"));
						v8.add(CBiomeType.of(BiomeDictionary.Type.COLD));
						v8.add(CBiomeType.of(BiomeDictionary.Type.SNOWY));
						v8.add(CBiomeType.of(BiomeDictionary.Type.WET));
						v8.add(CBiomeType.of(BiomeDictionary.Type.OCEAN));
						v8.add(CBiomeType.of(BiomeDictionary.Type.RIVER));
					}
					v7.add(CNot.of(COr.of(v8)));
					v5.validIf = CAnd.of(v7);
					v5.ds = true;
					v4.push(v5, 595);
				}
				{
					SGenericStruct v5 = new SGenericStruct();
					v5.struct = new WorldGenIceDragonRoosts();
					List<WGCondition> v7 = new ArrayList<>();
					v7.add(isFarEnoughFromSpawn());
					v7.add(CBiomeTypeHills.of());
					v7.add(CBiomeType.of(BiomeDictionary.Type.COLD));
					v7.add(CBiomeType.of(BiomeDictionary.Type.SNOWY));
					v5.validIf = CAnd.of(v7);
					v5.ds = true;
					v4.push(v5, 1190);
				}
				{
					SGenericStruct v5 = new SGenericStruct();
					v5.struct = new WorldGenIceDragonRoosts();
					List<WGCondition> v7 = new ArrayList<>();
					v7.add(isFarEnoughFromSpawn());
					v7.add(CBiomeType.of(BiomeDictionary.Type.COLD));
					v7.add(CBiomeType.of(BiomeDictionary.Type.SNOWY));
					v5.validIf = CAnd.of(v7);
					v5.ds = true;
					v4.push(v5, 595);
				}
				{
					SGenericStruct v5 = new SGenericStruct();
					v5.struct =SEntityDragonDead.of(EntityFireDragon.class);
					List<WGCondition> v7 = new ArrayList<>();
					v7.add(CBiomeType.of(BiomeDictionary.Type.DRY));
					v7.add(CBiomeType.of(BiomeDictionary.Type.SANDY));
					v5.validIf = CAnd.of(v7);
					v5.ds = false;
					v4.push(v5, 1428);
				}
				{
					SGenericStruct v5 = new SGenericStruct();
					v5.struct =SEntityDragonDead.of(EntityIceDragon.class);
					List<WGCondition> v7 = new ArrayList<>();
					v7.add(CBiomeType.of(BiomeDictionary.Type.COLD));
					v7.add(CBiomeType.of(BiomeDictionary.Type.SNOWY));
					v5.validIf = CAnd.of(v7);
					v5.ds = false;
					v4.push(v5, 1428);
				}
				{
					SGenericRepeating v5 = new SGenericRepeating();
					v5.amountBase = 0;
					v5.amountRand = 5;
					v5.posRand = true;
					v5.posXZRand = 5;
					v5.posYBase = 20;
					v5.posYRand = 40;
					v5.posYMp = 0;
					{
						SGenericStruct v6 = new SGenericStruct();
						v6.ds = false;
						{
							List<WGCondition> v7 = new ArrayList<>();
							v7.add(CBiomeType.of(BiomeDictionary.Type.OCEAN));
							{
								List<WGCondition> v8 = new ArrayList<>();
								v8.add(CSBSBlock.of("minecraft:water"));
								v8.add(CSBSBlock.of("minecraft:flowing_water"));
								v7.add(COr.of(v8));
							}
							v6.validIf = CAnd.of(v7);
						}
						v6.struct = new SHippocampus();
						v5.sub = v6;
					}
					v4.push(v5, 6120);
				}
				{
					SGenericRepeating v5 = new SGenericRepeating();
					v5.amountBase = 1;
					v5.amountRand = 0;
					v5.posRand = true;
					v5.posXZRand = 5;
					v5.posYBase = 20;
					v5.posYRand = 40;
					v5.posYMp = 0;
					{
						SGenericStruct v6 = new SGenericStruct();
						v6.ds = false;
						{
							List<WGCondition> v7 = new ArrayList<>();
							v7.add(CBiomeType.of(BiomeDictionary.Type.OCEAN));
							{
								List<WGCondition> v8 = new ArrayList<>();
								v8.add(CSBSBlock.of("minecraft:water"));
								v8.add(CSBSBlock.of("minecraft:flowing_water"));
								v7.add(COr.of(v8));
							}
							v6.validIf = CAnd.of(v7);
						}
						v6.struct = new SSeaSerpents();
						v5.sub = v6;
					}
					v4.push(v5, 2142);
				}
				{
					SGenericRepeating v5 = new SGenericRepeating();
					v5.amountBase = 4;
					v5.amountRand = 4;
					v5.posRand = true;
					v5.posXZRand = 5;
					v5.posYBase = 8;
					v5.posYRand = 0;
					v5.posYMp = 1;
					{
						SGenericStruct v6 = new SGenericStruct();
						v6.ds = true;
						{
							List<WGCondition> v7 = new ArrayList<>();
							v7.add(isFarEnoughFromSpawn());
							v7.add(CBiomeType.of(BiomeDictionary.Type.SWAMP));
							v6.validIf = CAnd.of(v7);
						}
						v6.struct = new SStymphalianBirds();
						v5.sub = v6;
					}
					v4.push(v5, 4284);
				}
				{
					SGenericRepeating v5 = new SGenericRepeating();
					v5.amountBase = 1;
					v5.amountRand = 0;
					v5.posRand = true;
					v5.posXZRand = 0;
					v5.posYBase = 20;
					v5.posYRand = 20;
					v5.posYMp = 0;
					{
						SGenericStruct v6 = new SGenericStruct();
						List<WGCondition> v7 = new ArrayList<>();
						v7.add(isFarEnoughFromSpawn());
						{
							CBiomeTemperature v8 = new CBiomeTemperature();
							v8.setMin(true, false, 0);
							v8.setMax(false, false, -1);
							v7.add(v8);
						}
						{
							List<WGCondition> v8 = new ArrayList<>();
							v8.add(CBiomeSnow.of(true));
							v8.add(CBiomeType.of(BiomeDictionary.Type.COLD));
							v8.add(CBiomeType.of(BiomeDictionary.Type.SNOWY));
							v8.add(CBiomeType.of(BiomeDictionary.Type.WET));
							v8.add(CBiomeType.of(BiomeDictionary.Type.OCEAN));
							v8.add(CBiomeType.of(BiomeDictionary.Type.RIVER));
							v8.add(CBiomeType.of(BiomeDictionary.Type.BEACH));
							v8.add(new CSCanBlockSeeSky());
							v7.add(CNot.of(COr.of(v8)));
						}
						{
							List<WGCondition> v8 = new ArrayList<>();
							v8.add(CBiomeType.of(BiomeDictionary.Type.HILLS));
							v8.add(CBiomeType.of(BiomeDictionary.Type.MOUNTAIN));
							v7.add(COr.of(v8));
						}
						v6.validIf = CAnd.of(v7);
						v6.ds = true;
						v6.struct = new WorldGenFireDragonCave();
						v5.sub = v6;
					}
					v4.push(v5, 2380);
				}
				{
					SGenericRepeating v5 = new SGenericRepeating();
					v5.amountBase = 1;
					v5.amountRand = 0;
					v5.posRand = true;
					v5.posXZRand = 0;
					v5.posYBase = 20;
					v5.posYRand = 20;
					v5.posYMp = 0;
					{
						SGenericStruct v6 = new SGenericStruct();
						List<WGCondition> v7 = new ArrayList<>();
						v7.add(isFarEnoughFromSpawn());
						{
							CBiomeTemperature v8 = new CBiomeTemperature();
							v8.setMin(true, false, 0);
							v8.setMax(false, false, -1);
							v7.add(v8);
						}
						{
							List<WGCondition> v8 = new ArrayList<>();
							v8.add(CBiomeSnow.of(true));
							v8.add(CBiomeType.of(BiomeDictionary.Type.COLD));
							v8.add(CBiomeType.of(BiomeDictionary.Type.SNOWY));
							v8.add(CBiomeType.of(BiomeDictionary.Type.WET));
							v8.add(CBiomeType.of(BiomeDictionary.Type.OCEAN));
							v8.add(CBiomeType.of(BiomeDictionary.Type.RIVER));
							v8.add(CBiomeType.of(BiomeDictionary.Type.BEACH));
							v8.add(new CSCanBlockSeeSky());
							v7.add(CNot.of(COr.of(v8)));
						}
						v6.validIf = CAnd.of(v7);
						v6.ds = true;
						v6.struct = new WorldGenFireDragonCave();
						v5.sub = v6;
					}
					v4.push(v5, 1190);
				}
				{
					SGenericRepeating v5 = new SGenericRepeating();
					v5.amountBase = 1;
					v5.amountRand = 0;
					v5.posRand = true;
					v5.posXZRand = 0;
					v5.posYBase = 20;
					v5.posYRand = 20;
					v5.posYMp = 0;
					{
						SGenericStruct v6 = new SGenericStruct();
						List<WGCondition> v7 = new ArrayList<>();
						v7.add(isFarEnoughFromSpawn());
						v7.add(CBiomeType.of(BiomeDictionary.Type.COLD));
						v7.add(CBiomeType.of(BiomeDictionary.Type.SNOWY));
						{
							List<WGCondition> v8 = new ArrayList<>();
							v8.add(CBiomeType.of(BiomeDictionary.Type.BEACH));
							v8.add(new CSCanBlockSeeSky());
							v7.add(CNot.of(COr.of(v8)));
						}
						{
							List<WGCondition> v8 = new ArrayList<>();
							v8.add(CBiomeType.of(BiomeDictionary.Type.HILLS));
							v8.add(CBiomeType.of(BiomeDictionary.Type.MOUNTAIN));
							v7.add(COr.of(v8));
						}
						v6.validIf = CAnd.of(v7);
						v6.ds = true;
						v6.struct = new WorldGenIceDragonCave();
						v5.sub = v6;
					}
					v4.push(v5, 2380);
				}
				{
					SGenericRepeating v5 = new SGenericRepeating();
					v5.amountBase = 1;
					v5.amountRand = 0;
					v5.posRand = true;
					v5.posXZRand = 0;
					v5.posYBase = 20;
					v5.posYRand = 20;
					v5.posYMp = 0;
					{
						SGenericStruct v6 = new SGenericStruct();
						List<WGCondition> v7 = new ArrayList<>();
						v7.add(isFarEnoughFromSpawn());
						v7.add(CBiomeType.of(BiomeDictionary.Type.COLD));
						v7.add(CBiomeType.of(BiomeDictionary.Type.SNOWY));
						{
							List<WGCondition> v8 = new ArrayList<>();
							v8.add(CBiomeType.of(BiomeDictionary.Type.BEACH));
							v8.add(new CSCanBlockSeeSky());
							v7.add(CNot.of(COr.of(v8)));
						}
						v6.validIf = CAnd.of(v7);
						v6.ds = true;
						v6.struct = new WorldGenIceDragonCave();
						v5.sub = v6;
					}
					v4.push(v5, 1190);
				}
				{
					SGenericStruct v5=new SGenericStruct();
					v5.ds=false;
					{
						List<WGCondition> v6 = new ArrayList<>();
						v6.add(CBiomeType.of(BiomeDictionary.Type.COLD));
						v6.add(CBiomeType.of(BiomeDictionary.Type.SNOWY));
						v5.validIf=CAnd.of(v6);
					}
					v5.struct=new MapGenSnowVillage();
					v4.push(v5, 4284);
				}
				{
					SGenericRepeating v5 = new SGenericRepeating();
					v5.amountBase=1;
					v5.amountRand=0;
					v5.posRand=true;
					v5.posXZRand=0;
					v5.posYBase=-20;
					v5.posYMp=1;
					v5.posYRand=10;
					{
						SGenericStruct v6=new SGenericStruct();
						v6.flh=true;
						{
							/* Invalid rules:
							 * MyrmexWorldData.get(world).getNearestHive(height, 500)
							 */
							List<WGCondition> v7 = new ArrayList<>();
							v7.add(isFarEnoughFromSpawn());
							{
								CDistanceY v8=new CDistanceY();
								v8.setMax(false, false, 0);
								v8.setMin(true, true, 15);
							v7.add(v8);
							}
							v7.add(CBiomeType.of(BiomeDictionary.Type.JUNGLE));
							v6.validIf=CAnd.of(v7);
						}
						v6.ds=false;
						v6.struct=new WorldGenMyrmexHive(false, true);
						v5.sub=v6;
					}
					v4.push(v5, 2856);
				}
				{
					SGenericRepeating v5 = new SGenericRepeating();
					v5.amountBase=1;
					v5.amountRand=0;
					v5.posRand=true;
					v5.posXZRand=0;
					v5.posYBase=-20;
					v5.posYMp=1;
					v5.posYRand=10;
					{
						SGenericStruct v6=new SGenericStruct();
						v6.flh=true;
						{
							/* Invalid rules:
							 * MyrmexWorldData.get(world).getNearestHive(height, 500)
							 */
							List<WGCondition> v7 = new ArrayList<>();
							v7.add(isFarEnoughFromSpawn());
							{
								CDistanceY v8=new CDistanceY();
								v8.setMax(false, false, 0);
								v8.setMin(true, true, 15);
							v7.add(v8);
							}
							v7.add(CBiomeType.of(BiomeDictionary.Type.HOT));
							v7.add(CBiomeType.of(BiomeDictionary.Type.DRY));
							v7.add(CBiomeType.of(BiomeDictionary.Type.SANDY));
							v6.validIf=CAnd.of(v7);
						}
						v6.ds=false;
						v6.struct=new WorldGenMyrmexHive(false, false);
						v5.sub=v6;
					}
					v4.push(v5, 2856);
				}
				{
					SGenericStruct v5=new SGenericStruct();
					v5.ds=true;
					{
						List<WGCondition> v6 = new ArrayList<>();
						v6.add(CBiomeType.of(BiomeDictionary.Type.COLD));
						v6.add(CBiomeType.of(BiomeDictionary.Type.SNOWY));
						v5.validIf=CAnd.of(v6);
					}
					v5.struct=new WorldGenMausoleum(null);
					v4.push(v5, 238);
				}
				{
					SGenericStruct v5=new SGenericStruct();
					v5.ds=true;
					{
						List<WGCondition> v6 = new ArrayList<>();
						v6.add(isFarEnoughFromSpawn());
						v6.add(CBiomeType.of(BiomeDictionary.Type.SWAMP));
						v6.add(new CGBSOpaqueCube());
						v5.validIf=CAnd.of(v6);
					}
					v5.struct=new WorldGenHydraCave();
					v4.push(v5, 2142);
				}
				// END chance spawn
				v4.dummyFromTotal(428400);
				v3.add(v4.build());
			}
			{
				SGenericRepeating v4 = new SGenericRepeating();
				v4.amountBase = 2;
				v4.amountRand = 0;
				v4.posRand = true;
				v4.posXZRand = -1;
				v4.posYBase = 0;
				v4.posYRand = 32;
				v4.posYMp = 0;
				v4.sub=SOreVein.of(4, 4, IafBlockRegistry.silverOre.getDefaultState());
				v3.add(v4);
			}
			{
				SGenericStruct v4=new SGenericStruct();
				v4.ds=false;
				v4.validIf=CBiomeType.of(BiomeDictionary.Type.SNOWY);
				{
					SGenericRepeating v5 = new SGenericRepeating();
					v5.amountBase = 3;
					v5.amountRand = 6;
					v5.posRand = true;
					v5.posXZRand = -1;
					v5.posYBase = 4;
					v5.posYRand = 28;
					v5.posYMp = 0;
					v5.sub=SOreVein.of(1,0, IafBlockRegistry.sapphireOre.getDefaultState());
					v4.struct=v5;
				}
				v3.add(v4);
			}
			{
				WGWeightedObject.Builder v4 = new WGWeightedObject.Builder();
				v4.dummyFromTotal(15);
				{
					SGenericStruct v5=new SGenericStruct();
					v5.ds=false;
					v5.flh=false;
					v5.struct=SLily.of(IafBlockRegistry.frost_lily.getDefaultState());
					{
						List<WGCondition> v6 = new ArrayList<>();
						v6.add(CBiomeType.of(BiomeDictionary.Type.COLD));
						v6.add(CBiomeType.of(BiomeDictionary.Type.SNOWY));
						v5.validIf=CAnd.of(v6);
					}
					v4.push(v5, 1);
				}
				{
					SGenericStruct v5=new SGenericStruct();
					v5.ds=false;
					v5.flh=false;
					v5.struct=SLily.of(IafBlockRegistry.fire_lily.getDefaultState());
					{
						List<WGCondition> v6 = new ArrayList<>();
						v6.add(CBiomeType.of(BiomeDictionary.Type.HOT));
						v6.add(CBiomeType.of(BiomeDictionary.Type.SANDY));
						v5.validIf=CAnd.of(v6);
					}
					v4.push(v5, 1);
				}
	            v3.add(v4.build());
			}
			v2.objs = v3.toArray(new WGObject[0]);
			v1.add(v2);
		}
		{
			WGDimCommon v2 = new WGDimCommon();
			List<WGObject> v3 = new ArrayList<>();
			{
				WGWeightedObject.Builder v4 = new WGWeightedObject.Builder();
				v4.dummyFromTotal(15);
				{
					SGenericStruct v5=new SGenericStruct();
					v5.ds=false;
					v5.flh=false;
					v5.struct=SLily.of(IafBlockRegistry.fire_lily.getDefaultState());
					v5.validIf=CBiomeType.of(BiomeDictionary.Type.NETHER);
					v4.push(v5, 1);
				}
	            v3.add(v4.build());
			}
			v2.objs = v3.toArray(new WGObject[0]);
			v2.dim = new int[] {DimensionType.NETHER.getId()};
			v2.isDefault = false;
			v1.add(v2);
		}
		{
			WGDimEmpty v2 = new WGDimEmpty();
			v2.dim = new int[0];
			v2.isDefault = true;
			v1.add(v2);
		}
		return v1;
	}

	private static final CDistanceXZ isFarEnoughFromSpawn() {
		CDistanceXZ v1 = new CDistanceXZ();
		v1.setMin(true, false, 200);
		v1.setMax(false, false, -1);
		return v1;
	}

}
