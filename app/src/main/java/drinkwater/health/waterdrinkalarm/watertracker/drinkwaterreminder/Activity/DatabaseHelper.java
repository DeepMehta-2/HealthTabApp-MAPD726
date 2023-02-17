package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.AppPref;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.WaterIntake;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_cup_master = "CREATE TABLE cup_master(cup_id INTEGER PRIMARY KEY AUTOINCREMENT,cup_size INTEGER,image_id INTEGER,is_current INTEGER)";
    private static final String CREATE_TABLE_daily_history = "CREATE TABLE daily_history(date TEXT PRIMARY KEY,tot_ml INTEGER,weight INTEGER)";
    private static final String CREATE_TABLE_water_intake = "CREATE TABLE water_intake(wi_id INTEGER PRIMARY KEY AUTOINCREMENT,wi_date TEXT,wi_time TEXT,cup_id INTEGER,wi_ml INTEGER,wi_cup_size INTEGER,wi_new_cup_size INTEGER)";
    private static final String DATABASE_NAME = "waterreminder";
    private static final int DATABASE_VERSION = 2;
    private static final String KEY_ID = "cup_id";
    private static final String KEY_cupsize = "cup_size";
    private static final String KEY_date = "date";
    private static final String KEY_iamge = "image_id";
    private static final String KEY_iscurrent = "is_current";
    private static final String KEY_tot_ml = "tot_ml";
    private static final String KEY_weight = "weight";
    private static final String KEY_wi_cup = "wi_cup_size";
    private static final String KEY_wi_date = "wi_date";
    private static final String KEY_wi_id = "wi_id";
    private static final String KEY_wi_ml = "wi_ml";
    private static final String KEY_wi_new_cup_size = "wi_new_cup_size";
    private static final String KEY_wi_time = "wi_time";
    private static final String LOG = "DatabaseHelper";
    private static final String TABLE_cupmaster = "cup_master";
    private static final String TABLE_dailyhistory = "daily_history";
    private static final String TABLE_waterintake = "water_intake";

    public boolean addwater(int i) {
        return true;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CREATE_TABLE_cup_master);
        sQLiteDatabase.execSQL(CREATE_TABLE_water_intake);
        sQLiteDatabase.execSQL(CREATE_TABLE_daily_history);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS cup_master");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS water_intake");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS daily_history");
        onCreate(sQLiteDatabase);
    }

    public boolean insertData(int i, int i2, int i3) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_cupsize, Integer.valueOf(i));
        contentValues.put(KEY_iamge, Integer.valueOf(i2));
        contentValues.put(KEY_iscurrent, Integer.valueOf(i3));
        return writableDatabase.insert(TABLE_cupmaster, null, contentValues) != -1;
    }

    public int insertWaterIntake(String str, String str2, int i, int i2, int i3, Context context) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_wi_date, str);
        contentValues.put(KEY_wi_time, str2);
        contentValues.put(KEY_ID, Integer.valueOf(i));
        contentValues.put(KEY_wi_ml, Integer.valueOf(i2));
        contentValues.put(KEY_wi_cup, Integer.valueOf(4));
        contentValues.put(KEY_wi_new_cup_size, Integer.valueOf(i3));
        long insert = writableDatabase.insert(TABLE_waterintake, null, contentValues);
        setverable(context);
        return insert == -1 ? -1 : (int) insert;
    }

    public boolean call_history(String str, int i, int i2) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        int i3 = i2 * 40;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM daily_history WHERE date ='");
        stringBuilder.append(format);
        stringBuilder.append("'");
        Cursor rawQuery = writableDatabase.rawQuery(stringBuilder.toString(), null);
        if (rawQuery.moveToFirst()) {
            rawQuery.close();
            return true;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_date, format);
        contentValues.put(KEY_tot_ml, Integer.valueOf(i3));
        contentValues.put(KEY_weight, Integer.valueOf(i2));
        if (writableDatabase.insert(TABLE_dailyhistory, null, contentValues) == -1) {
            return false;
        }
        if (rawQuery == null || rawQuery.isClosed()) {
            return true;
        }
        rawQuery.close();
        return true;
    }

    public void setverable(Context context) {
        Homefragment.maxML = getMl(context);
        Homefragment.currentML = getCurrentDrinkedWater();
    }

    public int getMl(Context context) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM daily_history WHERE date ='");
        stringBuilder.append(format);
        stringBuilder.append("'");
        Cursor rawQuery = writableDatabase.rawQuery(stringBuilder.toString(), null);
        int i;
        if (rawQuery.moveToFirst()) {
            i = rawQuery.getInt(1);
            rawQuery.close();
            return i;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(AppPref.getWeight(context));
        stringBuilder.append("");
        i = Integer.parseInt(stringBuilder.toString());
        int i2 = i * 40;
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_date, format);
        contentValues.put(KEY_tot_ml, Integer.valueOf(i2));
        contentValues.put(KEY_weight, Integer.valueOf(i));
        return writableDatabase.insert(TABLE_dailyhistory, null, contentValues) == -1 ? 0 : i2;
    }

    public void updateweight(Context context, int i) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        int i2 = i * 40;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("UPDATE daily_history set tot_ml =");
            stringBuilder.append(i2);
            stringBuilder.append(" , ");
            stringBuilder.append(KEY_weight);
            stringBuilder.append(" = ");
            stringBuilder.append(i);
            stringBuilder.append(" where ");
            stringBuilder.append(KEY_date);
            stringBuilder.append(" ='");
            stringBuilder.append(format);
            stringBuilder.append("'");
            String stringBuilder2 = stringBuilder.toString();
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("");
            stringBuilder3.append(stringBuilder2);
            Log.d("updateQuery", stringBuilder3.toString());
            writableDatabase.execSQL(stringBuilder2);
            setverable(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateml(Context context, int i, int i2, int i3) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("UPDATE water_intake  set wi_new_cup_size =");
            stringBuilder.append(i);
            stringBuilder.append(" , ");
            stringBuilder.append(KEY_wi_cup);
            stringBuilder.append(" = ");
            stringBuilder.append(i2);
            stringBuilder.append(" where ");
            stringBuilder.append(KEY_wi_id);
            stringBuilder.append(" = ");
            stringBuilder.append(i3);
            String stringBuilder2 = stringBuilder.toString();
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("");
            stringBuilder3.append(stringBuilder2);
            Log.d("updateQuery", stringBuilder3.toString());
            writableDatabase.execSQL(stringBuilder2);
            setverable(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCurrentDrinkedWater() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT IFNULL(sum(wi_new_cup_size),0) FROM water_intake WHERE wi_date ='");
        stringBuilder.append(format);
        stringBuilder.append("'");
        Cursor rawQuery = writableDatabase.rawQuery(stringBuilder.toString(), null);
        if (rawQuery.moveToFirst()) {
            return rawQuery.getInt(0);
        }
        if (rawQuery == null || rawQuery.isClosed()) {
            return 0;
        }
        rawQuery.close();
        return 0;
    }

    public int getCurrentCupSize() {
        Cursor rawQuery = getWritableDatabase().rawQuery("SELECT cup_size,cup_id FROM cup_master WHERE is_current = 1", null);
        if (!rawQuery.moveToFirst()) {
            return 0;
        }
        int i = rawQuery.getInt(0);
        MyAdapter.selectedPosition = rawQuery.getInt(1);
        rawQuery.close();
        return i;
    }

    public Cursor getAllData() {
        Cursor rawQuery = getWritableDatabase().rawQuery("select tot_ml from daily_history", null);
        rawQuery.close();
        return rawQuery;
    }

    public ArrayList<cupmaster> getData() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ArrayList<cupmaster> arrayList = new ArrayList();
        Cursor rawQuery = writableDatabase.rawQuery("select * from cup_master", null);
        if (rawQuery.moveToFirst()) {
            do {
                cupmaster cupmaster = new cupmaster();
                cupmaster.setCup_id(rawQuery.getInt(rawQuery.getColumnIndex(KEY_ID)));
                cupmaster.setCup_size(rawQuery.getInt(rawQuery.getColumnIndex(KEY_cupsize)));
                cupmaster.setImage_id(rawQuery.getInt(rawQuery.getColumnIndex(KEY_iamge)));
                cupmaster.setIs_current(rawQuery.getInt(rawQuery.getColumnIndex(KEY_iscurrent)));
                arrayList.add(cupmaster);
            } while (rawQuery.moveToNext());
        }
        if (!(rawQuery == null || rawQuery.isClosed())) {
            rawQuery.close();
        }
        return arrayList;
    }

    public int getcurrentcupid() {
        Cursor rawQuery = getWritableDatabase().rawQuery("select cup_id from cup_master where is_current=1", null);
        if (rawQuery.moveToFirst()) {
            return rawQuery.getInt(rawQuery.getColumnIndex(KEY_ID));
        }
        if (!(rawQuery == null || rawQuery.isClosed())) {
            rawQuery.close();
        }
        return -1;
    }

    public int getHydrardeCount() {
        Cursor rawQuery = getWritableDatabase().rawQuery("select IFNULL(count(distinct(wi_date)),0) total_days from water_intake", null);
        if (rawQuery.moveToFirst()) {
            return rawQuery.getInt(rawQuery.getColumnIndex("total_days"));
        }
        if (!(rawQuery == null || rawQuery.isClosed())) {
            rawQuery.close();
        }
        return -1;
    }

    public int getWeeklyAverage() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String format = new SimpleDateFormat("yyyy").format(new Date());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT IFNULL(SUM(wi_new_cup_size)/count(distinct(wi_date)),0) weekavg from water_intake where wi_date > (SELECT DATETIME('now', '-7 day')) AND strftime('%Y',wi_date)='");
        stringBuilder.append(format);
        stringBuilder.append("'");
        Cursor rawQuery = writableDatabase.rawQuery(stringBuilder.toString(), null);
        if (rawQuery.moveToFirst()) {
            return rawQuery.getInt(rawQuery.getColumnIndex("weekavg"));
        }
        if (!(rawQuery == null || rawQuery.isClosed())) {
            rawQuery.close();
        }
        return -1;
    }

    public int getMonthlyAverage() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        new SimpleDateFormat("MM").format(new Date());
        String format = new SimpleDateFormat("yyyy").format(new Date());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select IFNULL(SUM(wi_new_cup_size)/count(distinct(wi_date)),0) monthavg from water_intake  where wi_date > (SELECT DATETIME('now', '-30 day')) AND strftime('%Y',wi_date)='");
        stringBuilder.append(format);
        stringBuilder.append("'");
        Cursor rawQuery = writableDatabase.rawQuery(stringBuilder.toString(), null);
        if (rawQuery.moveToFirst()) {
            return rawQuery.getInt(rawQuery.getColumnIndex("monthavg"));
        }
        if (!(rawQuery == null || rawQuery.isClosed())) {
            rawQuery.close();
        }
        return -1;
    }

    public int getDailyFreq() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        String format = new SimpleDateFormat("MM").format(new Date());
        String format2 = new SimpleDateFormat("yyyy").format(new Date());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select IFNULL(count(*)/count(distinct(wi_date)),0) per from water_intake where strftime('%m', wi_date) = '");
        stringBuilder.append(format);
        stringBuilder.append("' AND strftime('%Y',wi_date)='");
        stringBuilder.append(format2);
        stringBuilder.append("'");
        Cursor rawQuery = writableDatabase.rawQuery(stringBuilder.toString(), null);
        if (rawQuery.moveToFirst()) {
            return rawQuery.getInt(rawQuery.getColumnIndex("per"));
        }
        if (!(rawQuery == null || rawQuery.isClosed())) {
            rawQuery.close();
        }
        return -1;
    }

    public int getAveragePer() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        String format = new SimpleDateFormat("yyyy").format(new Date());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT IFNULL((sum(cast(T.totalml as real)/cast(his.tot_ml as real))*100) / count(his.date),0) as per  FROM daily_history his  INNER JOIN ( SELECT wi_date,sum(wi_new_cup_size)totalml FROM water_intake  GROUP BY wi_date  )T on T.wi_date = his.date  where strftime('%Y', his.date) = '");
        stringBuilder.append(format);
        stringBuilder.append("'");
        Cursor rawQuery = writableDatabase.rawQuery(stringBuilder.toString(), null);
        if (rawQuery.moveToFirst()) {
            return rawQuery.getInt(rawQuery.getColumnIndex("per"));
        }
        if (!(rawQuery == null || rawQuery.isClosed())) {
            rawQuery.close();
        }
        return -1;
    }

    public void updateIsCurrent() {
        try {
            getWritableDatabase().execSQL("UPDATE cup_master set  is_current = 0 ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setKEY_iscurrent(int i) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE cup_master set  is_current = 1 where cup_id=");
        stringBuilder.append(i);
        writableDatabase.execSQL(stringBuilder.toString());
    }

    public List<WaterIntake> getAllWaterIntake() {
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT  * FROM water_intake WHERE wi_date='");
        stringBuilder.append(format);
        stringBuilder.append("' ORDER BY ");
        stringBuilder.append(KEY_wi_time);
        stringBuilder.append(" DESC");
        format = stringBuilder.toString();
        List<WaterIntake> linkedList = new LinkedList();
        Cursor rawQuery = getWritableDatabase().rawQuery(format, null);
        if (rawQuery.moveToFirst()) {
            do {
                WaterIntake waterIntake = new WaterIntake();
                waterIntake.setWater_id(rawQuery.getInt(rawQuery.getColumnIndex(KEY_wi_id)));
                waterIntake.setWater_date(rawQuery.getString(rawQuery.getColumnIndex(KEY_wi_date)));
                waterIntake.setWater_time(rawQuery.getString(rawQuery.getColumnIndex(KEY_wi_time)));
                waterIntake.setCup_id(rawQuery.getInt(rawQuery.getColumnIndex(KEY_ID)));
                waterIntake.setCup_size(rawQuery.getInt(rawQuery.getColumnIndex(KEY_wi_ml)));
                waterIntake.setCup_flag(rawQuery.getInt(rawQuery.getColumnIndex(KEY_wi_cup)));
                waterIntake.setNew_cup_size(rawQuery.getInt(rawQuery.getColumnIndex(KEY_wi_new_cup_size)));
                linkedList.add(waterIntake);
            } while (rawQuery.moveToNext());
        }
        if (!(rawQuery == null || rawQuery.isClosed())) {
            rawQuery.close();
        }
        return linkedList;
    }

    public HashMap monthlyReport() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        String format = new SimpleDateFormat("MM").format(new Date());
        String format2 = new SimpleDateFormat("yyyy").format(new Date());
        LinkedList linkedList = new LinkedList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT cast(strftime('%d', ink.wi_date) as integer) date1,round(cast(SUM(ink.wi_new_cup_size) as real) / cast(MAX(his.tot_ml) as real)*100) as per  FROM water_intake ink LEFT JOIN daily_history his on his.date = ink.wi_date  where strftime('%m', wi_date) = '");
        stringBuilder.append(format);
        stringBuilder.append("' AND  strftime('%Y', wi_date) = '");
        stringBuilder.append(format2);
        stringBuilder.append("'  GROUP BY strftime('%d', ink.wi_date)");
        Cursor rawQuery = writableDatabase.rawQuery(stringBuilder.toString(), null);
        HashMap hashMap = new HashMap();
        if (rawQuery.moveToFirst()) {
            do {
                hashMap.put(Integer.valueOf(rawQuery.getInt(rawQuery.getColumnIndex("date1"))), Integer.valueOf(rawQuery.getInt(1)));
            } while (rawQuery.moveToNext());
        }
        if (!(rawQuery == null || rawQuery.isClosed())) {
            rawQuery.close();
        }
        return hashMap;
    }

    public HashMap yealyReport() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        String format = new SimpleDateFormat("yyyy").format(new Date());
        LinkedList linkedList = new LinkedList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT strftime('%m', ink.wi_date) month,round(cast(SUM(ink.wi_new_cup_size) as real) / cast((SELECT cast(SUM(his.tot_ml) as real) as per  FROM daily_history his where strftime('%Y', his.date) = '");
        stringBuilder.append(format);
        stringBuilder.append("') as real)*100) as per   FROM water_intake ink  LEFT JOIN daily_history his on his.date = ink.wi_date  where strftime('%Y', wi_date) = '");
        stringBuilder.append(format);
        stringBuilder.append("'  GROUP BY strftime('%m', ink.wi_date)");
        Cursor rawQuery = writableDatabase.rawQuery(stringBuilder.toString(), null);
        HashMap hashMap = new HashMap();
        if (rawQuery.moveToFirst()) {
            do {
                hashMap.put(Integer.valueOf(rawQuery.getInt(0)), Integer.valueOf(rawQuery.getInt(1)));
            } while (rawQuery.moveToNext());
        }
        if (!(rawQuery == null || rawQuery.isClosed())) {
            rawQuery.close();
        }
        return hashMap;
    }

    public void deleteWaterIntake(int i, Context context) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM water_intake WHERE wi_id=");
        stringBuilder.append(i);
        writableDatabase.execSQL(stringBuilder.toString());
        setverable(context);
    }
}
