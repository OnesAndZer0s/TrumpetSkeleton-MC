package onesandzeros.doot.entities;

import onesandzeros.doot.Doot;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;

public class TrumpetSkeletonModel extends SkeletonModel<TrumpetSkeletonEntity> {

    public static final String BODY = "body";

    public static ModelLayerLocation SKELE_LAYER = new ModelLayerLocation(
            new ResourceLocation(Doot.MODID, "trumpet_skeleton"), BODY);

    // public static LayerDefinition createBodyLayer() {
    //     MeshDefinition meshdefinition = createMesh(CubeDeformation.NONE, 0.6f);
    //     return LayerDefinition.create(meshdefinition, 64, 32);
    // }

    public TrumpetSkeletonModel(ModelPart part) {
        super(part);
    }
}
