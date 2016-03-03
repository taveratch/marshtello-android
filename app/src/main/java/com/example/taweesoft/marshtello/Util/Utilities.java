package com.example.taweesoft.marshtello.Util;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TAWEESOFT on 2/27/16 AD.
 */
public class Utilities {

    public static String getMonthStr(int month){
        switch (month){
            case 0 :
                return "Jan";
            case 1 :
                return "Feb";
            case 2 :
                return "March";
            case 3 :
                return "April";
            case 4 :
                return "May";
            case 5 :
                return "June";
            case 6 :
                return "July";
            case 7 :
                return "Aug";
            case 8 :
                return "Sep";
            case 9 :
                return "Oct";
            case 10 :
                return "Nov";
            case 11 :
                return "Dec";
            default: return "";
        }
    }

    public static void setActionBarColor(ActionBar actionbar , int color){
        actionbar.setBackgroundDrawable(new ColorDrawable(color));
    }

    public static void setStatusBarColor(Activity activity,int color){
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(color);
    }

    /**
     * Hide keyboard
     * @param view
     */
    public static void hideKeyboard(Activity activity,View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static String getDateStr(long dateLong){
        SimpleDateFormat format = new SimpleDateFormat("yyyy hh:mm");
        Date date = new Date(dateLong);
        String monthStr = Utilities.getMonthStr(date.getMonth());
        return String.format("%d %s %s",date.getDate(),monthStr,format.format(date));
    }


}
