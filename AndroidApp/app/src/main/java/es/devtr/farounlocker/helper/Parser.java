package es.devtr.farounlocker.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.devtr.farounlocker.classes.ContextSchema;


public class Parser {

    public interface SchemaListener{
        public void OnSchemaLoaded(ContextSchema contextSchema);
        public void OnError();
    }

    public static void load(String url, SchemaListener schemaListener){

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    String html = getContents(url);

                    String[] scripts = html.split("<script type=\"application/ld+json\">");

                    String json = null;

                    if(scripts.length> 1){
                        for(int i=1;i<scripts.length;i++){
                            if(scripts[i].contains("NewsArticle")){
                                json = scripts[i].split("</script>")[0];
                                break;
                            }
                        }
                    }

                    if(json == null || json.isEmpty()) {
                        json = html.split("application\\/ld\\+json\"\\>")[1].split("</script>")[0];
                    }

                    Gson gson = new Gson();
                    gson = new GsonBuilder().registerTypeHierarchyAdapter(List.class, new JsonDeserializer<List<?>>() {
                        @Override
                        public List<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                            if (json.isJsonArray()) {
                                JsonArray array = json.getAsJsonArray();
                                Type itemType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
                                List list = new ArrayList<>();
                                for (int i = 0; i < array.size(); i++) {
                                    JsonElement element = array.get(i);
                                    Object item = context.deserialize(element, itemType);
                                    list.add(item);
                                }
                                return list;
                            } else {
                                return Collections.EMPTY_LIST;
                            }
                        }
                    }).create();


                    ContextSchema contextSchema = gson.fromJson(json, ContextSchema.class);

                    if(schemaListener!=null){
                        schemaListener.OnSchemaLoaded(contextSchema);
                    }

                }catch (Exception error){

                    error.printStackTrace();

                    if(schemaListener!=null){
                        schemaListener.OnError();
                    }

                }


            }
        }).start();

    }

    private static String getContents(String url){
        ArrayList<String> list = getContent(url);
        StringBuilder stringBuilder = new StringBuilder();
        for(String s:list){
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    private static ArrayList<String> getContent(String url){

        try {
            URL website = new URL(url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            website.openStream()));

            String inputLine;
            ArrayList<String> lineas = new ArrayList<>();

            while ((inputLine = in.readLine()) != null)
                lineas.add(inputLine);

            in.close();

            return lineas;

        }catch (Exception ignored){}

        return new ArrayList<>();
    }
}
