package ru.granby.tabukan.model.data.database.dao.game;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import io.reactivex.rxjava3.core.Single;
import ru.granby.tabukan.model.data.database.dao.BaseDao;
import ru.granby.tabukan.model.data.database.entity.game.DbCard;
import ru.granby.tabukan.model.data.database.relations.game.Card;

@Dao
public interface CardDao extends BaseDao<DbCard> {
    @Query("SELECT * FROM 'tabukan.cards' LIMIT 1 OFFSET :index;")
    @Transaction
    Single<Card> getByIndex(int index);

    @Query("SELECT COUNT(*) FROM 'tabukan.cards'")
    Single<Integer> getCardsCount();
}
