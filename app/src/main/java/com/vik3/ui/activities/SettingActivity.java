package com.vik3.ui.activities;

import static com.vik3.utils.CommonMethods.getWebsite;
import static com.vik3.utils.CommonMethods.setLocale;
import static com.vik3.utils.CommonMethods.setNightMode;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.vik3.MainActivity;
import com.vik3.R;
import com.vik3.databinding.ActivitySettingBinding;

import java.util.Objects;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding binding;
    private ReviewManager reviewManager;

    private static final int NOTIFICATION_ID = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
        }
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        reviewManager = ReviewManagerFactory.create(this);

        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.textViewPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Vik3 Privacy policy page URL: http://vik3.eoxysitsolution.com/privacy-policy/
                getWebsite(SettingActivity.this, "http://vik3.eoxysitsolution.com/privacy-policy/");
            }
        });

        binding.switchDarkTheme.setChecked(getSharedPreferences("THEME_MODE", MODE_PRIVATE).getBoolean("isChecked", false));

        binding.switchDarkTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setNightMode(SettingActivity.this, isChecked);
            getSharedPreferences("THEME_MODE", MODE_PRIVATE).edit().putBoolean("isChecked", isChecked).apply();
        });

        binding.textViewLanguageSelect.setText(getSharedPreferences("THEME_MODE", MODE_PRIVATE).getBoolean("isFrench", false) ? getString(R.string.french) : getString(R.string.english));
        binding.textViewWriteReview.setOnClickListener(v -> launchMarket());

        binding.textViewLanguageSelect.setOnClickListener(v -> {
            PopupMenu menu = new PopupMenu(SettingActivity.this, binding.textViewLanguageSelect);
            menu.getMenu().add("English");
            menu.getMenu().add("French");
            menu.show();
            menu.setOnMenuItemClickListener(item -> {
                if (item.getTitle() == "English") {
                    setLocale(SettingActivity.this, "en");
                    binding.textViewLanguageSelect.setText(getString(R.string.english));
                    getSharedPreferences("THEME_MODE", MODE_PRIVATE).edit().putBoolean("isFrench", false).apply();
                    finish();
                } else {
                    getSharedPreferences("THEME_MODE", MODE_PRIVATE).edit().putBoolean("isFrench", true).apply();
                    setLocale(SettingActivity.this, "fr");
                    binding.textViewLanguageSelect.setText(getString(R.string.french));
                    finish();
                }
                return false;
            });
        });

        binding.switchVerseOfTheDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    FirebaseMessaging.getInstance().subscribeToTopic("verse").addOnSuccessListener((com.google.android.gms.tasks.OnSuccessListener<? super Void>) aVoid -> {
//                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    });

                    String channelId = "Your_channel_id";
                    Intent intent = new Intent(SettingActivity.this, SettingActivity.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(SettingActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder b = new NotificationCompat.Builder(SettingActivity.this, channelId);

                    b.setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setTicker("VIK3")
                            .setContentTitle("VIK3")
                            .setContentText(getResources().getString(R.string.mesage_subscribe))
                            .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                            .setContentIntent(contentIntent)
                            .setContentInfo("Info");


                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1, b.build());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel(
                                channelId,
                                "Channel human readable title",
                                NotificationManager.IMPORTANCE_HIGH);
                        notificationManager.createNotificationChannel(channel);
                        b.setChannelId(channelId);
                    }

                    notificationManager.notify(0, b.build());
                }
            }
        });

    }

    public void showNotification(String message, String title) {
        String channelId = "Your_channel_id";
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setSound(sound)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message));
//                .build();


        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(NOTIFICATION_ID, notificationBuilder.build());
        NotificationManager nm = (NotificationManager)getApplication().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
            notificationBuilder.setChannelId(channelId);
        }
        nm.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class).putExtra("menu", "more"));
        finish();
    }

    public void showRateApp() {
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                // Getting the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();

                Task<Void> flow = reviewManager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(task1 -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown.
                });
            }
        });
    }

}