package vn.edu.usth.spotify;

public class ItemSongList {
    String songName;
    String authorName;
    String url;


    public ItemSongList(String songName, String authorName, String url) {
        this.songName = songName;
        this.authorName = authorName;
        this.url = url;
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
}
