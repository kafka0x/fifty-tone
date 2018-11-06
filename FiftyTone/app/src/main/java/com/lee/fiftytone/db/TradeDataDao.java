package com.lee.fiftytone.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.lee.fiftytone.util.PreferencesUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 交易数据表，存储交易数据
 *
 * @author Administrator
 */
public class TradeDataDao {
    public static final String TABLE_NAME = "tradeData";// 表名
    public static final String COLUMN_NAME_ID = "_id";// 数据id
    public static final String COLUMN_NAME_DATE = "date";// 时间
    public static final String COLUMN_NAME_MASENUM = "maseNum";// 机具流水号
    public static final String COLUMN_NAME_MERID = "merId";// 商户id
    public static final String COLUMN_NAME_USERID = "userId";// 用户id
    public static final String COLUMN_NAME_PAYPLATNAME = "payPlatName";// 支付平台名字
    public static final String COLUMN_NAME_ORDERID = "orderId";// 订单号
    public static final String COLUMN_NAME_MONEY = "money";// 支付金额
    public static final String COLUMN_NAME_PRONAME = "proName";// 支付金额
    public static final String COLUMN_NAME_STATUS = "status";// 交易状态

    private DbOpenHelper dbHelper;
    private Context context;

    public TradeDataDao(Context context) {
        dbHelper = DbOpenHelper.getInstance(context);
        this.context = context;
    }

    /**
     * 存入数据库；
     *
     * @param jo
     */
    public void saveData(JSONObject jo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_MERID, PreferencesUtil.getString(context,
                    PreferencesUtil.MER_ID, null));
            values.put(COLUMN_NAME_USERID, PreferencesUtil.getString(context,
                    PreferencesUtil.ID, null));
            try {
                values.put(COLUMN_NAME_DATE, jo.getString("date"));
                values.put(COLUMN_NAME_MASENUM, jo.getString("maseNum"));
                values.put(COLUMN_NAME_PAYPLATNAME, jo.getString("payPlatName"));
                values.put(COLUMN_NAME_ORDERID, jo.getString("orderId"));
                values.put(COLUMN_NAME_MONEY, jo.getString("money"));
                values.put(COLUMN_NAME_PRONAME, jo.getString("proName"));
                values.put(COLUMN_NAME_STATUS, jo.getString("status"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            db.insert(TABLE_NAME, null, values);
        }
    }

    /**
     * 读取表中的数据
     *
     * @param sort
     * @return
     */
    public JSONArray getData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        JSONArray cacheJsonArray = new JSONArray();
        Cursor cursor = null;
        if (db.isOpen()) {
            try {
                String sql = "";
                sql = "SELECT * FROM " + TABLE_NAME;
                cursor = db.rawQuery(sql, null);
            } catch (Exception e) {
                Toast.makeText(context, "获取数据失败：" + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
            while (cursor.moveToNext()) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("id", cursor.getInt(cursor
                            .getColumnIndex(COLUMN_NAME_ID)));
                    object.put("date", cursor.getString(cursor
                            .getColumnIndex(COLUMN_NAME_DATE)));
                    object.put("maseNum", cursor.getString(cursor
                            .getColumnIndex(COLUMN_NAME_MASENUM)));
                    object.put("merId", cursor.getString(cursor
                            .getColumnIndex(COLUMN_NAME_MERID)));
                    object.put("userId", cursor.getString(cursor
                            .getColumnIndex(COLUMN_NAME_USERID)));
                    object.put("payPlatName", cursor.getString(cursor
                            .getColumnIndex(COLUMN_NAME_PAYPLATNAME)));
                    object.put("orderId", cursor.getString(cursor
                            .getColumnIndex(COLUMN_NAME_ORDERID)));
                    object.put("money", cursor.getString(cursor
                            .getColumnIndex(COLUMN_NAME_MONEY)));
                    object.put("proName", cursor.getString(cursor
                            .getColumnIndex(COLUMN_NAME_PRONAME)));
                    object.put("status", cursor.getString(cursor
                            .getColumnIndex(COLUMN_NAME_STATUS)));
                    cacheJsonArray.put(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "加载数据失败：" + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
            cursor.close();
        }
        return cacheJsonArray;
    }

    /**
     * 根据id删除数据
     *
     * @param id
     */
    public void deleteById(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.execSQL("delete from " + TABLE_NAME + " where " + COLUMN_NAME_ID
                    + " = " + id);
        }
    }
}
