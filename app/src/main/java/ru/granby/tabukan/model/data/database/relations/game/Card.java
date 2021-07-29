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
import ru.granby.tabukan.model.data.database.entity.game.DbHeader;
import ru.granby.tabukan.model.data.database.entity.game.DbAssociation;

@NoArgsConstructor
@Data
public class Card implements Parcelable {
    @Embedded
    private DbCard dbCard;

//    @Relation(parentColumn = "deck_id",
//            entityColumn = "id",
//            entity = DbDeck.class)
//    private Deck deck;

    @Relation(parentColumn = "id",
            entityColumn = "card_id",
            entity = DbHeader.class)
    private Header header;

    @Relation(parentColumn = "id",
            entityColumn = "card_id",
            entity = DbAssociation.class)
    private List<Association> associations;

    protected Card(Parcel in) {
        dbCard = in.readParcelable(DbCard.class.getClassLoader());
        //deck = in.readParcelable(Deck.class.getClassLoader());
        header = in.readParcelable(Header.class.getClassLoader());
        associations = in.createTypedArrayList(Association.CREATOR);
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(dbCard, flags);
        //dest.writeParcelable(deck, flags);
        dest.writeParcelable(header, flags);
        dest.writeTypedList(associations);
    }

    @Override
    public @NotNull String toString() {
        return "Card{" +
                "dbCard=" + dbCard +
                ", header=" + header +
                ", associations=" + associations +
                '}';
    }
}
