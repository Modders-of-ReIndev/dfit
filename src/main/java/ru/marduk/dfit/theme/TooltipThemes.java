package ru.marduk.dfit.theme;

import java.util.HashMap;

public class TooltipThemes {
    private static final HashMap<Theme, TooltipTheme> themes = new HashMap<>();

    static {
        themes.put(Theme.Default, new TooltipTheme());
    }

    public static TooltipTheme get(Theme key) {
        return themes.get(key);
    }
    
    public static void add(Theme key, TooltipTheme theme) {
        themes.put(key, theme);
    }

    public enum Theme {
        Default
    }
}
