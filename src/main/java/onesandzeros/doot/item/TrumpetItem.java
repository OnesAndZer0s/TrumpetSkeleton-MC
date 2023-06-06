package onesandzeros.doot.item;

import java.util.List;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import onesandzeros.doot.setup.Config;
import onesandzeros.doot.setup.Registration;

public class TrumpetItem extends Item {

  public TrumpetItem(Properties properties) {
    super(properties);
  }

  public int getEnchantmentValue() {
    return 1;
  }

  public static void scare(Level world, LivingEntity user) {
    if (!world.isClientSide) {
      List<LivingEntity> spooked = world.getEntitiesOfClass(
          LivingEntity.class,
          user.getBoundingBox().inflate(
              Config.TRUMPET_RANGE.get()
                  + EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, user.getMainHandItem()) * 2));

      for (LivingEntity entity : spooked) {
        if (entity == user)
          continue;

        double punch = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, user.getMainHandItem()) * 2;

        double deltaX = entity.getX() - user.getX() + world.random.nextDouble() - world.random.nextDouble();
        double deltaZ = entity.getZ() - user.getZ() + world.random.nextDouble() - world.random.nextDouble();

        double distance = Math.sqrt((deltaX * deltaX) + (deltaZ * deltaZ));

        entity.hurtMarked = true;
        entity.setLastHurtByMob(user);

        entity.push(
            (0.5 * deltaX + punch) / distance,
            (5 + punch) / (10 + distance),
            (0.5 + deltaZ + punch) / distance);
      }
    }
  }

  @Override
  public UseAnim getUseAnimation(ItemStack stack) {
    return UseAnim.DRINK;
  }

  public SoundEvent getDrinkingSound() {
    return null;
  }

  @Override
  public int getUseDuration(ItemStack stack) {
    return 55 - Math.min(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack) * 7, 35);
  }

  @Override
  public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
    super.onUsingTick(stack, player, count);

    int useTime = getUseDuration(stack) - count;
    if (useTime == 10 - Math.min(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack) * 2, 10)) {
      player.playSound(Registration.TRUMPET_SOUND.get(), 1f, 0.95F + player.level.random.nextFloat() * 0.1F);
      TrumpetItem.scare(player.level, player);
      stack.getItem().damageItem(stack, 1, player, (entity) -> entity.broadcastBreakEvent(player.getUsedItemHand()));
    } else if (useTime >= 15
        - Math.min(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack) * 2, 10)) {
      player.stopUsingItem();
    }
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level world, Player playerIn, InteractionHand handIn) {
    playerIn.startUsingItem(handIn);
    return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
    // }
  }

}