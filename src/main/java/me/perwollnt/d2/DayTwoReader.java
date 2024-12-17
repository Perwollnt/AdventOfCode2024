package me.perwollnt.d2;

import me.perwollnt.helpers.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class DayTwoReader {


    public static List<List<Integer>> read() {

        List<List<Integer>> data = new ArrayList<>();

        try {
            HttpURLConnection connection = Settings.BuildUrl(2);

            if (connection.getResponseCode() == 200) {
                // Read response
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        List<Integer> inList = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
                        data.add(inList);
                    }
                }
            } else {
                System.out.println("Failed to fetch input. HTTP code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Logger.getLogger(DayTwoReader.class.getName()).severe(e.getMessage());
        }

        return data;
    }
}
