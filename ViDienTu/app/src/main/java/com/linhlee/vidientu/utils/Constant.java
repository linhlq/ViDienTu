package com.linhlee.vidientu.utils;

import android.content.Context;

/**
 * Created by Linh Lee on 4/9/2017.
 */
public class Constant {
    public static int convertDpIntoPixels(int sizeInDp, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (sizeInDp * scale + 0.5f);
        return dpAsPixels;
    }
}
