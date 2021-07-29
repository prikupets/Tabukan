package ru.granby.tabukan.model.data.database.entity.localization;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import lombok.*;

@Data
@Entity(tableName = "tabukan.localizeds")
public class Localized implements Parcelable {
    private static final String TAG = "~Localized";

    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;
    
    private String ru;

    private String en;

    private String es;

    private String de;

    private String fr;

    private String tr;

    private String it;

    public Localized(String ru) {
        this.ru = ru;
    }

    protected Localized(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        ru = in.readString();
        en = in.readString();
        es = in.readString();
        de = in.readString();
        fr = in.readString();
        tr = in.readString();
        it = in.readString();
    }

    public static final Creator<Localized> CREATOR = new Creator<Localized>() {
        @Override
        public Localized createFromParcel(Parcel in) {
            return new Localized(in);
        }

        @Override
        public Localized[] newArray(int size) {
            return new Localized[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(ru);
        dest.writeString(en);
        dest.writeString(es);
        dest.writeString(de);
        dest.writeString(fr);
        dest.writeString(tr);
        dest.writeString(it);
    }
}
