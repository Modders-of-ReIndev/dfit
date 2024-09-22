package ru.marduk.dfit;

import com.fox2code.foxloader.config.ConfigEntry;
import com.fox2code.foxloader.loader.Mod;

public class Dfit extends Mod {
    public static final DfitModConfig CONFIG = new DfitModConfig();

    @Override
    public void onPreInit() {
        setConfigObject(CONFIG);
    }

    public static class DfitModConfig {
        @ConfigEntry(configName = "Rat eat cheese")
        public boolean eatCheese = true;
    }
}
