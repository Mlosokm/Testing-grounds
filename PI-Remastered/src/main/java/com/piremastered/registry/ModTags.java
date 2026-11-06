package com.piremastered.registry;
import com.piremastered.PIRemastered;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
public final class ModTags {
 public static final class Items {
  public static final TagKey<Item> HIGH_TIER_FUELS=TagKey.create(Registries.ITEM,new ResourceLocation(PIRemastered.MOD_ID,"high_tier_fuels"));
  private Items(){}
 }
 private ModTags(){}
}
