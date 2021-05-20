package es.devtr.farounlocker.helper;

import java.util.ArrayList;

import es.devtr.farounlocker.classes.AssociatedMedium;
import es.devtr.farounlocker.classes.ContextSchema;

public class Form {

    private String portadaUrl = null;
    private String portadaTitulo = null;
    private String title = null;
    private String body = null;
    private String intro = null;
    private ArrayList<Imagen> imagenes;

    public Form(ContextSchema contextSchema){

        portadaUrl = contextSchema.getImage().getUrl();
        portadaTitulo = contextSchema.getImage().getName();

        title = contextSchema.getHeadline();
        body = contextSchema.getArticleBody();

        int div = body.length()/4;
        String[] sp = body.split("[.]");

        intro = "";
        body = "";

        for(int i=0;i<sp.length;i++){
            if(intro.length()>=div){
                if(body.length()>0){
                    body = body + "."+sp[i];
                }else{
                    body = sp[i];
                }
            }else{
                if(intro.length()>0){
                    intro = intro +"."+sp[i];
                }else{
                    intro = sp[i];
                }
            }
        }

        if(contextSchema.getAssociatedMedia()!= null && contextSchema.getAssociatedMedia().size()>0){
            imagenes = new ArrayList<>();
            for(AssociatedMedium associatedMedium:contextSchema.getAssociatedMedia()) {
                if (associatedMedium.getImage() != null) {
                    imagenes.add(new Imagen(associatedMedium.getImage().getName(), associatedMedium.getImage().getUrl()));
                }
            }
        }
    }

    public String getPortadaUrl() {
        return portadaUrl;
    }

    public String getPortadaTitulo() {
        return portadaTitulo;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getIntro() {
        return intro;
    }

    public ArrayList<Imagen> getImagenes() {
        return imagenes;
    }
}
