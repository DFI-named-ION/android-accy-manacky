package com.example.accymanacky.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.accymanacky.model.AccountModel;

import java.util.List;

@Dao
public interface IAccountDao {
    @Query("SELECT * FROM AccountModel")
    List<AccountModel> getAll();

    @Query("SELECT * FROM AccountModel")
    LiveData<List<AccountModel>> getAllLiveData();

    @Query("SELECT * FROM AccountModel WHERE owner_email=:owner")
    LiveData<List<AccountModel>> getAllLiveDataByOwner(String owner);

    @Query("SELECT * FROM AccountModel WHERE id IN (:ids)")
    List<AccountModel> getAllByIds(int[] ids);

    @Query("SELECT * FROM AccountModel WHERE id=:id LIMIT 1")
    AccountModel findById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AccountModel task);

    @Update
    void update(AccountModel task);

    @Delete
    void delete(AccountModel task);
}