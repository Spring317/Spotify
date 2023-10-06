package vn.edu.usth.spotify;

public class ItemPlaylistTitle {

    private String image;
    private String playlistTitle;
    private String url;
    private String type;

    public ItemPlaylistTitle(String image, String playlistTitle, String url, String type) {
        this.image = image;
        this.playlistTitle = playlistTitle;
        this.url = url;
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public String getPlaylistTitle() {
        return playlistTitle;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }
}
