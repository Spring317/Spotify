package vn.edu.usth.spotify;

public class ItemPlaylistTitle {

    private int image;
    private int playlistTitle;

    public ItemPlaylistTitle(int image, int playlistTitle) {
        this.image = image;
        this.playlistTitle = playlistTitle;
    }

    public int getImage() {
        return image;
    }

    public int getPlaylistTitle() {
        return playlistTitle;
    }
}
