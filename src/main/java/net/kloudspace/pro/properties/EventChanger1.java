package net.kloudspace.pro.properties;

import net.kloudspace.pro.common.CommonProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class EventChanger1 implements IExtendedEntityProperties{
	
	private static final String ironVar = "ironVar";
	private static boolean hasGotIron;
	private final EntityPlayer player;
	
	public EventChanger1(EntityPlayer player) {
		this.player = player;
		}
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(ironVar, new EventChanger1(player));
	}

	public static boolean getIronVar(EntityPlayer player) {
		EventChanger1 var = get(player);
		return var.hasGotIron;
	}

	public static EventChanger1 get(EntityPlayer player) {
		return (EventChanger1) player.getExtendedProperties(ironVar);
	}
	public static void setIronAge(EntityPlayer player) {
		EventChanger1 ec1 = get(player);
		ec1.hasGotIron = true;
	}
	
//	public static boolean isIronAge(EntityPlayer player) {
//		EventChanger1 ec1 = get(player);
//		return ec1.hasGotIron;
//	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound prop = new NBTTagCompound();
		compound.setBoolean(ironVar, hasGotIron);
		compound.setTag(ironVar, prop);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound prop = (NBTTagCompound)compound.getTag(ironVar);
		this.hasGotIron = compound.getBoolean(ironVar);
		System.out.println("Has Entered The Iron Age: " + hasGotIron);
	}

	@Override
	public void init(Entity entity, World world) {
		
	}
	private static String getSaveKey(EntityPlayer player) {
		return player.getDisplayName() + ":" + ironVar;
	}
	
	public static void saveProxyData(EntityPlayer player) {
		EventChanger1 ec1 = get(player);
		NBTTagCompound savedData = new NBTTagCompound();
		ec1.saveNBTData(savedData);
		CommonProxy.storeEntityData(getSaveKey(player), savedData);
	}
	
	public static void loadProxyData(EntityPlayer player) {
		EventChanger1 ec1 = get(player);
		NBTTagCompound savedData = CommonProxy.getEntityData(getSaveKey(player));
		if(savedData != null) {
			ec1.loadNBTData(savedData);
		}
	}

}
