package com.vik3.ui.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.vik3.databinding.ActivitySoundClubBinding;

public class SoundClubActivity extends AppCompatActivity {

    private ActivitySoundClubBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySoundClubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.webView.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = this;

        binding.webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        binding.webView .loadUrl("https://soundcloud.com/radiovik3");
    }
}