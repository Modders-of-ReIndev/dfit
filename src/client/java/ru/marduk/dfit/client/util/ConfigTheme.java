package ru.marduk.dfit.client.util;

public class ConfigTheme {
    public static final ConfigTheme INSTANCE = new ConfigTheme();
    public final int bg, grad1, grad2;
    public final boolean bevel, shadow;

    ConfigTheme() {
        int bgValue;
        int grad1Value;
        int grad2Value;
        boolean bevelVal;
        boolean shadowVal;

        try {
            Config.fetchConfig();

            Config conf = Config.getInstance();

            byte[] processedBackground = DrawUtil.toByteArray(Integer.parseInt(conf.bg.substring(1), 16));
            byte[] processedGrad1 = DrawUtil.toByteArray(Integer.parseInt(conf.grad1.substring(1), 16));
            byte[] processedGrad2 = DrawUtil.toByteArray(Integer.parseInt(conf.grad2.substring(1), 16));
            processedBackground[0] = (byte)0xf0;
            processedGrad1[0] = (byte)0x50;
            processedGrad2[0] = (byte)0x50;

            bgValue = DrawUtil.fromByteArray(processedBackground);
            grad1Value = DrawUtil.fromByteArray(processedGrad1);
            grad2Value = DrawUtil.fromByteArray(processedGrad2);
            bevelVal = conf.bevel;
            shadowVal = conf.shadow;
        } catch (Exception e) {
            System.err.println("Failed to get config");
            e.printStackTrace();
            bgValue = 0xf0100010;
            grad1Value = 0x505000ff;
            grad2Value = 0x5028007F;
            bevelVal = true;
            shadowVal = false;
        }

        bg = bgValue;
        grad1 = grad1Value;
        grad2 = grad2Value;
        bevel = bevelVal;
        shadow = shadowVal;
    }
}
