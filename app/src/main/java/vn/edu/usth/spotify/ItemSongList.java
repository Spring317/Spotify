package vn.edu.usth.spotify;

public class ItemSongList {
    String songName;
    String authorName;
    String url;

    String uri;


    public ItemSongList(String songName, String authorName, String url, String uri) {
        this.songName = songName;
        this.authorName = authorName;
        this.url = url;
        this.uri = uri;
    }

    public String getSongName() {
        return songName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getUrl() {
        return url;
    }

    public String getUri() {
        return uri;
    }
}
