package ru.granby.tabukan.model.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.granby.tabukan.model.data.database.dao.game.CardDao;
import ru.granby.tabukan.model.data.database.entity.game.DbCard;
import ru.granby.tabukan.model.data.database.entity.game.DbDeck;
import ru.granby.tabukan.model.data.database.entity.game.DbHeader;
import ru.granby.tabukan.model.data.database.entity.game.DbAssociation;
import ru.granby.tabukan.model.data.database.entity.localization.Localized;

@Database(entities = {
        DbCard.class,
        DbDeck.class,
        DbHeader.class,
        DbAssociation.class,
        Localized.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CardDao getCardDao();
}