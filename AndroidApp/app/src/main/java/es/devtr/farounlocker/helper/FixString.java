package es.devtr.farounlocker.helper;

public class FixString {
    public final static String fix(String a){
        a = a.replace("&quot;","\"");
        return a;
    }
}
