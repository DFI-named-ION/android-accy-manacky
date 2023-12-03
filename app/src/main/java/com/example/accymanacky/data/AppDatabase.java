package com.example.accymanacky.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.accymanacky.model.AccountModel;
@Database(entities = {AccountModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract IAccountDao accountDao();
}
