package com.alganaut.hominid.registry.datagen;


import com.alganaut.hominid.Hominid;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.ibm.icu.impl.CurrencyData.provider;

@EventBusSubscriber(modid = Hominid.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var helper = event.getExistingFileHelper();
        var provider = event.getLookupProvider();


        generator.addProvider(
                event.includeServer(),
                new HominidRecipeProvider(output, provider)
        );

        var blockTagsProvider = new HominidBlockTagProvider(output, provider, helper);

        generator.addProvider(event.includeServer(), blockTagsProvider);

        generator.addProvider(
                event.includeServer(),
                new HominidItemTagProvider(
                        output,
                        provider,
                        blockTagsProvider.contentsGetter(),
                        helper
                )
        );

        generator.addProvider(
                event.includeServer(),
                new HominidDatapackProvider(output, provider)
        );
    }
}
