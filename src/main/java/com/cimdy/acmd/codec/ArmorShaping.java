package com.cimdy.acmd.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record ArmorShaping(Holder<Item> itemStack){
    public static final Codec<ArmorShaping> CODEC = RecordCodecBuilder.create(itemstack ->
            itemstack.group(
                    ItemStack.ITEM_NON_AIR_CODEC.fieldOf("armor_shaping").forGetter(ArmorShaping::itemStack)
            ).apply(itemstack, ArmorShaping::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ArmorShaping> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.holderRegistry(Registries.ITEM) ,ArmorShaping::itemStack, ArmorShaping::new);

}
