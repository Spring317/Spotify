package vn.edu.usth.spotify;

import android.net.Uri;

public class ItemSongList {
    String songName;
    String authorName;

    Uri uri;


    public ItemSongList(String songName, String authorName, Uri uri) {
        this.songName = songName;
        this.authorName = authorName;
        this.uri = uri;
    }

    public String getSongName() {
        return songName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Uri getUri() {
        return uri;
    }
}
