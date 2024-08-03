package com.cimdy.acmd;

import com.cimdy.acmd.component.DataComponentRegister;
import com.cimdy.acmd.event.ItemEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(ArmorCustomModelData.MODID)
public class ArmorCustomModelData
{
    public static final String MODID = "armor_custommodeldata";


    public ArmorCustomModelData(IEventBus modEventBus, ModContainer modContainer)
    {
        DataComponentRegister.DATA_COMPONENT_TYPES.register(modEventBus);

        NeoForge.EVENT_BUS.addListener(ItemEvent::ItemStackedOnOtherEvent);
        NeoForge.EVENT_BUS.addListener(ItemEvent::ItemTooltipEvent);

        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }
}
