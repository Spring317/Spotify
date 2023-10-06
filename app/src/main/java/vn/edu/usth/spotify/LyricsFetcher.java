package vn.edu.usth.spotify;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LyricsFetcher {
    private static final String BASE_URL = "https://api.genius.com";
    private static final String ACCESS_TOKEN = "n2z4IPlI5D5umdjhdGeU1_qc1KYnEOnHb4bdqrzyw3czL8NMOymsM7zUYyYr8h92";

    LyricsFetcher lyricsFetcher = new LyricsFetcher();

    String query = "Despacito";

    private OkHttpClient client;
    private Gson gson;

    public LyricsFetcher() {
        client = new OkHttpClient();
        gson = new Gson();
    }

    public List<Song> search(String query) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/search?q=" + query)
                .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                .build();

        try {
            List<Song> searchResults = lyricsFetcher.search(query);

            // Handle the search results here
            for (Song song : searchResults) {
                // Access the information about each song (e.g., title, artist)
                String title = song.getTitle();
                String artist = song.getArtist();
                // Display or process the search results as needed
            }
        } catch (IOException e) {
            // Handle any errors that may occur during the API request
            e.printStackTrace();
        }

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            Log.e("LyricsFetcher", "Unexpected response body: " + response.body().string());
            throw new IOException("Unexpected response code: " + response.code());
        }

        return gson.fromJson(response.body().string(), new TypeToken<List<Song>>(){}.getType());
    }

    public Song getLyrics(String songId) throws IOException {
        songId = "378195";
        Request request = new Request.Builder()
                .url(BASE_URL + "/songs/" + songId)
                .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected response code: " + response.code());
        }

        return gson.fromJson(response.body().string(), Song.class);
    }

}

