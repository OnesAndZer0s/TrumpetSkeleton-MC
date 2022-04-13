package onesandzeros.doot.mixin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import onesandzeros.doot.item.TrumpetItem;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

  @Inject(method = "canEnchant", at = @At("RETURN"), cancellable = true)
  private void canApply(ItemStack stack, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
    if (!callbackInfoReturnable.getReturnValue()) {
      Enchantment enchantment = (Enchantment) (Object) this;
      callbackInfoReturnable.setReturnValue(hasGoodEnchant(enchantment, stack));// || HoeHarvestingModule.canFortuneApply(enchantment, stack));
    }
  }

  private static boolean hasGoodEnchant(Enchantment enchantment, ItemStack stack) {
    return (enchantment == Enchantments.POWER_ARROWS || enchantment == Enchantments.PUNCH_ARROWS
        || enchantment == Enchantments.QUICK_CHARGE) && stack.getItem() instanceof TrumpetItem;
    // quick charge
    // power - range
    //punch - knockback
    // not knockback

  }

}