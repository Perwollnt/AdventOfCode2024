package me.perwollnt.d6;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.perwollnt.d3.DayThreeReader;
import me.perwollnt.helpers.Settings;
import me.perwollnt.helpers.Tuple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class DaySixReader {
    public static List<List<String>> read() {
        List<List<String>> data = new ArrayList<>();
        try {
            HttpURLConnection connection = Settings.BuildUrl(6);

            if (connection.getResponseCode() == 200) {
                // Read response
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        List<String> inList = new ArrayList<>(Arrays.asList(line.split("")));
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
}