package com.cimdy.acmd.event;

import com.cimdy.acmd.codec.ArmorShaping;
import com.cimdy.acmd.component.DataComponentRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.ItemStackedOnOtherEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

public class ItemEvent {
    @SubscribeEvent
    public static void ItemStackedOnOtherEvent(ItemStackedOnOtherEvent event) {
        ClickAction clickAction = event.getClickAction();
        ItemStack carriedItem = event.getCarriedItem();
        ItemStack stackedOnItem = event.getStackedOnItem();
        Slot slot = event.getSlot();
        Player player = event.getPlayer();
        if (clickAction == ClickAction.SECONDARY && slot.getSlotIndex() < 36 || slot.getSlotIndex() > 39) {
            EquipmentSlot carriedEquipmentSlot = player.getEquipmentSlotForItem(carriedItem);
            EquipmentSlot stackedOnEquipmentSlot = player.getEquipmentSlotForItem(stackedOnItem);
            if(stackedOnEquipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR
                    && !stackedOnItem.is(ItemTags.SKULLS) && !carriedItem.is(ItemTags.SKULLS)
                    && carriedEquipmentSlot == stackedOnEquipmentSlot) {
                Holder<Item> shape = stackedOnItem.has(DataComponentRegister.MODEL_RESOURCE) ?
                        stackedOnItem.get(DataComponentRegister.MODEL_RESOURCE).itemStack() : stackedOnItem.getItemHolder();
                Holder<Item> carried = carriedItem.has(DataComponentRegister.MODEL_RESOURCE) ?
                        carriedItem.get(DataComponentRegister.MODEL_RESOURCE).itemStack() : carriedItem.getItemHolder();
                if(shape != carried) {
                    carriedItem.set(DataComponentRegister.MODEL_RESOURCE, new ArmorShaping(shape));
                    stackedOnItem.shrink(1);
                }
            }
        }
    }

    public static void ItemTooltipEvent(ItemTooltipEvent event) {
        if(event.getItemStack().has(DataComponentRegister.MODEL_RESOURCE)) {
            Holder<Item> itemHolder = event.getItemStack().get(DataComponentRegister.MODEL_RESOURCE).itemStack();
            List<Component> toolTip = event.getToolTip();
            toolTip.add(Component.empty());
            toolTip.add(Component.translatable("armor.shape").append(": ").append(itemHolder.value().getDefaultInstance().getDisplayName()).withStyle(ChatFormatting.GRAY));
        }
    }
}
