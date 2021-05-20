package es.devtr.farounlocker.helper;

public class Imagen {

    private String title;
    private String url;

    public Imagen(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
