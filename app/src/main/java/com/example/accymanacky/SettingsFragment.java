package com.example.accymanacky;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.android.material.slider.Slider;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {
    public SettingsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        View mainView = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = mainView.findViewById(R.id.recyclerView);

        Button exit_btn = view.findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this.getActivity(), LoginActivity.class));
        });

        Slider columns = view.findViewById(R.id.column_slider);
        columns.addOnChangeListener((a, b, c) -> {
            MainActivity activity = (MainActivity) getActivity();
            if (activity != null) {
                activity.setColumnCount((int) b);
            }
        });

        return view;
    }
}