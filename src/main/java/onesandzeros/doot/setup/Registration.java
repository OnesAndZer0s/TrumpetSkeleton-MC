package onesandzeros.doot.setup;

import onesandzeros.doot.entities.TrumpetSkeletonEntity;
import onesandzeros.doot.item.TrumpetItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static onesandzeros.doot.Doot.MODID;

public class Registration {

        private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
        private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
                        MODID);
        private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(
                        ForgeRegistries.ENTITIES,
                        MODID);

        public static void init() {
                IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
                ITEMS.register(bus);
                ENTITIES.register(bus);
                SOUNDS.register(bus);

        }

        public static final RegistryObject<EntityType<TrumpetSkeletonEntity>> TRUMPET_SKELETON = ENTITIES.register(
                        "trumpet_skeleton",
                        () -> EntityType.Builder.of(TrumpetSkeletonEntity::new, MobCategory.MONSTER)
                                        .sized(0.6f, 1.99f)
                                        .clientTrackingRange(8)
                                        .build(new ResourceLocation(MODID, "trumpet_skeleton").toString()));
        public static final RegistryObject<Item> TRUMPET_SKELETON_EGG = ITEMS.register("trumpet_skeleton_egg",
                        () -> new ForgeSpawnEggItem(TRUMPET_SKELETON, 0xCCC198, 0x665D48,
                                        new Properties().tab(CreativeModeTab.TAB_MISC)));
        public static final RegistryObject<Item> TRUMPET = ITEMS.register("trumpet",
                        () -> new TrumpetItem(new Properties().durability(Config.TRUMPET_DURABILITY.get())
                                        .tab(CreativeModeTab.TAB_COMBAT)));
        public static final RegistryObject<SoundEvent> TRUMPET_SOUND = SOUNDS.register("item.trumpet.use",
                        () -> new SoundEvent(new ResourceLocation(MODID, "item.trumpet.use")));
}
