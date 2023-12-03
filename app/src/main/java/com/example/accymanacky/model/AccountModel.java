package com.example.accymanacky.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AccountModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name="title")
    public String title;
    @ColumnInfo(name="login")
    public String login;
    @ColumnInfo(name="password")
    public String password;
    @ColumnInfo(name="info")
    public String info;
    @ColumnInfo(name="image")
    public String image;
    @ColumnInfo(name="owner_email")
    public String owner_email;
    @ColumnInfo(name="timestamp")
    public long timestamp;

    public AccountModel() {}

    public AccountModel(int id, String title, String login, String password, String info, String image, String owner_email, long timestamp) {
        this.id = id;
        this.title = title;
        this.login = login;
        this.password = password;
        this.info = info;
        this.image = image;
        this.owner_email = owner_email;
        this.timestamp = timestamp;
    }

    protected AccountModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        login = in.readString();
        password = in.readString();
        info = in.readString();
        image = in.readString();
        owner_email = in.readString();

        if (in.readByte() == 0) {
            timestamp = 0;
        } else {
            timestamp = in.readLong();
        }
    }


    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(login);
        parcel.writeString(password);
        parcel.writeString(info);
        parcel.writeString(image);
        parcel.writeString(owner_email);

        if (timestamp == 0) {
            parcel.writeByte((byte)0);
        } else {
            parcel.writeByte((byte)1);
            parcel.writeLong(timestamp);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AccountModel> CREATOR = new Creator<AccountModel>() {
        @Override
        public AccountModel createFromParcel(Parcel in) { return new AccountModel(in); }

        @Override
        public AccountModel[] newArray(int size) { return new AccountModel[size]; }
    };
}