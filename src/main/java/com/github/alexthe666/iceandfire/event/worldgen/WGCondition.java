package com.github.alexthe666.iceandfire.event.worldgen;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;


public interface WGCondition extends JsonSerializable, Predicate<WGParam> {
	public boolean apply(@Nullable WGParam args);
}
