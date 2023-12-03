package com.example.accymanacky.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.accymanacky.App;
import com.example.accymanacky.model.AccountModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private LiveData<List<AccountModel>> accountLiveData = null;

    public void createAccount(AccountModel account) {
        App.getInstance().getAccountDao().insert(account);
    }

    public void updateAccount(AccountModel account) {
        App.getInstance().getAccountDao().update(account);
    }

    public void removeAccount(AccountModel account) {
        App.getInstance().getAccountDao().delete(account);
    }

    public LiveData<List<AccountModel>> getAccountLiveData() {
        if (accountLiveData == null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null && user.getEmail() != null) {
                accountLiveData = App.getInstance().getAccountDao().getAllLiveDataByOwner(user.getEmail());
            } else {
                accountLiveData = new MutableLiveData<>(new ArrayList<>());
            }
        }
        return accountLiveData;
    }
}