package ru.granby.tabukan.model.data.database.relations.game;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.jetbrains.annotations.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.granby.tabukan.model.data.database.entity.game.DbHeader;
import ru.granby.tabukan.model.data.database.entity.localization.Localized;

@NoArgsConstructor
@Data
public class Header implements Parcelable {
    @Embedded
    private DbHeader dbHeader;

//    @Relation(parentColumn = "card_id",
//            entityColumn = "id",
//            entity = DbCard.class)
//    private Card card;

    @Relation(parentColumn = "localized_text_id",
            entityColumn = "id")
    private Localized localizedText;

    protected Header(Parcel in) {
        dbHeader = in.readParcelable(DbHeader.class.getClassLoader());
        localizedText = in.readParcelable(Localized.class.getClassLoader());
    }

    public static final Creator<Header> CREATOR = new Creator<Header>() {
        @Override
        public Header createFromParcel(Parcel in) {
            return new Header(in);
        }

        @Override
        public Header[] newArray(int size) {
            return new Header[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(dbHeader, flags);
        dest.writeParcelable(localizedText, flags);
    }

    @Override
    public @NotNull String toString() {
        return "Header{" +
                "dbHeader=" + dbHeader +
                ", localizedText=" + localizedText +
                '}';
    }
}
