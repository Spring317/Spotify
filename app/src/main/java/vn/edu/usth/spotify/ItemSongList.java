package vn.edu.usth.spotify;

public class ItemSongList {
    String songName;
    String authorName;


    public ItemSongList(String songName, String authorName) {
        this.songName = songName;
        this.authorName = authorName;
    }

    public String getSongName() {
        return songName;
    }

    public String getAuthorName() {
        return authorName;
    }
}
