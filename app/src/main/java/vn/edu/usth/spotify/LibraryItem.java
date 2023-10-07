package vn.edu.usth.spotify;

public class LibraryItem {

    String library_name;
    String library_type;
    String library_image_view;

    public LibraryItem(String library_name, String library_type, String library_image_view) {
        this.library_name = library_name;
        this.library_type = library_type;
        this.library_image_view = library_image_view;
    }

    public String getLibrary_name() {
        return library_name;
    }

    public void setLibrary_name(String library_name) {
        this.library_name = library_name;
    }

    public String getLibrary_type() {
        return library_type;
    }

    public void setLibrary_type(String library_type) {
        this.library_type = library_type;
    }

    public String getLibrary_image_view() {
        return library_image_view;
    }

    public void setLibrary_image_view(String library_image_view) {
        this.library_image_view = library_image_view;
    }
}
