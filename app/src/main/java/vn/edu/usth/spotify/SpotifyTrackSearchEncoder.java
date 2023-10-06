package vn.edu.usth.spotify;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SpotifyTrackSearchEncoder {

//    public static String encodeSearchTerm(String searchTerm) {
//        try {
//            // Encode the search term
//            return URLEncoder.encode(searchTerm, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return "";
//        }
//    }

    public static String constructSearchUrl(String encodedSearchTerm) {
        // Construct the search URL
        return "https://api.spotify.com/v1/search?q=" + encodedSearchTerm(encodedSearchTerm) + "&type=track";
    }

    private static String encodedSearchTerm(String searchTerm) {
        // Replace spaces with %20
        String encodedSearchTerm = searchTerm.replaceAll(" ", "%20");

        // Encode the modified search term
        return encodedSearchTerm;
    }

}