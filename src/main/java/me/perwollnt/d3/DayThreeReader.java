package me.perwollnt.d3;

import me.perwollnt.helpers.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

public class DayThreeReader {
    public static String read() {
        String urlString = "https://adventofcode.com/2024/day/3/input";

        StringBuilder data = new StringBuilder();

        try {
            HttpURLConnection connection = Settings.BuildUrl(3);

            if (connection.getResponseCode() == 200) {
                // Read response
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        data.append(line);
                    }
                }
            } else {
                System.out.println("Failed to fetch input. HTTP code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Logger.getLogger(DayThreeReader.class.getName()).severe(e.getMessage());
        }

        return data.toString();
    }
}
