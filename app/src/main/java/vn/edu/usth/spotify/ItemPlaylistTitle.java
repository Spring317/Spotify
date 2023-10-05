package vn.edu.usth.spotify;

public class ItemPlaylistTitle {

    private String image;
    private String playlistTitle;

    public ItemPlaylistTitle(String image, String playlistTitle) {
        this.image = image;
        this.playlistTitle = playlistTitle;
    }

    public String getImage() {
        return image;
    }

    public String getPlaylistTitle() {
        return playlistTitle;
    }
}
