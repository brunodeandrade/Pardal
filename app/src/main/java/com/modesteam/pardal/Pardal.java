package com.modesteam.pardal;

import android.app.Application;

/**
 * Created by andrebsguedes on 13/04/15.
 */
public class Pardal extends Application{

    private static Pardal instance;
    private static String databaseName;

    public Pardal(){
        databaseName = "database.sqlite3.db";
        instance = this;
    }

    public static String getDatabaseName() {
        return databaseName;
    }

    public static void setDatabaseName(String databaseName) {
        Pardal.databaseName = databaseName;
    }

    public static Pardal getInstance() {
        return instance;
    }
}
