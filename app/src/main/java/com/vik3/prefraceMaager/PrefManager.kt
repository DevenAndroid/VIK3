package com.vik3.prefraceMaager

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri

val IS_FIRST_TIME = "IS_FIRST_TIME"
val PREF_IS_Play = "PREF_IS_Play"
val IS_PLAY = "IS_PLAY"
val IS_FIRST_DIALOG = "IS_FIRST_DIALOG"
val MENU_LIST = "MENU_LIST"
val PAGE_ID = "PAGE_ID"
val FULL_READ_DATA = "FULL_READ_DATA"
val NOTIFI_URL = "NOTIFI_URL"
val SEARCH_VALUE = "SEARCH_VALUE"
val SEARCH_HISTORY = "SEARCH_HISTORY"
val PDF_VIEWER_URL = "https://drive.google.com/viewerng/viewer?embedded=true&url="
val ABOUT_URL = "https://www.mayerbrown.com"
val RESPONSE_TEAM_EAMIL = "fw-sig-covid-19-core-response-team@mayerbrown.com"
val USER_GUIDE_EAMIL = "fw-sig-covid-19-core-response-team@mayerbrown.com"

fun openShareSheet(mContext : Context){
    /*var mMessageText = ""
    if (!TextUtils.isEmpty(mUpdatesModel.post_title))
        mMessageText = mUpdatesModel.post_title

    if (!TextUtils.isEmpty(mUpdatesModel.url))
        mMessageText = String.format("%s - %s",mMessageText,mUpdatesModel.url)*/

    val sendIntent = Intent()
    sendIntent.setAction(Intent.ACTION_SEND)
//    sendIntent.putExtra(Intent.EXTRA_TEXT, mMessageText)
    sendIntent.type = "text/plain"
    mContext.startActivity(sendIntent)
}

fun sendMail(mContext: Context,mEmailId : String){
    val intent = Intent(Intent.ACTION_SENDTO) // it's not ACTION_SEND
    intent.data = Uri.parse("mailto:$mEmailId") // or just "mailto:" for blank
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // this will make such that when user returns to your app, your app is displayed, instead of the email app.
    try {
        mContext.startActivity(intent)
    } catch (ex: android.content.ActivityNotFoundException) {
        //toast("There are no email clients installed.")
        val send = Intent(Intent.ACTION_SEND)
        send.type = "text/plain"
        send.putExtra(Intent.EXTRA_EMAIL, arrayOf(mEmailId))
        send.type = "message/rfc822";
        mContext.startActivity(Intent.createChooser(send, "Send mail"))
    }
}

class PrefManager(private val preferences:SharedPreferences, private val editor : SharedPreferences.Editor) {

    var isFirstTime: Boolean
        get() = preferences.getBoolean(IS_FIRST_TIME, true)
        set(value) { editor.putBoolean(IS_FIRST_TIME, value).commit() }

    var menuList: String?
        get() = preferences.getString(MENU_LIST, null)
        set(value) { editor.putString(MENU_LIST, value).commit() }

    var searchHistory: String?
        get() = preferences.getString(SEARCH_HISTORY, null)
        set(value) { editor.putString(SEARCH_HISTORY, value).commit() }
}

class PrefManagerPlayer1(context: Context) {

    private val preferences:SharedPreferences = context.getSharedPreferences(PREF_IS_Play, Context.MODE_PRIVATE)
    var isPlay: Boolean
        get() = preferences.getBoolean(IS_PLAY, false)
        set(value) { preferences.edit().putBoolean(IS_PLAY, value).apply() }

    var isDialog: Boolean
        get() = preferences.getBoolean(IS_FIRST_DIALOG, false)
        set(value) { preferences.edit().putBoolean(IS_FIRST_DIALOG, value).apply() }

    var searchHistory: String?
        get() = preferences.getString(SEARCH_HISTORY, null)
        set(value) { preferences.edit().putString(SEARCH_HISTORY, value).commit() }
}
