package me.perwollnt.d5;

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

public class DayFiveReader {
    public static ReadValue read() {
        List<List<Integer>> data = new ArrayList<>();
        List<Tuple<Integer, Integer>> data2 = new ArrayList<>();
        try {
            HttpURLConnection connection = Settings.BuildUrl(5);

            if (connection.getResponseCode() == 200) {
                // Read response
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // two kinds of data, one contains | the other does not.
                        if (line.contains("|")) {
                            // example: 1|3
                            try {
                                String[] parts = line.split("\\|");
                                Tuple<Integer, Integer> tuple = new Tuple<>(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                                data2.add(tuple);
                            } catch (Exception ex) {
                                System.out.println("Failed to parse line: " + line);
                            }
                        } else {
                            // example: 1,2,3,4,5
                            if (line.isEmpty()) continue;
                            List<Integer> list = new ArrayList<>(Arrays.stream(line.split(","))
                                    .map(Integer::parseInt)
                                    .toList());

                            data.add(list);
                        }
                    }
                }
            } else {
                System.out.println("Failed to fetch input. HTTP code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Logger.getLogger(DayThreeReader.class.getName()).severe(e.getMessage());
        }

        return new ReadValue(data2, data);
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    public static class ReadValue {
        private List<Tuple<Integer, Integer>> typeOne = new ArrayList<>();
        private List<List<Integer>> typeTwo = new ArrayList<>();
    }
}