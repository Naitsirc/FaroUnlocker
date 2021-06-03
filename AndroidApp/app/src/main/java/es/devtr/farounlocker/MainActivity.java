package es.devtr.farounlocker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.devtr.farounlocker.helper.ImagenAdapter;
import es.devtr.farounlocker.helper.Parser;
import es.devtr.farounlocker.classes.ContextSchema;
import es.devtr.farounlocker.helper.Form;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private String direccion;

    public static void launchActivity(String url, Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("URL",url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.context = this;


        View footer = findViewById(R.id.footer);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://devtr.es/")));

                } catch (Exception ignored) {}
            }
        });

        View loading = findViewById(R.id.loading);

        loading.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        direccion = "";

        if(getIntent()!=null && getIntent().getStringExtra("URL")!=null){
            direccion = getIntent().getStringExtra("URL");
        }

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (sharedText != null) {
                    direccion = sharedText;
                }
            }
        }

        if(Intent.ACTION_VIEW.equals(action)){
            Uri data = intent.getData();
            direccion = data.toString();
        }

        direccion = getLinkFromString(direccion);


        if(direccion!=null) {

            Parser.load(direccion, new Parser.SchemaListener() {

                @Override
                public void OnSchemaLoaded(ContextSchema contextSchema) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Form form = new Form(contextSchema);

                            ImageView imagenPortada = (ImageView) findViewById(R.id.imagen_portada);

                            Glide.with(context)
                                    .load(form.getPortadaUrl())
                                    .into(imagenPortada);

                            imagenPortada.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                                    CustomTabsIntent customTabsIntent = builder.build();
                                    customTabsIntent.launchUrl(context, Uri.parse(form.getPortadaUrl()));
                                }
                            });

                            TextView intro = findViewById(R.id.intro);
                            TextView body = findViewById(R.id.body);
                            TextView title = findViewById(R.id.title);

                            intro.setText(form.getIntro());
                            body.setText(form.getBody());
                            title.setText(form.getPortadaTitulo());

                            RecyclerView recyclerView
                                    = (RecyclerView) findViewById(
                                    R.id.imagenes);

                            recyclerView.setVisibility((form.getImagenes() == null || form.getImagenes().size() == 0) ? View.GONE : View.VISIBLE);

                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                            ImagenAdapter adapter = new ImagenAdapter(form.getImagenes(), MainActivity.this);

                            recyclerView.setAdapter(adapter);

                            loading.setVisibility(View.GONE);

                        }
                    });

                }

                @Override
                public void OnError() {
                    ExplorerActivity.launchActivity(direccion,MainActivity.this);
                }

            });

        }else{
            ErrorActivity.launchActivity("",MainActivity.this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navegador, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            if (item.getItemId() == R.id.action_browser) {

                if (direccion != null) {
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(context, Uri.parse(direccion));
                }

                return true;
            }

            if (item.getItemId() == R.id.action_home) {

                if (direccion != null) {
                    String site = getDomainName(direccion);
                    if(site!=null){
                        ExplorerActivity.launchActivity(site, this);
                    }
                }

                return true;
            }
        }catch (Exception ignored){}

        return super.onOptionsItemSelected(item);

    }

    public static String getDomainName(String url){
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            return  ((url.startsWith("https")?"https://":"http://")+(domain.startsWith("www.") ? domain.substring(4) : domain));
        }catch (Exception ignored){
            return null;
        }
    }

    private String getLinkFromString(String text){

        ArrayList<String> s = getLinksFromString(text);

        if(s.size()==0){
            return null;
        }
        return s.get(0);
    }

    private ArrayList<String> getLinksFromString(String text) {

        ArrayList<String> links = new ArrayList<>();

        Matcher m = Patterns.WEB_URL.matcher(text);

        while(m.find()) {
            String urlStr = m.group();
            if (urlStr.startsWith("(") && urlStr.endsWith(")"))
            {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }
            links.add(urlStr);
        }
        return links;
    }


}