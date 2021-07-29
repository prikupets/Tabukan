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
@Entity(tableName = "tabukan.associations")
public class DbAssociation implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "card_id")
    private Integer cardId;

    @ColumnInfo(name = "localized_text_id")
    private Integer localizedTextId;

    protected DbAssociation(Parcel in) {
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
    }

    public static final Creator<DbAssociation> CREATOR = new Creator<DbAssociation>() {
        @Override
        public DbAssociation createFromParcel(Parcel in) {
            return new DbAssociation(in);
        }

        @Override
        public DbAssociation[] newArray(int size) {
            return new DbAssociation[size];
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
    }

    @Override
    public @NotNull String toString() {
        return "DbAssociation{" +
                "id=" + id +
                ", cardId=" + cardId +
                ", localizedTextId=" + localizedTextId +
                '}';
    }
}
