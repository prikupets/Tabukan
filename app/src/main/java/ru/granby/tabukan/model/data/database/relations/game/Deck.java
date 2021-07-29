package ru.granby.tabukan.model.data.database.relations.game;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.granby.tabukan.model.data.database.entity.game.DbCard;
import ru.granby.tabukan.model.data.database.entity.game.DbDeck;

@NoArgsConstructor
@Data
public class Deck implements Parcelable {
    @Embedded
    private DbDeck deck;

    @Relation(parentColumn = "id",
            entityColumn = "deck_id",
            entity = DbCard.class)
    private List<DbCard> cards;

    protected Deck(Parcel in) {
        deck = in.readParcelable(DbDeck.class.getClassLoader());
        cards = in.createTypedArrayList(DbCard.CREATOR);
    }

    public static final Creator<Deck> CREATOR = new Creator<Deck>() {
        @Override
        public Deck createFromParcel(Parcel in) {
            return new Deck(in);
        }

        @Override
        public Deck[] newArray(int size) {
            return new Deck[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(deck, flags);
        dest.writeTypedList(cards);
    }

    @Override
    public @NotNull String toString() {
        return "Deck{" +
                "deck=" + deck +
                ", cards=" + cards +
                '}';
    }
}
