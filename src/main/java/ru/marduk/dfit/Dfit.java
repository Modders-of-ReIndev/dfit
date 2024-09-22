package ru.marduk.dfit;

import com.fox2code.foxloader.config.ConfigEntry;
import com.fox2code.foxloader.loader.Mod;
import ru.marduk.dfit.theme.TooltipThemes;

public abstract class Dfit extends Mod {
    DfitModConfig config = new DfitModConfig();

    @Override
    public void onPreInit() {
        setConfigObject(config);
    }

    public static class DfitModConfig {
        @ConfigEntry(configName = "Theme")
        public TooltipThemes.Theme theme = TooltipThemes.Theme.Default;
    }
}
