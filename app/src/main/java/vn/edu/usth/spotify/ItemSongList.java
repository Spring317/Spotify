package vn.edu.usth.spotify;

public class ItemSongList {
    String songName;
    String authorName;
    String url;
    String uri;
    int positionInList;

    public ItemSongList(String songName, String authorName, String url, String uri, int positionInList) {
        this.songName = songName;
        this.authorName = authorName;
        this.url = url;
        this.uri = uri;
        this.positionInList = positionInList;

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

    public Integer getPosition() {
        return positionInList;
    }
}
