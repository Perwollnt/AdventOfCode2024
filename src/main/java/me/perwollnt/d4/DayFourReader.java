package me.perwollnt.d4;

import me.perwollnt.d3.DayThreeReader;
import me.perwollnt.helpers.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DayFourReader {
    public static List<List<String>> read() {
        List<List<String>> data = new ArrayList<>();

        try {
            HttpURLConnection connection = Settings.BuildUrl(4);

            if (connection.getResponseCode() == 200) {
                // Read response
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        List<String> inList = List.of(line.split(""));
                        data.add(inList);
                    }
                }
            } else {
                System.out.println("Failed to fetch input. HTTP code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Logger.getLogger(DayThreeReader.class.getName()).severe(e.getMessage());
        }

        return data;
    }

    public static List<List<String>> readFakeDataForTesting() {
        List<String> row1 = List.of("M", "M", "S", "S");
        List<String> row2 = List.of("M", "A", "A", "S");
        List<String> row3 = List.of("M", "M", "A", "S");
        List<String> row4 = List.of("M", "M", "S", "S");

        return List.of(row1, row2, row3, row4);
    }
}