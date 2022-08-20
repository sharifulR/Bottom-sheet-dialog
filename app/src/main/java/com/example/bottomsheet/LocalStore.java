package com.example.bottomsheet;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStore {
    public static Context mContext;
    private static SharedPreferences sharedPreferences;

    public static LocalStore localStore;

    public static LocalStore getInstance(Context context) {
        mContext = context;
        sharedPreferences = context.getSharedPreferences("store_date_time", Context.MODE_PRIVATE);
        if (localStore == null) {
            localStore = new LocalStore();
        }

        return localStore;
    }

    public void insertDateTime(long time) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("time", time);
        editor.apply();
    }

    public long getTime(){
        return sharedPreferences.getLong("time",0);
    }

}
