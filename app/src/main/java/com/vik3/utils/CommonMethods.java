package com.vik3.utils;

import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.appcompat.app.AppCompatDelegate;

import com.vik3.ui.activities.SettingActivity;

import java.util.Locale;

/**
 * Developer - Deven
 * date - 7July, 2021
 */
public class CommonMethods {

    /**
     * Select The Theme Mode either Day or Night
     *
     * @param target
     * @param state
     */
    public static void setNightMode(Context target, boolean state) {
        UiModeManager uiManager = (UiModeManager) target.getSystemService(Context.UI_MODE_SERVICE);
        if (Build.VERSION.SDK_INT <= 22) {
            uiManager.enableCarMode(0);
        }
        if (state) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            uiManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            uiManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    /**
     * Language Selection
     *
     * @param context
     * @param lang
     */
    public static void setLocale(Context context, String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = context.getResources();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, res.getDisplayMetrics());

        Intent refresh = new Intent(context, SettingActivity.class);
        context.startActivity(refresh);
    }

    public static void share(Context context, String message) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(sharingIntent, "Select"));
    }

    public static void showAlert(Context context, String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setPositiveButton(
                "OK",
                (dialog, id) -> dialog.cancel());
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public static void composeEmail(Context context, String addresses) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static void getNumber(Context context, String number) {
        Uri uri = Uri.parse("tel:" + number);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(callIntent);
    }

    public static void getWhatsApp(Context context, String number) {
        String url = "https://api.whatsapp.com/send?phone=" + number;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    public static void getWebsite(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static void composeTextMessage(Context context, String number, String message) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + number));
        intent.putExtra("sms_body", message);
        context.startActivity(intent);
    }

    public static void getFacebook(Context context, String id, String name) {
//        try {
//            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
//            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + id));
//        } catch (Exception e) {
//            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + name));
//        }
        String facebookUrl = "https://www.facebook.com/"+name
                ;
        try {
            int versionCode = context.getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) {
                Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
                context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
            } else {
                Uri uri = Uri.parse("fb://page/"+name);
                context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        } catch (PackageManager.NameNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
        }
    }

    public static void getInstagram(Context context, String name) {
        Uri uri = Uri.parse("http://instagram.com/_u/" + name);
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            context.startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/" + name)));
        }
    }

    public static void getTwitter(Context context, String name) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name)));
        } catch (Exception e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + name)));
        }
    }

    public static void getYouTubeChannel(Context context, String name) {
        String youtubeURL = "https://www.youtube.com/channel/"+name;
        Intent youtubeIntent;
        try {
            youtubeIntent = new Intent(Intent.ACTION_VIEW);
            youtubeIntent.setPackage("com.google.android.youtube");
            youtubeIntent.setData(Uri.parse(youtubeURL));
            context.startActivity(youtubeIntent);
        } catch (ActivityNotFoundException e) {
            youtubeIntent = new Intent(Intent.ACTION_VIEW);
            youtubeIntent.setData(Uri.parse(youtubeURL));
            context.startActivity(youtubeIntent);
        }
    }
}
