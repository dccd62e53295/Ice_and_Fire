package com.github.alexthe666.iceandfire.event.worldgen;

import javax.annotation.Nonnull;

import com.google.gson.JsonObject;

public abstract interface JsonSerializable{
	/* reserved field */
	public static final String TypeKey="__class__";
	
	public void fromJson(@Nonnull JsonObject args);
	@Nonnull
	public JsonObject toJson();
}
