package net.kloudspace.pro.events;

import net.kloudspace.pro.properties.EventChanger1;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerConstructingEvents {
	
	@SubscribeEvent
	public void onPlayerConstruction(EntityConstructing event) {
		if(event.entity instanceof EntityPlayer && EventChanger1.get((EntityPlayer) event.entity) == null) {
			EntityPlayer player = (EntityPlayer)event.entity;
			EventChanger1.register(player);
		}
	}

}
