package me.perwollnt.d1;

import me.perwollnt.helpers.Settings;
import me.perwollnt.helpers.Tuple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DayOneReader {

    public static Tuple<List<String>, List<String>> read() {

        List<String> column1 = new ArrayList<>();
        List<String> column2 = new ArrayList<>();

        try {
            HttpURLConnection connection = Settings.BuildUrl(1);

            if (connection.getResponseCode() == 200) {
                // Read response
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(" {3}"); // Split 3 spaces
                        if (parts.length == 2) {
                            column1.add(parts[0]);
                            column2.add(parts[1]);
                        }
                    }
                }
            } else {
                System.out.println("Failed to fetch input. HTTP code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Logger.getLogger(DayOneReader.class.getName()).severe(e.getMessage());
        }

        return new Tuple<>(column1, column2);
    }
}
