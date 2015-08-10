package net.kloudspace.pro.events;

import java.util.Random;

import net.kloudspace.pro.common.CommonProxy;
import net.kloudspace.pro.items.KItem;
import net.kloudspace.pro.properties.EventChanger1;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class KEventHandler {
	CommonProxy proxy = new CommonProxy();
	
	@SubscribeEvent
	public void livingDeath(LivingDeathEvent event) {
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
			NBTTagCompound playerData = new NBTTagCompound();
			((EventChanger1)(event.entity.getExtendedProperties("ironVar"))).saveNBTData(playerData);
			proxy.storeEntityData(((EntityPlayer)event.entity).getDisplayName(), playerData);
			EventChanger1.saveProxyData((EntityPlayer)event.entity);
		}
	}
	
	@SubscribeEvent
	public void joinEvent(EntityJoinWorldEvent event) {
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.entity;
			NBTTagCompound data = proxy.getEntityData(player.getDisplayName());
			if(data != null) {
				boolean var = data.getBoolean("ironVar");
				EventChanger1.setIronAge(player);
			}
		}
	}
	
	@SubscribeEvent
	public void onBreak(BlockEvent.HarvestDropsEvent event) {
		EntityPlayer player = null;
		if(event.harvester != null && event.harvester instanceof EntityPlayer) {
			player = (EntityPlayer)event.harvester;
		}
		if(!event.isSilkTouching) {
			if(player != null) {
				if(!EventChanger1.getIronVar(player)) {
					int i = getRandomInt();
					if(event.block instanceof BlockLeavesBase){
						if(i <= 4) {
							event.drops.add(new ItemStack(Items.stick));
							event.drops.add(new ItemStack(KItem.leaf));
							event.drops.trimToSize();
						}
					}
					if(event.block instanceof BlockDirt) {
						event.drops.clear();
						event.drops.add(new ItemStack(Blocks.dirt));
						if(i <= 2){
							event.drops.add(new ItemStack(Items.flint));
						}
					}
					if(event.block instanceof BlockGrass) {
						event.drops.clear();
						event.drops.add(new ItemStack(Blocks.dirt));
						if(i <= 2) {
							event.drops.add(new ItemStack(Items.flint));
						}
					}
					if(event.block.getMaterial() == Material.wood) {
						if(player.inventory.getCurrentItem() != null) {
							Item tool = player.inventory.getCurrentItem().getItem();
							if(!(tool instanceof ItemAxe) && tool != Items.flint) {
								event.drops.clear();
							}
						} else {
							event.drops.clear();
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void blockEvent(BlockEvent.BreakEvent event) {
		if(!event.world.isRemote && event.getPlayer() != null) {
			
		}
	}
	
	@SubscribeEvent
	public void breakSpeed(PlayerEvent.BreakSpeed event) {
		if(!event.entity.worldObj.isRemote && event.entityPlayer != null) {
			if(event.block.getMaterial() == Material.wood) {
				if(event.entityPlayer.inventory.getCurrentItem() != null) {
					if(!(event.entityPlayer.inventory.getCurrentItem().getItem() instanceof ItemAxe)) {
						event.newSpeed = event.originalSpeed/10;
					} else if(event.entityPlayer.inventory.getCurrentItem().getItem() == Items.flint) {
						event.newSpeed = event.originalSpeed*2;
					}  else {
						event.newSpeed = event.originalSpeed;
					}
				} else {
					event.newSpeed = event.originalSpeed/10;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void pickup(EntityItemPickupEvent event) {
		if(event.entityPlayer != null) {
			if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(Blocks.iron_ore)) {
				EntityPlayer player = (EntityPlayer)event.entityPlayer;
				EventChanger1.setIronAge(player);
			}
			
		}
	}
	
	public int getRandomInt() {
		Random rand = new Random();
		int i = rand.nextInt(10) + 1;
		return i;
	}

}
