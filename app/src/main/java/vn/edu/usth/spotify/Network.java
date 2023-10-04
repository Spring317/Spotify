package vn.edu.usth.spotify;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class Network {
    private static final OkHttpClient client = new OkHttpClient();

    public static String fetchGeniusLyrics(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Network error: " + response.code());
        }
    }
}
