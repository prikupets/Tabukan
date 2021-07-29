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
@Entity(tableName = "tabukan.decks")
public class DbDeck implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "localized_name_id")
    private Integer localizedNameId;

    protected DbDeck(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            localizedNameId = null;
        } else {
            localizedNameId = in.readInt();
        }
    }

    public static final Creator<DbDeck> CREATOR = new Creator<DbDeck>() {
        @Override
        public DbDeck createFromParcel(Parcel in) {
            return new DbDeck(in);
        }

        @Override
        public DbDeck[] newArray(int size) {
            return new DbDeck[size];
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
        if (localizedNameId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(localizedNameId);
        }
    }

    @Override
    public @NotNull String toString() {
        return "DbDeck{" +
                "id=" + id +
                ", localizedNameId=" + localizedNameId +
                '}';
    }
}
