package es.devtr.farounlocker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

public class ErrorActivity extends AppCompatActivity {

    private Context context;
    private String direccion;

    public static void launchActivity(String url, Activity activity){
        Intent intent = new Intent(activity, ErrorActivity.class);
        intent.putExtra("URL",url);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);


        this.context = this;

        direccion = getIntent().getStringExtra("URL");


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
            if (item.getItemId() == R.id.action_browser) {// User chose the "Settings" item, show the app settings UI...

                if (direccion != null) {
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(context, Uri.parse(direccion));
                }

                return true;
            }
        }catch (Exception ignored){}

        return super.onOptionsItemSelected(item);

    }

}