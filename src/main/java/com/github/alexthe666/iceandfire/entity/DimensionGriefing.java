package com.github.alexthe666.iceandfire.entity;
import java.util.Map;

import com.github.alexthe666.iceandfire.IceAndFire;

import net.minecraft.world.World;

public class DimensionGriefing {
	public static Map<Integer,Integer> griefing_dim=null;
	public static int griefing_default=0;

	private static void logError(String p1,String p2) {
		IceAndFire.logger.error("config <dragon Griefing List> values '"+p1+"' "+p2+", skipping");
	}

	public static void init(int dragonGriefing,Map<Integer,Integer> dragonGriefingList) {
		griefing_default=dragonGriefing;
		griefing_dim=dragonGriefingList;
	}
	
	public static void init(int dragonGriefing, String[] dragonGriefingList) {
		griefing_default=dragonGriefing;
		for(String v1:dragonGriefingList) {
			String[] v2=v1.split(":");
			if(2!=v2.length) {
				logError(v1,"invalid split");
				continue;
			}
			int v3,v4;
			try {
				v3=Integer.parseInt(v2[0]);
				v4=Integer.parseInt(v2[1]);
			}catch(NumberFormatException e) {
				IceAndFire.logger.catching(e);
				logError(v1,"invalid number format");
				continue;
			}
			if(v4<0||v4>2) {
				logError(v1,"invalid grief level");
				continue;
			}
			if(v4==dragonGriefing) {
				continue;
			}
			griefing_dim.put(v3, v4);
		}
	}

	public static int get(World wi) {
		Integer v1=griefing_dim.get(wi.provider.getDimension());
		return null==v1?griefing_default:v1;
	}

	public static boolean canGrief(World w, boolean weak) {
    	Integer v1=griefing_dim.get(w.provider.getDimension());
    	int v2=((null==v1)?griefing_default:v1);
    	return (weak?(v2==0):(v2<2));
	}

}
