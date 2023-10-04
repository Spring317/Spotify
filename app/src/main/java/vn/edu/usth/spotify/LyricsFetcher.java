package vn.edu.usth.spotify;

import android.os.Bundle;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.*;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LyricsFetcher {

    public static String fetchLyrics(String songTitle, String artistName) {
        try {
            // Construct the Genius API URL with your API key
            String apiKey = "VgEgarls_Ajcn1LUkhHIQp6qZAbjtXbzbDeB4kySvVOiNPu4E01dlzHWCvOcAQkR";
            String apiUrl = "https://api.genius.com/search?q=" +
                    songTitle + " " + artistName;
            // Make the HTTP request
            String jsonResponse = Network.fetchGeniusLyrics(apiUrl);
            // Parse the JSON response using Gson
            // You'll need to create appropriate classes for Gson parsing
            // Extract and return the lyrics from the JSON response
            String lyrics = parseLyricsFromJson(jsonResponse);
            return lyrics;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String parseLyricsFromJson(String jsonResponse) {
        // Implement Gson parsing here to extract lyrics
        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

        // Check if the response contains a 'response' key
        if (jsonObject.has("response")) {
            JsonObject response = jsonObject.getAsJsonObject("response");

            // Check if the response contains 'hits' (search results)
            if (response.has("hits")) {
                JsonArray hits = response.getAsJsonArray("hits");

                // Check if there are any search results
                if (hits.size() > 0) {
                    JsonObject firstHit = hits.get(0).getAsJsonObject();

                    // Check if the hit contains a 'result' object
                    if (firstHit.has("result")) {
                        JsonObject result = firstHit.getAsJsonObject("result");

                        // Check if the result contains 'url' (lyrics URL)
                        if (result.has("url")) {
                            String lyricsUrl = result.get("url").getAsString();
                            // You can further process the lyrics URL or fetch the actual lyrics
                            // from the URL here
                            return fetchLyricsFromUrl(lyricsUrl);
                        }
                    }
                }
            }
        }
        return "Lyrics not found";
    }

    private static String fetchLyricsFromUrl(String lyricsUrl) {
        // You can implement logic here to fetch and extract lyrics from the lyrics URL
        // This may involve making another HTTP request to the lyrics page or using a lyrics API
        // Return the lyrics as a string
        OkHttpClient client = new OkHttpClient();
        try {
            // Make an HTTP request to the lyrics URL
            Request request = new Request.Builder()
                    .url(lyricsUrl)
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String html = response.body().string();
                // Extract the lyrics using regex (adjust the regex pattern as needed)
                String lyrics = extractLyricsFromHtml(html);
                return lyrics;
            } else {
                return "Error fetching lyrics from the URL.";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error fetching lyrics from the URL.";
        }
    }

    private static String extractLyricsFromHtml(String html) {
        // Use regex to extract lyrics from the HTML (adjust the pattern as needed)
        Pattern pattern = Pattern.compile("<div class=\"lyrics\">(.*?)</div>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);

        if (matcher.find()) {
            String lyrics = matcher.group(1)
                    .replaceAll("<br>", "\n") // Replace line breaks
                    .replaceAll("<.*?>", ""); // Remove HTML tags
            return lyrics.trim();
        } else {
            return "Lyrics not found on the webpage.";
        }
    }
}

