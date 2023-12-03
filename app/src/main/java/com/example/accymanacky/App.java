package com.example.accymanacky;

import android.app.Application;

import androidx.room.Room;

import com.example.accymanacky.data.AppDatabase;
import com.example.accymanacky.data.IAccountDao;
import com.example.accymanacky.model.AccountModel;

public class App extends Application {
    private AppDatabase database;
    private IAccountDao accountDao;
    private static App instance = null;

    public static App getInstance() { return instance; }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "accy_manacky_db")
                .allowMainThreadQueries().build();

        //addTestAccount();

        accountDao = database.accountDao();
    }

    private void addTestAccount() {
        IAccountDao accountDao = database.accountDao();
        AccountModel testAccount = new AccountModel(0, "Super title", "Ultra login", "Mega password", "Puper info",
                "https://media.giphy.com/media/bzKxypf8ywBoP7CGoj/giphy.gif", "ultra@mail.com", System.currentTimeMillis());
        accountDao.insert(testAccount);
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public void setDatabase(AppDatabase db) {
        this.database = db;
    }

    public IAccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(IAccountDao ad) {
        this.accountDao = ad;
    }
}