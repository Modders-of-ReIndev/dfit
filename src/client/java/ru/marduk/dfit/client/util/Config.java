package ru.marduk.dfit.client.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Config {
    private static Config INSTANCE;
    public String bg;
    public String grad1;
    public String grad2;
    public boolean bevel = true;
    public boolean shadow = false;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config defaultOptions() {
        bg = "#100010";
        grad1 = "#5000ff";
        grad2 = "#28007F";

        return this;
    }

    public static void fetchConfig() throws IOException {
        Path path = Paths.get(".","config", "dafuqIsThis.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (!Files.exists(path)) {
            try (Writer w = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                gson.toJson(new Config().defaultOptions(), w);
            } catch (Throwable t) {
                System.err.println("Failed to write default mariadb config file.");
            }
        }

        INSTANCE = gson.fromJson(Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining(System.lineSeparator())), Config.class);
    }
}