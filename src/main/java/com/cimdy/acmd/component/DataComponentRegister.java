package com.cimdy.acmd.component;

import com.cimdy.acmd.ArmorCustomModelData;
import com.cimdy.acmd.codec.ArmorShaping;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DataComponentRegister {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(ArmorCustomModelData.MODID);

    public static final Supplier<DataComponentType<Integer>> CUSTOM_MODEL_DATA = DATA_COMPONENT_TYPES.register(
            "custom_model_data", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());

    public static final Supplier<DataComponentType<ArmorShaping>> MODEL_RESOURCE = DATA_COMPONENT_TYPES.register(
            "model_resource", () -> DataComponentType.<ArmorShaping>builder().persistent(ArmorShaping.CODEC).networkSynchronized(ArmorShaping.STREAM_CODEC).build());
}
