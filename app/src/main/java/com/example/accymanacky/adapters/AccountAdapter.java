package com.example.accymanacky.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.accymanacky.R;
import com.example.accymanacky.model.AccountModel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    private List<AccountModel> accountList;
    private BiConsumer<AccountModel, View> onAccountClickListener;
    private Consumer<AccountModel> onAccountLongClickListener;

    public AccountAdapter(BiConsumer<AccountModel, View> onAccountClickListener, Consumer<AccountModel> onAccountLongClickListener) {
        this.accountList = new ArrayList<>();
        this.onAccountClickListener = onAccountClickListener;
        this.onAccountLongClickListener = onAccountLongClickListener;
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView titleView;

        public AccountViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.account_image);
            titleView = itemView.findViewById(R.id.account_title);
        }
    }

    public void setAccounts(List<AccountModel> accounts) {
        this.accountList = accounts;
        notifyDataSetChanged();
    }

    public int getPosition(AccountModel accountModel) {
        return this.accountList.indexOf(accountModel);
    }

    // Adapter methods
    @Override
    public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_item, parent, false);
        return new AccountViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AccountViewHolder holder, int position) {
        AccountModel account = accountList.get(position);
        holder.titleView.setText(account.title);
        Glide.with(holder.itemView.getContext())
                .load(account.image)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(view -> {
            onAccountClickListener.accept(account, holder.itemView);
        });

        holder.itemView.setOnLongClickListener(view -> {
            onAccountLongClickListener.accept(account);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }
}