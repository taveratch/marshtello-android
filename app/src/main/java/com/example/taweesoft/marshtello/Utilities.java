package com.example.taweesoft.marshtello;

/**
 * Created by TAWEESOFT on 2/27/16 AD.
 */
public class Utilities {

    public static String getMonthStr(int month){
        switch (month){
            case 1 :
                return "Jan";
            case 2 :
                return "Feb";
            case 3 :
                return "March";
            case 4 :
                return "April";
            case 5 :
                return "May";
            case 6 :
                return "June";
            case 7 :
                return "July";
            case 8 :
                return "Aug";
            case 9 :
                return "Sep";
            case 10 :
                return "Oct";
            case 11 :
                return "Nov";
            case 12 :
                return "Dec";
            default: return "";
        }
    }
}
