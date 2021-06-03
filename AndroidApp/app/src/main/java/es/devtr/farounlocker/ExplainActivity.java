package es.devtr.farounlocker;

import static es.devtr.farounlocker.ExplorerActivity.KEY_LAST_URL;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import es.devtr.farounlocker.helper.Preferences;

public class ExplainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);

        String validUrl = Preferences.read(KEY_LAST_URL, "",getApplicationContext());

        if(!TextUtils.isEmpty(validUrl)){
            ExplorerActivity.launchActivity(validUrl,this);
            finish();
        }

    }

}