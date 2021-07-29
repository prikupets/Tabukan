package ru.granby.tabukan.model.data.database.relations.game;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.jetbrains.annotations.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.granby.tabukan.model.data.database.entity.game.DbAssociation;
import ru.granby.tabukan.model.data.database.entity.localization.Localized;

@NoArgsConstructor
@Data
public class Association implements Parcelable {
    @Embedded
    private DbAssociation dbAssociation;

    @Relation(parentColumn = "localized_text_id",
            entityColumn = "id")
    private Localized localizedText;

    protected Association(Parcel in) {
        dbAssociation = in.readParcelable(DbAssociation.class.getClassLoader());
        localizedText = in.readParcelable(Localized.class.getClassLoader());
    }

    public static final Creator<Association> CREATOR = new Creator<Association>() {
        @Override
        public Association createFromParcel(Parcel in) {
            return new Association(in);
        }

        @Override
        public Association[] newArray(int size) {
            return new Association[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(dbAssociation, flags);
        dest.writeParcelable(localizedText, flags);
    }

    @Override
    public @NotNull String toString() {
        return "Association{" +
                "dbAssociation=" + dbAssociation +
                ", localizedText=" + localizedText +
                '}';
    }
}
