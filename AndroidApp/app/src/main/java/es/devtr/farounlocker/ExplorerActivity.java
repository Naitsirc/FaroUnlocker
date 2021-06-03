package es.devtr.farounlocker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.regex.Matcher;

import es.devtr.farounlocker.classes.ContextSchema;
import es.devtr.farounlocker.helper.Form;
import es.devtr.farounlocker.helper.ImagenAdapter;
import es.devtr.farounlocker.helper.Parser;
import es.devtr.farounlocker.helper.Preferences;

public class ExplorerActivity extends AppCompatActivity {

    private Activity context;
    private String validUrl = null;

    public static String KEY_LAST_URL = "LAST_URL";

    public static void launchActivity(String url, Activity activity){
        Intent intent = new Intent(activity, ExplorerActivity.class);
        intent.putExtra("URL",url);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);


        this.context = this;

        validUrl = getIntent().getStringExtra("URL");

        if(!TextUtils.isEmpty(validUrl)){
            Preferences.save(KEY_LAST_URL, validUrl, getApplicationContext());
        }

        WebView webView = findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(mWebViewClient);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "android");
        webView.loadUrl(validUrl);

    }



    private final WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView  view, String  url){

            android.util.Log.v("should","=>"+url);

            if(!url.equals(validUrl)) {
                Parser.load(url, new Parser.SchemaListener() {
                    @Override
                    public void OnSchemaLoaded(ContextSchema contextSchema) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.loadUrl(validUrl);
                                MainActivity.launchActivity(url, context);
                            }
                        });
                    }

                    @Override
                    public void OnError() {
                        validUrl = url;
                    }
                });
            }

            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onLoadResource(WebView  view, String  url){

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:window.android.onUrlChange(window.location.href);");
        };
    };

    private class MyJavaScriptInterface {
        @JavascriptInterface
        public void onUrlChange(String url) {

        }
    }
}
