package com.github.alexthe666.iceandfire.event.worldgen.object;

import java.lang.reflect.InvocationTargetException;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.event.worldgen.WGObject;
import com.github.alexthe666.iceandfire.event.worldgen.WGParam;
import com.google.gson.JsonObject;

public class SEntityDragonDead implements WGObject {
	
	public static SEntityDragonDead of(Class<? extends EntityDragonBase> p1) throws NoSuchMethodException, SecurityException {
		SEntityDragonDead s = new SEntityDragonDead();
		s.setDragonClass(p1);
		return s;
	}
	
	public void setDragonClass(Class<? extends EntityDragonBase> p1) throws NoSuchMethodException, SecurityException {
		this.dragonClass = p1;
		this.dragonConstructor=this.dragonClass.getConstructor(net.minecraft.world.World.class);
	}

	@Override
	public void fromJson(JsonObject args) {
		try {
			Class<?> v1 = Class.forName(args.get("dragonClass").getAsString());
			if (EntityDragonBase.class.isAssignableFrom(v1)) {
				this.setDragonClass( (Class<? extends EntityDragonBase>) v1);
			}else {
				throw new ClassCastException(v1.getName()+" dose not instanceof "+EntityDragonBase.class.getName());
			}
		} catch (ClassNotFoundException|ClassCastException | NoSuchMethodException | SecurityException e) {
			IceAndFire.logger.catching(e);
			IceAndFire.logger.error("Invalid dragon class for " + this.getClass().getName());
			IceAndFire.logger.error("Raw Value: ");
			IceAndFire.logger.error(args.get("dragonClass").getAsString());
		}
		this.ageBase=args.get("ageBase").getAsInt();
		this.ageRand=args.get("ageRand").getAsInt();
		this.ageDeathDiv=args.get("ageDeathDiv").getAsInt();
		this.deadProgress=args.get("deadProgress").getAsInt();
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1=new JsonObject();
		v1.addProperty("dragonClass", this.dragonClass.getName());
		v1.addProperty("ageBase", this.ageBase);
		v1.addProperty("ageRand", this.ageRand);
		v1.addProperty("ageDeathDiv", this.ageDeathDiv);
		v1.addProperty("deadProgress", this.deadProgress);
		return v1;
	}

	public Class<? extends EntityDragonBase> dragonClass = null;
	public java.lang.reflect.Constructor<? extends EntityDragonBase> dragonConstructor = null;

	public int ageBase=10;
	public int ageRand=100;
	public int ageDeathDiv=10;
	public int deadProgress=20;
	
	@Override
	public void generate(WGParam args) {
		EntityDragonBase thisdragon;
		try {
			thisdragon = this.dragonConstructor.newInstance(args.world);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			IceAndFire.logger.catching(e);
			IceAndFire.logger.error("Dragon generator spawn failed at " + this.getClass().getName());
			IceAndFire.logger.error("Dragon class: "+this.dragonClass.getName());
			return;
		}
        thisdragon.setPosition(args.x, args.surfacePos.getY() + 1, args.z);
        int dragonage = this.ageBase + args.random.nextInt(this.ageRand);
        thisdragon.growDragon(dragonage);
        thisdragon.modelDeadProgress = this.deadProgress;
        thisdragon.setModelDead(true);
        thisdragon.setDeathStage(dragonage / this.ageDeathDiv);
        thisdragon.rotationYaw = args.random.nextInt(360);
        args.world.spawnEntity(thisdragon);
	}

}
