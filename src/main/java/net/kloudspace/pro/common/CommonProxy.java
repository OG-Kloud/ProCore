package net.kloudspace.pro.common;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;

public class CommonProxy {
	
	private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();

	public static void storeEntityData(String saveKey, NBTTagCompound savedData) {
		extendedEntityData.put(saveKey, savedData);
	}

	public static NBTTagCompound getEntityData(String saveKey) {
		return extendedEntityData.remove(saveKey);
	}
	
	

}
