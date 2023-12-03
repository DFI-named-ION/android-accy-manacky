package com.example.accymanacky;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accymanacky.adapters.AccountAdapter;
import com.example.accymanacky.model.AccountModel;
import com.example.accymanacky.viewmodel.MainViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        MaterialButton settingsBtn = findViewById(R.id.settingsBtn);
        MaterialButton storageBtn = findViewById(R.id.storageBtn);

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        SettingsFragment settingsFragment = (SettingsFragment) getSupportFragmentManager().findFragmentById(R.id.settingsFragment);

        getSupportFragmentManager().beginTransaction().hide(settingsFragment).commit();

        settingsBtn.setOnClickListener(v -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.show(settingsFragment);
            transaction.hide(mainFragment);
            transaction.commit();
        });

        storageBtn.setOnClickListener(v -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.show(mainFragment);
            transaction.hide(settingsFragment);
            transaction.commit();
        });
    }

    public void setColumnCount(int count) {
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        if (mainFragment != null) {
            mainFragment.updateColumnCount(count);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        }
    }
}