package com.github.alexthe666.iceandfire.event.worldgen;

import javax.annotation.Nonnull;

public interface WGObject extends JsonSerializable{
	public void generate(@Nonnull WGParam args);
}
