package com.github.alexthe666.iceandfire.event.worldgen;

public interface WGDim extends WGObject{
int[] getDims();
boolean isDefault();
boolean isEmpty();
}
