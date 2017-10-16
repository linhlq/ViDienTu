package com.linhlee.vidientu.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;

/**
 * Created by Linh Lee on 4/9/2017.
 */
public class Constant {
    public static final String BASE_URL = "https://m.thecaosieure.com";

    public static String IS_LOGIN = "is_login";
    public static String LOGIN_SUCCESS = "login_success";
    public static String USER_INFO = "user_info";
    public static String CHANGE_FILE_FRAGMENT = "change_file_fragment";
    public static String GOTO_TRANSFER = "goto_transfer";

    public static int convertDpIntoPixels(int sizeInDp, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (sizeInDp * scale + 0.5f);
        return dpAsPixels;
    }

    public static void increaseHitArea(final View button) {
        final View parent = (View) button.getParent();  // button: the view you want to enlarge hit area
        parent.post(new Runnable() {
            public void run() {
                final Rect rect = new Rect();
                button.getHitRect(rect);
                rect.top -= 100;    // increase top hit area
                rect.left -= 100;   // increase left hit area
                rect.bottom += 100; // increase bottom hit area
                rect.right += 100;  // increase right hit area
                parent.setTouchDelegate(new TouchDelegate(rect, button));
            }
        });
    }

    public static String formatTime(int time) {
        String formatedTime = "";

        if (time < 10) {
            formatedTime = "0" + time;
        } else {
            formatedTime = "" + time;
        }

        return formatedTime;
    }
}
