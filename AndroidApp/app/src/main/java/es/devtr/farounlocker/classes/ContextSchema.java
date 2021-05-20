
package es.devtr.farounlocker.classes;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ContextSchema {

    @SerializedName("@context")
    @Expose
    private String context;
    @SerializedName("@type")
    @Expose
    private String type;
    @SerializedName("headline")
    @Expose
    private String headline;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("associatedMedia")
    @Expose
    private List<AssociatedMedium> associatedMedia = null;
    @SerializedName("articleBody")
    @Expose
    private String articleBody;


    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<AssociatedMedium> getAssociatedMedia() {
        return associatedMedia;
    }

    public void setAssociatedMedia(List<AssociatedMedium> associatedMedia) {
        this.associatedMedia = associatedMedia;
    }
    public String getArticleBody() {
        return articleBody;
    }

    public void setArticleBody(String articleBody) {
        this.articleBody = articleBody;
    }

}
