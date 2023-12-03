package com.example.accymanacky;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.accymanacky.adapters.AccountAdapter;
import com.example.accymanacky.model.AccountModel;
import com.example.accymanacky.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private MainViewModel viewModel;
    private RecyclerView recyclerView;
    private AccountAdapter adapter;

    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        RelativeLayout overlay = view.findViewById(R.id.overlay);
        overlay.setOnClickListener(v -> {});

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        FloatingActionButton addItemBtn = view.findViewById(R.id.add_item_btn);
        addItemBtn.setOnClickListener(v -> {
            addItemBtn.setVisibility(View.GONE);
            RelativeLayout createBoard = view.findViewById(R.id.create_board);
            createBoard.setVisibility(View.VISIBLE);
            overlay.setVisibility(View.VISIBLE);
            overlay.bringToFront();
            createBoard.bringToFront();

            createBoard.findViewById(R.id.closeCreateBtn).setOnClickListener(vv -> {
                createBoard.setVisibility(View.INVISIBLE);
                overlay.setVisibility(View.INVISIBLE);
                addItemBtn.setVisibility(View.VISIBLE);
            });

            createBoard.findViewById(R.id.create_btn).setOnClickListener(vv -> {
                TextView image_field = createBoard.findViewById(R.id.create_board_account_image);
                TextView title_field = createBoard.findViewById(R.id.create_board_account_title);
                TextView info_field = createBoard.findViewById(R.id.create_board_account_info);
                TextView login_field = createBoard.findViewById(R.id.create_board_account_login);
                TextView password_field = createBoard.findViewById(R.id.create_board_account_password);

                boolean isDataValid = true;

                String url = image_field.getText().toString();
                if (!url.isEmpty() && !isValidUrl(url)) {
                    image_field.setError("Image url can not be empty! Or url is not valid!");
                    isDataValid = false;
                }

                String title = title_field.getText().toString();
                if (title.isEmpty()) {
                    title_field.setError("Title can not be empty!");
                    isDataValid = false;
                }

                if (isDataValid) {
                    AccountModel account = new AccountModel(
                            0,
                            title,
                            login_field.getText().toString(),
                            password_field.getText().toString(),
                            info_field.getText().toString(),
                            image_field.getText().toString(),
                            FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                            System.currentTimeMillis()
                    );

                    viewModel.createAccount(account); // create
                    //adapter.notifyItemChanged(position); // update

                    Toast.makeText(view.getContext(), "Successfully created!", Toast.LENGTH_SHORT).show();
                    createBoard.setVisibility(View.INVISIBLE);
                    overlay.setVisibility(View.INVISIBLE);
                    addItemBtn.setVisibility(View.VISIBLE);
                }
            });
        });

        adapter = new AccountAdapter(
                (account, obj) -> {
                    addItemBtn.setVisibility(View.GONE);
                    RelativeLayout infoBoard = view.findViewById(R.id.info_board);
                    overlay.setVisibility(View.VISIBLE);
                    overlay.bringToFront();
                    infoBoard.setVisibility(View.VISIBLE);
                    infoBoard.bringToFront();

                    ImageView img = infoBoard.findViewById(R.id.board_account_image);
                    Glide.with(getContext())
                            .load(account.image)
                            .into(img);
                    ((TextView)infoBoard.findViewById(R.id.board_account_title)).setText(account.title);
                    ((TextView)infoBoard.findViewById(R.id.board_account_info)).setText(account.info);

                    TextView login_field = infoBoard.findViewById(R.id.board_account_login);
                    login_field.setText(account.login.replaceAll(".", "*"));

                    TextView password_field = infoBoard.findViewById(R.id.board_account_password);
                    password_field.setText(account.password.replaceAll(".", "*"));

                    infoBoard.findViewById(R.id.closeInfoBtn).setOnClickListener(b -> {
                        infoBoard.setVisibility(View.INVISIBLE);
                        overlay.setVisibility(View.INVISIBLE);
                        addItemBtn.setVisibility(View.VISIBLE);
                    });

                    infoBoard.findViewById(R.id.toggleViewLoginBtn).setOnClickListener(cl -> {
                        String masked = login_field.getText().toString().replaceAll(".", "*");
                        if (login_field.getText().toString().equals(masked)) {
                                login_field.setText(account.login);
                        } else {
                            login_field.setText(masked);
                        }
                    });

                    infoBoard.findViewById(R.id.toggleViewPasswordBtn).setOnClickListener(cl -> {
                        String masked = password_field.getText().toString().replaceAll(".", "*");
                        if (password_field.getText().toString().equals(masked)) {
                            password_field.setText(account.password);
                        } else {
                            password_field.setText(masked);
                        }
                    });

                    infoBoard.findViewById(R.id.copyLoginBtn).setOnClickListener(cl -> {
                        Toast.makeText(getContext(), "Successfully copied to clipboard!", Toast.LENGTH_SHORT).show();
                        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Your login", account.login);
                        clipboard.setPrimaryClip(clip);
                    });

                    infoBoard.findViewById(R.id.copyPasswordBtn).setOnClickListener(cl -> {
                        Toast.makeText(getContext(), "Successfully copied to clipboard!", Toast.LENGTH_SHORT).show();
                        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Your password", account.password);
                        clipboard.setPrimaryClip(clip);
                    });

                    infoBoard.findViewById(R.id.edit_btn).setOnClickListener(v -> {
                        infoBoard.setVisibility(View.INVISIBLE);
                        overlay.setVisibility(View.INVISIBLE);
                        obj.performLongClick();
                    });

                    infoBoard.findViewById(R.id.remove_btn).setOnClickListener(v -> {
                        RelativeLayout removeBoard = view.findViewById(R.id.remove_board);
                        removeBoard.setVisibility(View.VISIBLE);
                        removeBoard.bringToFront();

                        infoBoard.setVisibility(View.INVISIBLE);
                        overlay.bringToFront();

                        removeBoard.findViewById(R.id.btn_approve_remove).setOnClickListener(vv -> {
                            viewModel.removeAccount(account); // remove
                            adapter.notifyDataSetChanged(); // update

                            removeBoard.setVisibility(View.INVISIBLE);
                            overlay.setVisibility(View.INVISIBLE);

                            Toast.makeText(getContext(), "Successfully removed!", Toast.LENGTH_SHORT).show();
                            addItemBtn.setVisibility(View.VISIBLE);
                        });

                        removeBoard.findViewById(R.id.btn_cancel_remove).setOnClickListener(vv -> {
                            removeBoard.setVisibility(View.INVISIBLE);
                            overlay.setVisibility(View.INVISIBLE);
                            addItemBtn.setVisibility(View.VISIBLE);
                        });
                    });
                },
                account -> {
                    addItemBtn.setVisibility(View.GONE);
                    RelativeLayout editBoard = view.findViewById(R.id.edit_board);
                    overlay.setVisibility(View.VISIBLE);
                    overlay.bringToFront();
                    editBoard.setVisibility(View.VISIBLE);
                    editBoard.bringToFront();

                    ((TextView)editBoard.findViewById(R.id.edit_board_account_title)).setText(account.title);
                    ((TextView)editBoard.findViewById(R.id.edit_board_account_info)).setText(account.info);

                    TextView image_field = editBoard.findViewById(R.id.edit_board_account_image);
                    image_field.setText(account.image);

                    TextView title_field = editBoard.findViewById(R.id.edit_board_account_title);
                    title_field.setText(account.title);

                    TextView info_field = editBoard.findViewById(R.id.edit_board_account_info);
                    info_field.setText(account.info);

                    TextView login_field = editBoard.findViewById(R.id.edit_board_account_login);
                    login_field.setText(account.login);

                    TextView password_field = editBoard.findViewById(R.id.edit_board_account_password);
                    password_field.setText(account.password);

                    editBoard.findViewById(R.id.closeEditBtn).setOnClickListener(b -> {
                        editBoard.setVisibility(View.INVISIBLE);
                        overlay.setVisibility(View.INVISIBLE);
                        addItemBtn.setVisibility(View.VISIBLE);
                    });

                    editBoard.findViewById(R.id.save_btn).setOnClickListener(v -> {
                        boolean isDataValid = true;

                        String url = image_field.getText().toString();
                        if (!url.isEmpty() && !isValidUrl(url)) {
                            image_field.setError("Image url can not be empty! Or url is not valid!");
                            isDataValid = false;
                        }

                        String title = title_field.getText().toString();
                        if (title.isEmpty()) {
                            title_field.setError("Title can not be empty!");
                            isDataValid = false;
                        }

                        if (isDataValid) {
                            int position = adapter.getPosition(account);

                            account.image = url;
                            account.title = title;
                            account.info = info_field.getText().toString();
                            account.login = login_field.getText().toString();
                            account.password = password_field.getText().toString();

                            viewModel.updateAccount(account); // save
                            adapter.notifyItemChanged(position); // update

                            Toast.makeText(getContext(), "Successfully saved!", Toast.LENGTH_SHORT).show();
                            editBoard.setVisibility(View.INVISIBLE);
                            overlay.setVisibility(View.INVISIBLE);
                            addItemBtn.setVisibility(View.VISIBLE);
                        }
                    });

                    editBoard.findViewById(R.id.cancel_btn).setOnClickListener(v -> {
                        editBoard.setVisibility(View.INVISIBLE);
                        overlay.setVisibility(View.INVISIBLE);
                        addItemBtn.setVisibility(View.VISIBLE);
                    });

                });
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getAccountLiveData().observe(getViewLifecycleOwner(), accounts -> {
            adapter.setAccounts(accounts);
        });

        return view;
    }

    public void updateColumnCount(int count) {
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), count));
        }
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}