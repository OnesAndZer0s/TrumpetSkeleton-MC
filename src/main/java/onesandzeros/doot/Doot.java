package onesandzeros.doot;

import onesandzeros.doot.setup.Config;
import onesandzeros.doot.setup.ModSetup;
import onesandzeros.doot.setup.ClientSetup;
import onesandzeros.doot.setup.Registration;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.chunk.LevelChunk.EntityCreationType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Doot.MODID)
@Mod.EventBusSubscriber(modid = Doot.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Doot {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "doot";

    public Doot() {

        // Register the deferred registry
        ModSetup.setup();
        Registration.init();
        Config.register();

        // Register the setup method for modloading
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        modbus.addListener(ModSetup::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modbus.addListener(ClientSetup::init));
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBiomeLoad(final BiomeLoadingEvent event) {
        int spawnWeight = Config.SPAWN_WEIGHT.get();

        if (spawnWeight > 0) {
            int skeletonWeight = 0;

            for (SpawnerData spawner : event.getSpawns().getSpawner(MobCategory.MONSTER)) {
                if (spawner.type == EntityType.SKELETON)
                    skeletonWeight += spawner.getWeight().asInt();
            }

            // LOGGER.info("THISHAPPENED");
            // System.out.println("THISHAPPENED");
            if (skeletonWeight > 0) {
                event.getSpawns().addSpawn(
                        MobCategory.MONSTER,
                        new SpawnerData(Registration.TRUMPET_SKELETON.get(),
                                spawnWeight,
                                Config.MIN_GROUP_SIZE.get(),
                                Config.MAX_GROUP_SIZE.get()));
            }
        } else {
            LOGGER.info("Trumpet skeletons have been configured not to spawn; not registering spawn entries.");
        }
    }
}
