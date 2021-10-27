package com.vik3.utils;

public class Constants {
    public static String emailDefaultBody = "Hi Radio VIK3, <br>";
    public static String phVIK = "+50939233333";
    public static String prayerCall = "+16313598297";
    public static String fbPageAppURL = "fb://page/?id=104558617865543";
    public static String fbPageWebURL = "http://www.facebook.com/RadioVik3";
    public static String instaPageURL = "instagram://user?username=radiovik3";
    public static String instaPageWebURL = "http://instagram.com/user?username=radiovik3";
    public static String twitterPageAppURL = "twitter://user?screen_name=RadioVik3";
    public static String twitterPageWebURL = "https://twitter.com/RadioVik3";
    public static String youtubePageAppURL = "tyoutube://www.youtube.com/user/UCQbgYU4oN4zLjhTjCRHyGaA";
    public static String youtubePageWebURL = "https://www.youtube.com/channel/UCQbgYU4oN4zLjhTjCRHyGaA/featured";

    public static String AUDIO_STREAMING_URL = "https://stream.radio.co/s24d6d2312/listen";

    public interface ACTION {
        public static String MAIN_ACTION = "action.main";
        public static String PREV_ACTION = "action.prev";
        public static String PLAY_ACTION = "action.play";
        public static String PAUSE_ACTION = "action.pause";
        public static String NEXT_ACTION = "action.next";
        public static String CLOSE_ACTION = "action.close";
        public static String STARTFOREGROUND_ACTION = "action.startforeground";
        public static String STOPFOREGROUND_ACTION = "action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }
}
