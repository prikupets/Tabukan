package ru.granby.tabukan.model.data.database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ru.granby.tabukan.model.data.database.dao.game.CardDao;
import ru.granby.tabukan.model.data.database.dao.game.CardDao_Impl;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile CardDao _cardDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `tabukan.cards` (`id` INTEGER, `deck_id` INTEGER, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `tabukan.decks` (`id` INTEGER, `localized_name_id` INTEGER, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `tabukan.headers` (`id` INTEGER, `card_id` INTEGER, `localized_text_id` INTEGER, `image_url` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `tabukan.associations` (`id` INTEGER, `card_id` INTEGER, `localized_text_id` INTEGER, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `tabukan.localizeds` (`id` INTEGER, `ru` TEXT, `en` TEXT, `es` TEXT, `de` TEXT, `fr` TEXT, `tr` TEXT, `it` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'cbb6f62e2978facf7c9e82c0ace9e776')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `tabukan.cards`");
        _db.execSQL("DROP TABLE IF EXISTS `tabukan.decks`");
        _db.execSQL("DROP TABLE IF EXISTS `tabukan.headers`");
        _db.execSQL("DROP TABLE IF EXISTS `tabukan.associations`");
        _db.execSQL("DROP TABLE IF EXISTS `tabukan.localizeds`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTabukanCards = new HashMap<String, TableInfo.Column>(2);
        _columnsTabukanCards.put("id", new TableInfo.Column("id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabukanCards.put("deck_id", new TableInfo.Column("deck_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTabukanCards = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTabukanCards = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTabukanCards = new TableInfo("tabukan.cards", _columnsTabukanCards, _foreignKeysTabukanCards, _indicesTabukanCards);
        final TableInfo _existingTabukanCards = TableInfo.read(_db, "tabukan.cards");
        if (! _infoTabukanCards.equals(_existingTabukanCards)) {
          return new RoomOpenHelper.ValidationResult(false, "tabukan.cards(ru.granby.tabukan.model.data.database.entity.game.DbCard).\n"
                  + " Expected:\n" + _infoTabukanCards + "\n"
                  + " Found:\n" + _existingTabukanCards);
        }
        final HashMap<String, TableInfo.Column> _columnsTabukanDecks = new HashMap<String, TableInfo.Column>(2);
        _columnsTabukanDecks.put("id", new TableInfo.Column("id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabukanDecks.put("localized_name_id", new TableInfo.Column("localized_name_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTabukanDecks = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTabukanDecks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTabukanDecks = new TableInfo("tabukan.decks", _columnsTabukanDecks, _foreignKeysTabukanDecks, _indicesTabukanDecks);
        final TableInfo _existingTabukanDecks = TableInfo.read(_db, "tabukan.decks");
        if (! _infoTabukanDecks.equals(_existingTabukanDecks)) {
          return new RoomOpenHelper.ValidationResult(false, "tabukan.decks(ru.granby.tabukan.model.data.database.entity.game.DbDeck).\n"
                  + " Expected:\n" + _infoTabukanDecks + "\n"
                  + " Found:\n" + _existingTabukanDecks);
        }
        final HashMap<String, TableInfo.Column> _columnsTabukanHeaders = new HashMap<String, TableInfo.Column>(4);
        _columnsTabukanHeaders.put("id", new TableInfo.Column("id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabukanHeaders.put("card_id", new TableInfo.Column("card_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabukanHeaders.put("localized_text_id", new TableInfo.Column("localized_text_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabukanHeaders.put("image_url", new TableInfo.Column("image_url", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTabukanHeaders = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTabukanHeaders = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTabukanHeaders = new TableInfo("tabukan.headers", _columnsTabukanHeaders, _foreignKeysTabukanHeaders, _indicesTabukanHeaders);
        final TableInfo _existingTabukanHeaders = TableInfo.read(_db, "tabukan.headers");
        if (! _infoTabukanHeaders.equals(_existingTabukanHeaders)) {
          return new RoomOpenHelper.ValidationResult(false, "tabukan.headers(ru.granby.tabukan.model.data.database.entity.game.DbHeader).\n"
                  + " Expected:\n" + _infoTabukanHeaders + "\n"
                  + " Found:\n" + _existingTabukanHeaders);
        }
        final HashMap<String, TableInfo.Column> _columnsTabukanAssociations = new HashMap<String, TableInfo.Column>(3);
        _columnsTabukanAssociations.put("id", new TableInfo.Column("id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabukanAssociations.put("card_id", new TableInfo.Column("card_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabukanAssociations.put("localized_text_id", new TableInfo.Column("localized_text_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTabukanAssociations = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTabukanAssociations = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTabukanAssociations = new TableInfo("tabukan.associations", _columnsTabukanAssociations, _foreignKeysTabukanAssociations, _indicesTabukanAssociations);
        final TableInfo _existingTabukanAssociations = TableInfo.read(_db, "tabukan.associations");
        if (! _infoTabukanAssociations.equals(_existingTabukanAssociations)) {
          return new RoomOpenHelper.ValidationResult(false, "tabukan.associations(ru.granby.tabukan.model.data.database.entity.game.DbAssociation).\n"
                  + " Expected:\n" + _infoTabukanAssociations + "\n"
                  + " Found:\n" + _existingTabukanAssociations);
        }
        final HashMap<String, TableInfo.Column> _columnsTabukanLocalizeds = new HashMap<String, TableInfo.Column>(8);
        _columnsTabukanLocalizeds.put("id", new TableInfo.Column("id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabukanLocalizeds.put("ru", new TableInfo.Column("ru", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabukanLocalizeds.put("en", new TableInfo.Column("en", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabukanLocalizeds.put("es", new TableInfo.Column("es", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabukanLocalizeds.put("de", new TableInfo.Column("de", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabukanLocalizeds.put("fr", new TableInfo.Column("fr", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabukanLocalizeds.put("tr", new TableInfo.Column("tr", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabukanLocalizeds.put("it", new TableInfo.Column("it", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTabukanLocalizeds = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTabukanLocalizeds = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTabukanLocalizeds = new TableInfo("tabukan.localizeds", _columnsTabukanLocalizeds, _foreignKeysTabukanLocalizeds, _indicesTabukanLocalizeds);
        final TableInfo _existingTabukanLocalizeds = TableInfo.read(_db, "tabukan.localizeds");
        if (! _infoTabukanLocalizeds.equals(_existingTabukanLocalizeds)) {
          return new RoomOpenHelper.ValidationResult(false, "tabukan.localizeds(ru.granby.tabukan.model.data.database.entity.localization.Localized).\n"
                  + " Expected:\n" + _infoTabukanLocalizeds + "\n"
                  + " Found:\n" + _existingTabukanLocalizeds);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "cbb6f62e2978facf7c9e82c0ace9e776", "c5d73d6f135d31f6336d99a058641bb9");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "tabukan.cards","tabukan.decks","tabukan.headers","tabukan.associations","tabukan.localizeds");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `tabukan.cards`");
      _db.execSQL("DELETE FROM `tabukan.decks`");
      _db.execSQL("DELETE FROM `tabukan.headers`");
      _db.execSQL("DELETE FROM `tabukan.associations`");
      _db.execSQL("DELETE FROM `tabukan.localizeds`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CardDao.class, CardDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public CardDao getCardDao() {
    if (_cardDao != null) {
      return _cardDao;
    } else {
      synchronized(this) {
        if(_cardDao == null) {
          _cardDao = new CardDao_Impl(this);
        }
        return _cardDao;
      }
    }
  }
}
