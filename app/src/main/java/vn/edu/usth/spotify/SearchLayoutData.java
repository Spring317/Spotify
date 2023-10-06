package vn.edu.usth.spotify;

public class SearchLayoutData {
    String image;
    String name;
    String declare;
    public SearchLayoutData(String image, String name, String declare) {
        this.image = image;
        this.name = name;
        this.declare = declare;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeclare() {
        return declare;
    }

    public void setDeclare(String declare) {
        this.declare = declare;
    }
}
