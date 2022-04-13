package onesandzeros.doot.entities;

import onesandzeros.doot.Doot;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class TrumpetSkeletonRenderer extends HumanoidMobRenderer<TrumpetSkeletonEntity, TrumpetSkeletonModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Doot.MODID,
            "textures/entity/trumpet_skeleton.png");

    public TrumpetSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context, new TrumpetSkeletonModel(context.bakeLayer(TrumpetSkeletonModel.SKELE_LAYER)), 1f);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(TrumpetSkeletonEntity entity) {
        return TEXTURE;
    }
}
