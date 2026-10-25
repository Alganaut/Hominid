package com.alganaut.hominid.registry.datagen;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class HominidItemModelProvider extends ItemModelProvider {
    public HominidItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Hominid.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(HominidItems.MUSIC_DISC_HEMATOMA.get());
        basicItem(HominidItems.REMAINS_SMITHING_TEMPLATE.get());
        basicItem(HominidItems.GASOLINE_TANK.get());
        basicItem(HominidItems.FAMISHED_STOMACH.get());
    }
}