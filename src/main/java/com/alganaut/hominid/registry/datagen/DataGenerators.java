package com.alganaut.hominid.registry.datagen;


import com.alganaut.hominid.Hominid;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;

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
                new LootTableProvider(
                        output,
                        Collections.emptySet(),
                        List.of(
                                new LootTableProvider.SubProviderEntry(HominidBlockLootTableProvider::new, LootContextParamSets.BLOCK)
                        ),
                        provider
                )
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
                event.includeClient(),
                new HominidItemModelProvider(output, helper)
        );

        generator.addProvider(
                event.includeClient(),
                new HominidBlockStateProvider(output, helper)
        );

        generator.addProvider(
                event.includeServer(),
                new HominidDatapackProvider(output, provider)
        );
    }
}
