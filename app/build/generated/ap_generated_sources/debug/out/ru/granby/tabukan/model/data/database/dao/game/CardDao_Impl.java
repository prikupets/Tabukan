package ru.granby.tabukan.model.data.database.dao.game;

import android.database.Cursor;
import androidx.collection.LongSparseArray;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.rxjava3.EmptyResultSetException;
import androidx.room.rxjava3.RxRoom;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import io.reactivex.rxjava3.core.Single;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import ru.granby.tabukan.model.data.database.entity.game.DbAssociation;
import ru.granby.tabukan.model.data.database.entity.game.DbCard;
import ru.granby.tabukan.model.data.database.entity.game.DbHeader;
import ru.granby.tabukan.model.data.database.entity.localization.Localized;
import ru.granby.tabukan.model.data.database.relations.game.Association;
import ru.granby.tabukan.model.data.database.relations.game.Card;
import ru.granby.tabukan.model.data.database.relations.game.Header;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CardDao_Impl implements CardDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DbCard> __insertionAdapterOfDbCard;

  private final EntityDeletionOrUpdateAdapter<DbCard> __deletionAdapterOfDbCard;

  private final EntityDeletionOrUpdateAdapter<DbCard> __updateAdapterOfDbCard;

  public CardDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDbCard = new EntityInsertionAdapter<DbCard>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `tabukan.cards` (`id`,`deck_id`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DbCard value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getDeckId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getDeckId());
        }
      }
    };
    this.__deletionAdapterOfDbCard = new EntityDeletionOrUpdateAdapter<DbCard>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `tabukan.cards` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DbCard value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
      }
    };
    this.__updateAdapterOfDbCard = new EntityDeletionOrUpdateAdapter<DbCard>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `tabukan.cards` SET `id` = ?,`deck_id` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DbCard value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getDeckId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getDeckId());
        }
        if (value.getId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.getId());
        }
      }
    };
  }

  @Override
  public void insert(final DbCard obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDbCard.insert(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final DbCard obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfDbCard.handle(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final DbCard obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfDbCard.handle(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Single<Card> getByIndex(final int index) {
    final String _sql = "SELECT * FROM 'tabukan.cards' LIMIT 1 OFFSET ?;";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, index);
    return RxRoom.createSingle(new Callable<Card>() {
      @Override
      public Card call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfDeckId = CursorUtil.getColumnIndexOrThrow(_cursor, "deck_id");
            final LongSparseArray<Header> _collectionHeader = new LongSparseArray<Header>();
            final LongSparseArray<ArrayList<Association>> _collectionAssociations = new LongSparseArray<ArrayList<Association>>();
            while (_cursor.moveToNext()) {
              if (!_cursor.isNull(_cursorIndexOfId)) {
                final long _tmpKey = _cursor.getLong(_cursorIndexOfId);
                _collectionHeader.put(_tmpKey, null);
              }
              if (!_cursor.isNull(_cursorIndexOfId)) {
                final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
                ArrayList<Association> _tmpAssociationsCollection = _collectionAssociations.get(_tmpKey_1);
                if (_tmpAssociationsCollection == null) {
                  _tmpAssociationsCollection = new ArrayList<Association>();
                  _collectionAssociations.put(_tmpKey_1, _tmpAssociationsCollection);
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiptabukanHeadersAsruGranbyTabukanModelDataDatabaseRelationsGameHeader(_collectionHeader);
            __fetchRelationshiptabukanAssociationsAsruGranbyTabukanModelDataDatabaseRelationsGameAssociation(_collectionAssociations);
            final Card _result;
            if(_cursor.moveToFirst()) {
              final DbCard _tmpDbCard;
              if (! (_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfDeckId))) {
                _tmpDbCard = new DbCard();
                final Integer _tmpId;
                if (_cursor.isNull(_cursorIndexOfId)) {
                  _tmpId = null;
                } else {
                  _tmpId = _cursor.getInt(_cursorIndexOfId);
                }
                _tmpDbCard.setId(_tmpId);
                final Integer _tmpDeckId;
                if (_cursor.isNull(_cursorIndexOfDeckId)) {
                  _tmpDeckId = null;
                } else {
                  _tmpDeckId = _cursor.getInt(_cursorIndexOfDeckId);
                }
                _tmpDbCard.setDeckId(_tmpDeckId);
              }  else  {
                _tmpDbCard = null;
              }
              Header _tmpHeader = null;
              if (!_cursor.isNull(_cursorIndexOfId)) {
                final long _tmpKey_2 = _cursor.getLong(_cursorIndexOfId);
                _tmpHeader = _collectionHeader.get(_tmpKey_2);
              }
              ArrayList<Association> _tmpAssociationsCollection_1 = null;
              if (!_cursor.isNull(_cursorIndexOfId)) {
                final long _tmpKey_3 = _cursor.getLong(_cursorIndexOfId);
                _tmpAssociationsCollection_1 = _collectionAssociations.get(_tmpKey_3);
              }
              if (_tmpAssociationsCollection_1 == null) {
                _tmpAssociationsCollection_1 = new ArrayList<Association>();
              }
              _result = new Card();
              _result.setDbCard(_tmpDbCard);
              _result.setHeader(_tmpHeader);
              _result.setAssociations(_tmpAssociationsCollection_1);
            } else {
              _result = null;
            }
            if(_result == null) {
              throw new EmptyResultSetException("Query returned empty result set: " + _statement.getSql());
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Single<Integer> getCardsCount() {
    final String _sql = "SELECT COUNT(*) FROM 'tabukan.cards'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return RxRoom.createSingle(new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          if(_result == null) {
            throw new EmptyResultSetException("Query returned empty result set: " + _statement.getSql());
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshiptabukanLocalizedsAsruGranbyTabukanModelDataDatabaseEntityLocalizationLocalized(
      final LongSparseArray<Localized> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<Localized> _tmpInnerMap = new LongSparseArray<Localized>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), null);
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiptabukanLocalizedsAsruGranbyTabukanModelDataDatabaseEntityLocalizationLocalized(_tmpInnerMap);
          _map.putAll(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<Localized>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiptabukanLocalizedsAsruGranbyTabukanModelDataDatabaseEntityLocalizationLocalized(_tmpInnerMap);
        _map.putAll(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`ru`,`en`,`es`,`de`,`fr`,`tr`,`it` FROM `tabukan.localizeds` WHERE `id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfRu = CursorUtil.getColumnIndexOrThrow(_cursor, "ru");
      final int _cursorIndexOfEn = CursorUtil.getColumnIndexOrThrow(_cursor, "en");
      final int _cursorIndexOfEs = CursorUtil.getColumnIndexOrThrow(_cursor, "es");
      final int _cursorIndexOfDe = CursorUtil.getColumnIndexOrThrow(_cursor, "de");
      final int _cursorIndexOfFr = CursorUtil.getColumnIndexOrThrow(_cursor, "fr");
      final int _cursorIndexOfTr = CursorUtil.getColumnIndexOrThrow(_cursor, "tr");
      final int _cursorIndexOfIt = CursorUtil.getColumnIndexOrThrow(_cursor, "it");
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final long _tmpKey = _cursor.getLong(_itemKeyIndex);
          if (_map.containsKey(_tmpKey)) {
            final Localized _item_1;
            final String _tmpRu;
            if (_cursor.isNull(_cursorIndexOfRu)) {
              _tmpRu = null;
            } else {
              _tmpRu = _cursor.getString(_cursorIndexOfRu);
            }
            _item_1 = new Localized(_tmpRu);
            final Long _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getLong(_cursorIndexOfId);
            }
            _item_1.setId(_tmpId);
            final String _tmpEn;
            if (_cursor.isNull(_cursorIndexOfEn)) {
              _tmpEn = null;
            } else {
              _tmpEn = _cursor.getString(_cursorIndexOfEn);
            }
            _item_1.setEn(_tmpEn);
            final String _tmpEs;
            if (_cursor.isNull(_cursorIndexOfEs)) {
              _tmpEs = null;
            } else {
              _tmpEs = _cursor.getString(_cursorIndexOfEs);
            }
            _item_1.setEs(_tmpEs);
            final String _tmpDe;
            if (_cursor.isNull(_cursorIndexOfDe)) {
              _tmpDe = null;
            } else {
              _tmpDe = _cursor.getString(_cursorIndexOfDe);
            }
            _item_1.setDe(_tmpDe);
            final String _tmpFr;
            if (_cursor.isNull(_cursorIndexOfFr)) {
              _tmpFr = null;
            } else {
              _tmpFr = _cursor.getString(_cursorIndexOfFr);
            }
            _item_1.setFr(_tmpFr);
            final String _tmpTr;
            if (_cursor.isNull(_cursorIndexOfTr)) {
              _tmpTr = null;
            } else {
              _tmpTr = _cursor.getString(_cursorIndexOfTr);
            }
            _item_1.setTr(_tmpTr);
            final String _tmpIt;
            if (_cursor.isNull(_cursorIndexOfIt)) {
              _tmpIt = null;
            } else {
              _tmpIt = _cursor.getString(_cursorIndexOfIt);
            }
            _item_1.setIt(_tmpIt);
            _map.put(_tmpKey, _item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshiptabukanHeadersAsruGranbyTabukanModelDataDatabaseRelationsGameHeader(
      final LongSparseArray<Header> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<Header> _tmpInnerMap = new LongSparseArray<Header>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), null);
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiptabukanHeadersAsruGranbyTabukanModelDataDatabaseRelationsGameHeader(_tmpInnerMap);
          _map.putAll(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<Header>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiptabukanHeadersAsruGranbyTabukanModelDataDatabaseRelationsGameHeader(_tmpInnerMap);
        _map.putAll(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`card_id`,`localized_text_id`,`image_url` FROM `tabukan.headers` WHERE `card_id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, true, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "card_id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCardId = CursorUtil.getColumnIndexOrThrow(_cursor, "card_id");
      final int _cursorIndexOfLocalizedTextId = CursorUtil.getColumnIndexOrThrow(_cursor, "localized_text_id");
      final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "image_url");
      final LongSparseArray<Localized> _collectionLocalizedText = new LongSparseArray<Localized>();
      while (_cursor.moveToNext()) {
        if (!_cursor.isNull(_cursorIndexOfLocalizedTextId)) {
          final long _tmpKey = _cursor.getLong(_cursorIndexOfLocalizedTextId);
          _collectionLocalizedText.put(_tmpKey, null);
        }
      }
      _cursor.moveToPosition(-1);
      __fetchRelationshiptabukanLocalizedsAsruGranbyTabukanModelDataDatabaseEntityLocalizationLocalized(_collectionLocalizedText);
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final long _tmpKey_1 = _cursor.getLong(_itemKeyIndex);
          if (_map.containsKey(_tmpKey_1)) {
            final Header _item_1;
            final DbHeader _tmpDbHeader;
            if (! (_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfCardId) && _cursor.isNull(_cursorIndexOfLocalizedTextId) && _cursor.isNull(_cursorIndexOfImageUrl))) {
              _tmpDbHeader = new DbHeader();
              final Integer _tmpId;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpId = null;
              } else {
                _tmpId = _cursor.getInt(_cursorIndexOfId);
              }
              _tmpDbHeader.setId(_tmpId);
              final Integer _tmpCardId;
              if (_cursor.isNull(_cursorIndexOfCardId)) {
                _tmpCardId = null;
              } else {
                _tmpCardId = _cursor.getInt(_cursorIndexOfCardId);
              }
              _tmpDbHeader.setCardId(_tmpCardId);
              final Integer _tmpLocalizedTextId;
              if (_cursor.isNull(_cursorIndexOfLocalizedTextId)) {
                _tmpLocalizedTextId = null;
              } else {
                _tmpLocalizedTextId = _cursor.getInt(_cursorIndexOfLocalizedTextId);
              }
              _tmpDbHeader.setLocalizedTextId(_tmpLocalizedTextId);
              final String _tmpImageUrl;
              if (_cursor.isNull(_cursorIndexOfImageUrl)) {
                _tmpImageUrl = null;
              } else {
                _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
              }
              _tmpDbHeader.setImageUrl(_tmpImageUrl);
            }  else  {
              _tmpDbHeader = null;
            }
            Localized _tmpLocalizedText = null;
            if (!_cursor.isNull(_cursorIndexOfLocalizedTextId)) {
              final long _tmpKey_2 = _cursor.getLong(_cursorIndexOfLocalizedTextId);
              _tmpLocalizedText = _collectionLocalizedText.get(_tmpKey_2);
            }
            _item_1 = new Header();
            _item_1.setDbHeader(_tmpDbHeader);
            _item_1.setLocalizedText(_tmpLocalizedText);
            _map.put(_tmpKey_1, _item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshiptabukanAssociationsAsruGranbyTabukanModelDataDatabaseRelationsGameAssociation(
      final LongSparseArray<ArrayList<Association>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<ArrayList<Association>> _tmpInnerMap = new LongSparseArray<ArrayList<Association>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiptabukanAssociationsAsruGranbyTabukanModelDataDatabaseRelationsGameAssociation(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<ArrayList<Association>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiptabukanAssociationsAsruGranbyTabukanModelDataDatabaseRelationsGameAssociation(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`card_id`,`localized_text_id` FROM `tabukan.associations` WHERE `card_id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, true, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "card_id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCardId = CursorUtil.getColumnIndexOrThrow(_cursor, "card_id");
      final int _cursorIndexOfLocalizedTextId = CursorUtil.getColumnIndexOrThrow(_cursor, "localized_text_id");
      final LongSparseArray<Localized> _collectionLocalizedText = new LongSparseArray<Localized>();
      while (_cursor.moveToNext()) {
        if (!_cursor.isNull(_cursorIndexOfLocalizedTextId)) {
          final long _tmpKey = _cursor.getLong(_cursorIndexOfLocalizedTextId);
          _collectionLocalizedText.put(_tmpKey, null);
        }
      }
      _cursor.moveToPosition(-1);
      __fetchRelationshiptabukanLocalizedsAsruGranbyTabukanModelDataDatabaseEntityLocalizationLocalized(_collectionLocalizedText);
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final long _tmpKey_1 = _cursor.getLong(_itemKeyIndex);
          ArrayList<Association> _tmpRelation = _map.get(_tmpKey_1);
          if (_tmpRelation != null) {
            final Association _item_1;
            final DbAssociation _tmpDbAssociation;
            if (! (_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfCardId) && _cursor.isNull(_cursorIndexOfLocalizedTextId))) {
              _tmpDbAssociation = new DbAssociation();
              final Integer _tmpId;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpId = null;
              } else {
                _tmpId = _cursor.getInt(_cursorIndexOfId);
              }
              _tmpDbAssociation.setId(_tmpId);
              final Integer _tmpCardId;
              if (_cursor.isNull(_cursorIndexOfCardId)) {
                _tmpCardId = null;
              } else {
                _tmpCardId = _cursor.getInt(_cursorIndexOfCardId);
              }
              _tmpDbAssociation.setCardId(_tmpCardId);
              final Integer _tmpLocalizedTextId;
              if (_cursor.isNull(_cursorIndexOfLocalizedTextId)) {
                _tmpLocalizedTextId = null;
              } else {
                _tmpLocalizedTextId = _cursor.getInt(_cursorIndexOfLocalizedTextId);
              }
              _tmpDbAssociation.setLocalizedTextId(_tmpLocalizedTextId);
            }  else  {
              _tmpDbAssociation = null;
            }
            Localized _tmpLocalizedText = null;
            if (!_cursor.isNull(_cursorIndexOfLocalizedTextId)) {
              final long _tmpKey_2 = _cursor.getLong(_cursorIndexOfLocalizedTextId);
              _tmpLocalizedText = _collectionLocalizedText.get(_tmpKey_2);
            }
            _item_1 = new Association();
            _item_1.setDbAssociation(_tmpDbAssociation);
            _item_1.setLocalizedText(_tmpLocalizedText);
            _tmpRelation.add(_item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
