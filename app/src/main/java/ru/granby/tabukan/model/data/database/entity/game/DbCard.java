package ru.granby.tabukan.model.data.database.entity.game;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import lombok.*;


@NoArgsConstructor
@Data
@Entity(tableName = "tabukan.cards")
public class DbCard implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "deck_id")
    private Integer deckId;


    protected DbCard(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            deckId = null;
        } else {
            deckId = in.readInt();
        }
    }

    public static final Creator<DbCard> CREATOR = new Creator<DbCard>() {
        @Override
        public DbCard createFromParcel(Parcel in) {
            return new DbCard(in);
        }

        @Override
        public DbCard[] newArray(int size) {
            return new DbCard[size];
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
        if (deckId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(deckId);
        }
    }

    @Override
    public @NotNull String toString() {
        return "DbCard{" +
                "id=" + id +
                ", deckId=" + deckId +
                '}';
    }
}

