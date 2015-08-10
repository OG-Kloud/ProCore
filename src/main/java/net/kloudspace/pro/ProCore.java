package net.kloudspace.pro;

import net.kloudspace.pro.events.KEventHandler;
import net.kloudspace.pro.events.PlayerConstructingEvents;
import net.kloudspace.pro.items.KItem;
import net.minecraft.init.Items;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = ProCore.MODID, version = ProCore.VERSION, name = ProCore.NAME)
public class ProCore {
    public static final String MODID = "procore";
    public static final String NAME = "Pro Core";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(new KEventHandler());
    	MinecraftForge.EVENT_BUS.register(new PlayerConstructingEvents());
    	Items.flint.setHarvestLevel("axe", 0);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    	GameRegistry.registerItem(KItem.leaf, "kleaf");
    }
}
