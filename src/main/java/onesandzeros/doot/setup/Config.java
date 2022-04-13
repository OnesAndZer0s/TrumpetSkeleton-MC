package onesandzeros.doot.setup;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Config {
    public static final String CATEGORY_SPAWNING = "spawning";
    public static final String CATEGORY_TRUMPET = "spawning";

    public static ForgeConfigSpec CONFIG;
    public static ForgeConfigSpec.DoubleValue RELATIVE_SPAWN_WEIGHT;
    public static ForgeConfigSpec.IntValue MIN_GROUP_SIZE;
    public static ForgeConfigSpec.IntValue MAX_GROUP_SIZE;

    public static ForgeConfigSpec.IntValue TRUMPET_DURABILITY;

    public static void register() {
        // registerServerConfigs();
        registerCommonConfigs();
        // registerClientConfigs();
    }

    private static void registerClientConfigs() {
        // ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
        // PowergenConfig.registerClientConfig(CLIENT_BUILDER);
        // ManaConfig.registerClientConfig(CLIENT_BUILDER);
        // ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
    }

    private static void registerCommonConfigs() {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("Trumpet Skeleton spawn settings").push(CATEGORY_SPAWNING);

        RELATIVE_SPAWN_WEIGHT = COMMON_BUILDER
                .comment(
                        "Spawn weight relative to normal skeleton spawns. Multiplied with normal skeleton spawn weight.",
                        "For example, the default value of 0.05 (aka 1/20) means that normal skeletons will be 20 times more common than trumpet skeletons.")
                .defineInRange("relative_spawn_weight", 0.05, 0, Integer.MAX_VALUE);
        MIN_GROUP_SIZE = COMMON_BUILDER
                .comment(
                        "Minimum Trumpet Skeletons in group.")
                .defineInRange("min_group", 1, 1, Integer.MAX_VALUE);
        MAX_GROUP_SIZE = COMMON_BUILDER
                .comment(
                        "Maximum Trumpet Skeletons in group.")
                .defineInRange("max_group", 1, 1, Integer.MAX_VALUE);

        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Trumpet").push(CATEGORY_TRUMPET);

        TRUMPET_DURABILITY = COMMON_BUILDER
                .comment(
                        "Trumpet durability.")
                .defineInRange("relative_spawn_weight", 420, 1, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();

        // COMMON_BUILDER.comment("Trumpet Skeleton spawn settings").push();

        CONFIG = COMMON_BUILDER.build();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_BUILDER.build());
    }

    private static void registerServerConfigs() {
        // ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        // GeneratorConfig.registerServerConfig(SERVER_BUILDER);
        // PowergenConfig.registerServerConfig(SERVER_BUILDER);
        // ManaConfig.registerServerConfig(SERVER_BUILDER);
        // ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build());
    }

}
