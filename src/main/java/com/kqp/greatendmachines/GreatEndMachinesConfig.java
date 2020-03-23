package com.kqp.greatendmachines;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class GreatEndMachinesConfig {
    private static final File FILE = new File("./config/greatendmachines.json");

    public Boolean fishTrapReqSeeds = true;
    public Float shulkFurnCookTimeMult = 0.125F;
    public Float shulkFurnFuelTimeMult = 0.5F;
    public Integer proxIntakeCount = 1;

    public static void save(GreatEndMachinesConfig cfg) {
        checkFileExistence();

        try (FileWriter writer = new FileWriter(FILE)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(cfg, writer);
        } catch (JsonIOException | IOException e) {
            e.printStackTrace();
        }
    }

    public static GreatEndMachinesConfig load() {
        if (!FILE.exists()) {
            save(new GreatEndMachinesConfig());
        }

        Gson gson = new Gson();
        try {
            return gson.fromJson(new FileReader(FILE), GreatEndMachinesConfig.class);
        } catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return new GreatEndMachinesConfig();
    }

    private static void checkFileExistence() {
        try {
            if (FILE.exists()) {
                FILE.getParentFile().mkdirs();
                FILE.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}