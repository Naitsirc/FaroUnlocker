
package es.devtr.farounlocker.classes;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AssociatedMedium {

    @SerializedName("@type")
    @Expose
    private String type;
    @SerializedName("image")
    @Expose
    private Image__1 image;
    @SerializedName("video")
    @Expose
    private Video video;
    @SerializedName("url")
    @Expose
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Image__1 getImage() {
        return image;
    }

    public void setImage(Image__1 image) {
        this.image = image;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
