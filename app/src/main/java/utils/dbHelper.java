package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 狒狒 on 2020/7/9.
 */
//工具类 数据库的
public class dbHelper extends SQLiteOpenHelper {
    String TB_Name="userinfo";
    String VIDEO_PLAY_LIST="videoplaylist";


    public dbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists "+TB_Name
                +"(uid integer primary key autoincrement,"
                +"userName varchar,"
                +"nickName varchar,"
                +"sex varchar,"
                + "signature varchar)");
        sqLiteDatabase.execSQL("create table if not exists "+VIDEO_PLAY_LIST
                +"(vid integer primary key autoincrement,"
                +"userName varchar,"
                +"chapterId int,"
                +"videoId int,"
                +"videoPath varchar,"
                +"title varchar,"
                +"videoTitle varchar)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists "+TB_Name);
        sqLiteDatabase.execSQL("drop table if exists "+VIDEO_PLAY_LIST);
        onCreate(sqLiteDatabase);
    }
}
