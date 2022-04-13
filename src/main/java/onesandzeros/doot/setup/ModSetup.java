package onesandzeros.doot.setup;

import onesandzeros.doot.Doot;
import onesandzeros.doot.entities.TrumpetSkeletonEntity;
// import onesandzeros.doot.manasystem.data.ManaEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.IModBusEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Doot.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {

    public static void setup() {
        IEventBus bus = MinecraftForge.EVENT_BUS;
        // bus.addListener(Ores::onBiomeLoadingEvent);
        // bus.addGenericListener(Entity.class, ManaEvents::onAttachCapabilitiesPlayer);
        // bus.addListener(ManaEvents::onPlayerCloned);
        // bus.addListener(ManaEvents::onRegisterCapabilities);
        // bus.addListener(ManaEvents::onWorldTick);
    }

    public static void init(FMLCommonSetupEvent event) {
        SpawnPlacements.register(
                Registration.TRUMPET_SKELETON.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                TrumpetSkeletonEntity::checkMobSpawnRules);
        // Registry.register(Registry.enti)
    }

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(Registration.TRUMPET_SKELETON.get(), TrumpetSkeletonEntity.prepareAttributes().build());
    }
}
