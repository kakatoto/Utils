package com.kakatoto.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by darong on 2017. 6. 29..
 */

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Context getContext() {
        return context;
    }
}
