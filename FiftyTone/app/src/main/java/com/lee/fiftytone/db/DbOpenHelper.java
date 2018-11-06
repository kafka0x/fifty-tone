package com.lee.fiftytone.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库管理辅助类
 *
 * @author Administrator
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static DbOpenHelper instance;
    private static final String TRADEDATA_TABLE_CREATE = "CREATE TABLE "
            + TradeDataDao.TABLE_NAME + " ("
            + TradeDataDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
            + TradeDataDao.COLUMN_NAME_DATE + " TEXT,"
            + TradeDataDao.COLUMN_NAME_MASENUM + " TEXT,"
            + TradeDataDao.COLUMN_NAME_MERID + " TEXT,"
            + TradeDataDao.COLUMN_NAME_USERID + " TEXT,"
            + TradeDataDao.COLUMN_NAME_PAYPLATNAME + " TEXT,"
            + TradeDataDao.COLUMN_NAME_ORDERID + " TEXT,"
            + TradeDataDao.COLUMN_NAME_MONEY + " TEXT,"
            + TradeDataDao.COLUMN_NAME_PRONAME + " TEXT,"
            + TradeDataDao.COLUMN_NAME_STATUS + " TEXT)";

    private DbOpenHelper(Context context) {
        super(context, "tradeDataDb", null, DATABASE_VERSION);
    }

    /**
     * 单例模式获取对象
     *
     * @param context
     * @return
     */
    public static DbOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TRADEDATA_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * 关闭数据库
     */
    public void closeDB() {
        if (instance != null) {
            try {
                SQLiteDatabase db = instance.getWritableDatabase();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance = null;
        }
    }
}
