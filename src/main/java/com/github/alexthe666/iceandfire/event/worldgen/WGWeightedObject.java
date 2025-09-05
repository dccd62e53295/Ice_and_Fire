package com.github.alexthe666.iceandfire.event.worldgen;

import java.util.List;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class WGWeightedObject implements WGObject {
	public int weightSum;// generated
	
	public int weightDummy;
	public ObjectContainer[] sub;
	
	@Override
	public void fromJson(JsonObject p1) {
		{
			JsonArray v1=p1.getAsJsonArray("sub");
			List<ObjectContainer> v2=new ArrayList<>();
			for(JsonElement v3:v1) {
				if(!v3.isJsonObject()) {
					continue;
				}
				ObjectContainer v4=new ObjectContainer();
				v4.fromJson((JsonObject) v3);
				v2.add(v4);
			}
			this.sub=v2.toArray(new ObjectContainer[0]);
		}
		this.weightDummy=p1.get("weightDummy").getAsInt();
		this.calculateWeightSum();
	}
	
	private void calculateWeightSum() {
		int v1=this.weightDummy;
		for(ObjectContainer v2:this.sub) {
			v1+=v2.weight;
		}
		this.weightSum=v1;
	}

	@Override
	public JsonObject toJson() {
		JsonObject v1=new JsonObject();
		v1.addProperty("weightDummy", this.weightDummy);
		{
			JsonArray v2=new JsonArray();
			for(ObjectContainer v3:this.sub) {
				v2.add(v3.toJson());
			}
			v1.add("sub", v2);
		}
		return v1;
	}

	@Override
	public void generate(WGParam p1) {
		int v1=p1.random.nextInt(this.weightSum)-this.weightDummy;
		if(v1<0) {
			return;
		}
		ObjectContainer v2=null;
		for(ObjectContainer v3:this.sub) {
			v1-=v3.weight;
			if(v1<0) {
				v2=v3;
				break;
			}
		}
		if(null!=v2) {
			v2.generate(p1);
		}
	}

	public static class ObjectContainer implements WGObject{
		public int weight;
		public WGObject instance;

		public void build(int p1,WGObject p2) {
			this.weight=p1;
			this.instance=p2;
		}
		
		@Override
		public void fromJson(JsonObject p1) {
			WGObject v2=WorldGenUtil.JsonDeserialize(p1.getAsJsonObject("object"),WGObject.class);
			if(null==v2) {
				return;
			}
			this.instance=v2;
			this.weight=p1.get("weight").getAsInt();
		}

		@Override
		public JsonObject toJson() {
			JsonObject v1=new JsonObject();
			v1.add("object", WorldGenUtil.JsonSerialize(this.instance));
			v1.addProperty("weight", this.weight);
			return v1;
		}

		@Override
		public void generate(WGParam p1) {
			this.instance.generate(p1);
		}

	}
	
	public static class Builder{
		
		public List<ObjectContainer> sub=new ArrayList<>();
		public int weightDummy=0;
		public int weightTotal=0;
		
		public Builder push(WGObject p1,int weight) {
			ObjectContainer v1=new ObjectContainer();
			v1.build(weight, p1);
			sub.add(v1);
			return this;
		}
		
		public Builder dummy(int p1) {
			this.weightDummy=p1;
			return this;
		}
		
		public Builder dummyFromTotal(int p1) {
			this.weightTotal=p1;
			return this;
		}
		
		private void calcDummyFromTotal() {
			int v1=this.weightTotal;
			for(ObjectContainer v2:this.sub) {
				v1-=v2.weight;
			}
			if(v1<0) {
				throw new RuntimeException(
						this.getClass().getName()+
						" can not support a negative dummy weight, input total: "+
						this.weightTotal+" , dummy found: "+v1);
			}
			this.weightDummy=v1;
		}
		
		public WGWeightedObject build() {
			if(0!=this.weightTotal) {
				this.calcDummyFromTotal();
			}
			WGWeightedObject v1=new WGWeightedObject();
			v1.sub=this.sub.toArray(new ObjectContainer[0]);
			v1.weightDummy=this.weightDummy;
			v1.calculateWeightSum();
			return v1;
		}

	}

}
