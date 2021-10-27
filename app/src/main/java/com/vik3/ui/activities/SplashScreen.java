package com.vik3.ui.activities;

import static com.vik3.utils.CommonMethods.share;
import static com.vik3.utils.CommonMethods.showAlert;
import static com.vik3.utils.Constants.AUDIO_STREAMING_URL;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.vik3.BuildConfig;
import com.vik3.MainActivity;
import com.vik3.R;
import com.vik3.apiClient.RetrofitClientStations;
import com.vik3.databinding.ActivitySplashScreenBinding;
import com.vik3.prefraceMaager.PreferenceManager;
import com.vik3.ui.adapters.AdapterSongList;
import com.vik3.ui.models.Audio;
import com.vik3.ui.models.ModelArtist;
import com.vik3.ui.models.ModelStations;
import com.vik3.utils.StorageUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
                float screenRatio = binding.videoView.getWidth() / (float)
                        binding.videoView.getHeight();
                float scaleX = videoRatio / screenRatio;
                if (scaleX >= 1f) {
                    binding.videoView.setScaleX(scaleX);
                } else {
                    binding.videoView.setScaleY(1f / scaleX);
                }
            }
        });

        if (getSharedPreferences("THEME_MODE", MODE_PRIVATE).getBoolean("isFrench", false)) {
            Locale myLocale = new Locale("fr");
            Resources res = getResources();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, res.getDisplayMetrics());

        } else {
            Locale myLocale = new Locale("en");
            Resources res = getResources();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, res.getDisplayMetrics());

        }
        getVideo();
        getStations();
    }

    private void getVideo() {
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.intro_video;
        binding.videoView.setVideoURI(Uri.parse(uri));
        binding.videoView.requestFocus();
        binding.videoView.start();
        binding.videoView.setOnCompletionListener(mp -> {
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
//            startActivity(new PreferenceManager(this).isLogIn() ? new Intent(SplashScreen.this, MainActivity.class) : new Intent(SplashScreen.this, LogInActivity.class));
            finish();
        });
    }

    private void getStations() {
        Call<ModelStations> call = RetrofitClientStations
                .getInstance()
                .getApiService()
                .getStations();
        call.enqueue(new Callback<ModelStations>() {
            @Override
            public void onResponse(@NotNull Call<ModelStations> call, @NotNull Response<ModelStations> response) {
                try {
                    ArrayList<Audio> audioList = new ArrayList<>();
                    audioList.add(new Audio(AUDIO_STREAMING_URL, response.body().getCurrentTrack().getTitle().split("- ")[1], response.body().getCurrentTrack().getTitle().split("- ")[0], response.body().getCurrentTrack().getTitle().split("- ")[0]));
                    StorageUtil storage = new StorageUtil(SplashScreen.this);
                    storage.storeAudio(audioList);
                    storage.storeAudioIndex(0);
                } catch (Exception ignored) {

                }
            }

            @Override
            public void onFailure(@NotNull Call<ModelStations> call, @NotNull Throwable t) {

            }
        });
    }
}