package com.alganaut.hominid.registry.datagen;


import com.alganaut.hominid.Hominid;
import net.minecraft.data.tags.TagsProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Hominid.MODID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var helper = event.getExistingFileHelper();
        var provider = event.getLookupProvider();


        generator.addProvider(
                event.includeServer(),
                new HominidItemTagProvider(
                        output,
                        provider,
                        CompletableFuture.completedFuture(TagsProvider.TagLookup.empty()),
                        helper
                )
        );

        generator.addProvider(
                event.includeClient(),
                new HominidItemModelProvider(output, helper)
        );

        generator.addProvider(
                event.includeServer(),
                new HominidDatapackProvider(output, provider)
        );
    }
}
