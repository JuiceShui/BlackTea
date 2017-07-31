package com.yeeyuntech.framework.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yeeyuntech.framework.BuildConfig;
import com.yeeyuntech.framework.YYApplication;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "app.db";
    private static final int DB_VERSION = BuildConfig.VERSION_CODE;

    private static Context applicationContext;
    private static DbHelper dbHelper;
    private static SQLiteDatabase db = null;

    public static DbHelper getInstance() {
        if (dbHelper == null) {
            applicationContext = YYApplication.getInstance();
            dbHelper = new DbHelper(applicationContext, DB_NAME, null, BuildConfig.VERSION_CODE);
        }
        return dbHelper;
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    /**
     * 应用创建时执行一次，应用替换时不执行
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            File file = applicationContext.getDatabasePath(DB_NAME);
            file.delete();
        }
    }

    /**
     * 缓存网络请求数据到数据库
     *
     * @param url      网络请求地址
     * @param params   网络请求参数
     * @param response 网络请求返回
     */
    public void saveToDb(String url, Map<String, String> params, String response) {
        // 使用MD5处理url得到表名
        String tableName = uglyMD5(url);
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
        Set<String> fields = null;
        if (params != null) {
            fields = params.keySet();
        }

        if (!isTableExist(tableName)) {
            StringBuilder builder = new StringBuilder();
            builder.append("create table ").append(tableName).append(" (");
            if (params != null) {
                for (String field : fields) {
                    builder.append(" ").append(field).append(" text,");
                }
            }
            builder.append("content" + " text" + " )");
//            LogUtils.i("DBPrint:", "db create：" + builder.toString());
            db.execSQL(builder.toString());
        }
        ContentValues values = new ContentValues();
        if (params != null) {
            for (String field : fields) {
                values.put(field, params.get(field));
            }
        }
        values.put("content", response);


        StringBuilder builder = new StringBuilder();

        if (params != null) {
//            builder.append("WHERE ");
            for (String field : fields) {
                builder.append(field).append(" ").append("=").append("\'").append(params.get(field)).append("\'");
                builder.append(" AND ");
            }
            builder.delete(builder.length() - 5, builder.length() - 1);
        }
//        LogUtils.i("DBPrint:", "db delete：" + builder.toString());
        db.delete(tableName, builder.toString(), null);
        db.insert(tableName, null, values);
    }

    /**
     * 从数据库获取网络请求缓存数据
     *
     * @param url    网络请求地址
     * @param params 网络请求参数
     * @return 缓存数据
     */
    public String getCache(String url, Map<String, String> params) {
        // 使用MD5处理url得到表名
        String tableName = uglyMD5(url);
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
        Set<String> fields = null;
        if (params != null) {
            fields = params.keySet();
        }
        if (!isTableExist(tableName)) {
            StringBuilder builder = new StringBuilder();
            builder.append("create table ").append(tableName).append(" (");
            if (params != null) {
                for (String field : fields) {
                    builder.append(" ").append(field).append(" text,");
                }
            }
            builder.append("content" + " text" + " )");
//            LogUtils.i("DBPrint:", "db create：" + builder.toString());
            db.execSQL(builder.toString());
        }
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM ").append(tableName);
        if (params != null) {
            builder.append(" WHERE ");
            for (String field : fields) {
                builder.append(field).append(" ").append("=").append("\'").append(params.get(field)).append("\'");
                builder.append(" AND ");
            }
            builder.delete(builder.length() - 5, builder.length() - 1);
        }
//        LogUtils.i("DBPrint:", "db query：" + builder.toString());
        Cursor cursor = db.rawQuery(builder.toString(), null);
        String jsonString = "";
        if (cursor.moveToFirst()) {
            do {
                jsonString = cursor.getString(cursor.getColumnIndex("content"));
            } while (cursor.moveToNext());

        }
        cursor.close();
        return jsonString;

//        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
//        String jsonString = "";
//        if (cursor.moveToFirst()) {
//            do {
//                jsonString = cursor.getString(cursor.getColumnIndex(tableName + "Json"));
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//
//        return jsonString;

    }

    /**
     * 清空某个表
     *
     * @param tableName 表名
     */
    public void clearDatabaseTable(String tableName) {
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
        db.delete(tableName, "id >= ?", new String[]{"0"});
    }

    /**
     * 判断某张表是否存在
     *
     * @param tabName 表名
     * @return
     */
    private boolean isTableExist(String tabName) {
        boolean result = false;
        if (tabName == null) {
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbHelper.getReadableDatabase();//此this是继承SQLiteOpenHelper类得到的
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='" + tabName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
            cursor.close();
        } catch (Exception e) {

        }
        return result;
    }

    /**
     * @param inputStr
     * @return
     */
    public static String uglyMD5(String inputStr) {
        BigInteger bigInteger = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] inputData = inputStr.getBytes();
            md.update(inputData);
            bigInteger = new BigInteger(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(bigInteger.toString(16));
        String s = m.replaceAll("").replace("-", "");
        return s;
    }
}
