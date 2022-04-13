package onesandzeros.doot.entities;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import onesandzeros.doot.item.TrumpetItem;
import onesandzeros.doot.setup.Registration;

public class TrumpetAttackGoal<T extends net.minecraft.world.entity.Mob & RangedAttackMob> extends Goal {
  private final T mob;
  private final double speedModifier;
  private int attackIntervalMin;
  private final float attackRadiusSqr;
  private int attackTime = -1;
  private int seeTime;
  private boolean strafingClockwise;
  private boolean strafingBackwards;
  private int strafingTime = -1;

  public <M extends Monster & RangedAttackMob> TrumpetAttackGoal(M pMob, double pSpeedModifier, int pAttackIntervalMin,
      float pAttackRadius) {
    this((T) pMob, pSpeedModifier, pAttackIntervalMin, pAttackRadius);
  }

  public TrumpetAttackGoal(T pMob, double pSpeedModifier, int pAttackIntervalMin, float pAttackRadius) {
    this.mob = pMob;
    this.speedModifier = pSpeedModifier;
    this.attackIntervalMin = pAttackIntervalMin;
    this.attackRadiusSqr = pAttackRadius * pAttackRadius;
    this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
  }

  public void setMinAttackInterval(int pAttackCooldown) {
    this.attackIntervalMin = pAttackCooldown;
  }

  /**
   * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
   * method as well.
   */
  public boolean canUse() {
    return this.mob.getTarget() == null ? false : this.isHoldingTrumpet();
  }

  protected boolean isHoldingTrumpet() {
    return this.mob.isHolding(is -> is.getItem() instanceof TrumpetItem);
  }

  /**
   * Returns whether an in-progress EntityAIBase should continue executing
   */
  public boolean canContinueToUse() {
    return (this.canUse() || !this.mob.getNavigation().isDone()) && this.isHoldingTrumpet();
  }

  /**
   * Execute a one shot task or start executing a continuous task
   */
  public void start() {
    super.start();
    this.mob.setAggressive(true);
  }

  /**
   * Reset the task's internal state. Called when this task is interrupted by another one
   */
  public void stop() {
    super.stop();
    this.mob.setAggressive(false);
    this.seeTime = 0;
    this.attackTime = -1;
    this.mob.stopUsingItem();
  }

  public boolean requiresUpdateEveryTick() {
    return true;
  }

  /**
   * Keep ticking a continuous task that has already been started
   */
  public void tick() {
    LivingEntity livingentity = this.mob.getTarget();
    if (livingentity != null) {
      double d0 = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
      boolean flag = this.mob.getSensing().hasLineOfSight(livingentity);
      boolean flag1 = this.seeTime > 0;
      if (flag != flag1) {
        this.seeTime = 0;
      }

      if (flag) {
        ++this.seeTime;
      } else {
        --this.seeTime;
      }

      if (!(d0 > (double) this.attackRadiusSqr) && this.seeTime >= 20) {
        this.mob.getNavigation().stop();
        ++this.strafingTime;
      } else {
        this.mob.getNavigation().moveTo(livingentity, this.speedModifier);
        this.strafingTime = -1;
      }

      if (this.strafingTime >= 20) {
        if ((double) this.mob.getRandom().nextFloat() < 0.3D) {
          this.strafingClockwise = !this.strafingClockwise;
        }

        if ((double) this.mob.getRandom().nextFloat() < 0.3D) {
          this.strafingBackwards = !this.strafingBackwards;
        }

        this.strafingTime = 0;
      }

      if (this.strafingTime > -1) {
        if (d0 > (double) (this.attackRadiusSqr * 0.75F)) {
          this.strafingBackwards = false;
        } else if (d0 < (double) (this.attackRadiusSqr * 0.25F)) {
          this.strafingBackwards = true;
        }

        this.mob.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
        this.mob.lookAt(livingentity, 30.0F, 30.0F);
      } else {
        this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
      }

      if (this.mob.isUsingItem()) {
        if (!flag && this.seeTime < -60) {
          this.mob.stopUsingItem();
        }
      } else if (--this.attackTime <= 0 && this.seeTime >= -60) {
        this.mob.startUsingItem(ProjectileUtil.getWeaponHoldingHand(this.mob, Registration.TRUMPET.get()));
        this.attackTime = this.mob.level.random.nextInt(this.attackIntervalMin);
      }
    }
  }
}