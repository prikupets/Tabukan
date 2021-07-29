package ru.granby.tabukan.model.data.database.entity.game;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity(tableName = "tabukan.headers")
public class DbHeader implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "card_id")
    private Integer cardId;

    @ColumnInfo(name = "localized_text_id")
    private Integer localizedTextId;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    protected DbHeader(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            cardId = null;
        } else {
            cardId = in.readInt();
        }
        if (in.readByte() == 0) {
            localizedTextId = null;
        } else {
            localizedTextId = in.readInt();
        }
        imageUrl = in.readString();
    }

    public static final Creator<DbHeader> CREATOR = new Creator<DbHeader>() {
        @Override
        public DbHeader createFromParcel(Parcel in) {
            return new DbHeader(in);
        }

        @Override
        public DbHeader[] newArray(int size) {
            return new DbHeader[size];
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
            dest.writeInt(id);
        }
        if (cardId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(cardId);
        }
        if (localizedTextId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(localizedTextId);
        }
        dest.writeString(imageUrl);
    }

    @Override
    public @NotNull String toString() {
        return "DbHeader{" +
                "id=" + id +
                ", cardId=" + cardId +
                ", localizedTextId=" + localizedTextId +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
