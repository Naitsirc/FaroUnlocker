package es.devtr.farounlocker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

public class ExplorerActivity extends AppCompatActivity {

    private Context context;
    private String direccion;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);


        this.context = this;


        WebView webView = findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(mWebViewClient);
        webView.addJavascriptInterface(new MyJavaScriptInterface(),
                "android");
        webView.loadUrl("");

    }

    WebViewClient mWebViewClient = new WebViewClient() {
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
