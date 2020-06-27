package com.github.mrgrtt.taohub;

import android.app.Application;

import androidx.room.Room;

public class TaoApplication extends Application {
    private TaoDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        this.db = Room.databaseBuilder(getApplicationContext(), TaoDatabase.class, "database").build();
    }

    public TaoDatabase getDatabase() {
        return this.db;
    }
}
