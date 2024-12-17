package me.perwollnt.helpers;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

@Getter
@Setter
@ToString
public class Settings {

    private static Dotenv dotenv = Dotenv.load();

    public static String getCookies() {
        return "session=%sess;ru=%ru".replace("%sess", dotenv.get("SESSION")).replace("%ru", dotenv.get("RU"));
    }

    public static HttpURLConnection BuildUrl(String urlString, String method) throws IOException {
        URL url = URI.create(urlString).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("User-Agent", "Perwollnt");

        connection.setRequestProperty("Cookie", Settings.getCookies());

        return connection;
    }

    public static HttpURLConnection BuildUrl(int day, String method) throws IOException {
        return BuildUrl(getUrl(day), method);
    }

    public static HttpURLConnection BuildUrl(int day) throws IOException {
        return BuildUrl(getUrl(day), "GET");
    }

    public static String getUrl(int day) {
        return "https://adventofcode.com/2024/day/" + day + "/input";
    }
}
