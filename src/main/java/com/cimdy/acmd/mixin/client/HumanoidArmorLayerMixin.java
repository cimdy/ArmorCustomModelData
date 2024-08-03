package com.cimdy.acmd.mixin.client;

import com.cimdy.acmd.ArmorCustomModelData;
import com.cimdy.acmd.codec.ArmorShaping;
import com.cimdy.acmd.component.DataComponentRegister;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
    public HumanoidArmorLayerMixin(RenderLayerParent<T, M> pRenderer) {
        super(pRenderer);
    }

    @Redirect(method = "renderArmorPiece", at = @At(value = "INVOKE",
            target = "Lnet/neoforged/neoforge/client/ClientHooks;" +
                    "getArmorTexture(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ArmorMaterial$Layer;" +
                    "ZLnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/resources/ResourceLocation;"))
    private ResourceLocation renderArmorPiece(Entity entity, ItemStack armor, ArmorMaterial.Layer layer, boolean innerModel, EquipmentSlot slot){
        if(armor.has(DataComponents.CUSTOM_MODEL_DATA)) {
            ResourceLocation resourceLocation = layer.texture(true);
            String i = String.valueOf(armor.get(DataComponents.CUSTOM_MODEL_DATA).value());
            return ResourceLocation.fromNamespaceAndPath(resourceLocation.getNamespace(), resourceLocation.getPath().replace(".png", "." + i + ".png"));
        }else if(armor.has(DataComponentRegister.CUSTOM_MODEL_DATA)){
            String i = String.valueOf(armor.get(DataComponentRegister.CUSTOM_MODEL_DATA));
            if(armor.is(ItemTags.HEAD_ARMOR) && !armor.is(ItemTags.SKULLS)) {
                return ResourceLocation.fromNamespaceAndPath(ArmorCustomModelData.MODID, "textures/head" + "." + i + ".png");
            }else {
                return ResourceLocation.fromNamespaceAndPath(ArmorCustomModelData.MODID, "textures/body" + 1 + "." + i + ".png");
            }
        }

        return net.neoforged.neoforge.client.ClientHooks.getArmorTexture(entity, armor, layer, innerModel, slot);
    }

    @Redirect(method = "renderArmorPiece", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack renderArmorPiece(LivingEntity living, EquipmentSlot equipmentSlot){
        ItemStack itemStack = living.getItemBySlot(equipmentSlot);
        if(itemStack.has(DataComponentRegister.MODEL_RESOURCE)) {
            return itemStack.getOrDefault(DataComponentRegister.MODEL_RESOURCE, new ArmorShaping(ItemStack.EMPTY.getItemHolder())).itemStack().value().getDefaultInstance();
        }
        return itemStack;
    }


    @Unique
    public void render(PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }
}
