package vn.edu.usth.spotify;

public class SearchLayoutData {
    String image;
    String name;
    String declare;
    Integer positionInList;

    String url;
    String uri;

    public SearchLayoutData(String image, String name, String declare, String url, String uri, Integer positionInList) {
        this.image = image;
        this.name = name;
        this.declare = declare;
        this.positionInList = positionInList;
        this.url = url;
        this.uri = uri;
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
    public Integer getPosition() {
        return positionInList;
    }

    public String getUrl() {
        return url;
    }

    public String getUri() {
        return uri;
    }
}
