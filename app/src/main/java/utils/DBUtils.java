package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bean.UserBean;
import bean.UserInfoBean;
import bean.VideoBean;

/**
 * Created by 狒狒 on 2020/7/9.
 * 数据库增改查的工具类
 */
public class DBUtils {

    dbHelper dbHelper;
    SQLiteDatabase db;
    String DB_Name="mydb";
    static   DBUtils instance=null;

    //构造方法
     public DBUtils(Context context){
         dbHelper=new dbHelper(context,DB_Name,null,1);
         db=dbHelper.getWritableDatabase();//打开数据库

     }
    //创建一个案例
    public static DBUtils getInstance(Context context){
        if (instance==null){
            instance=new DBUtils(context);

        }
        return instance;

    }


    //保存用户信息
    public void saveUserInfo(UserBean bean){
        //创建记录  将传过来的参数放入其中
        ContentValues values=new ContentValues();
        values.put("userName",bean.userName);
        values.put("nickName",bean.nickName);
        values.put("sex",bean.sex);
        values.put("signature",bean.signature);
        db.insert(dbHelper.TB_Name,null,values);//插入数据库中

    }
    //查询信息
    public UserBean getUserInfo(String userName){
        String sql="select * from "+dbHelper.TB_Name+" where userName=?";
        Cursor cursor=db.rawQuery(sql,new String[]{userName});
        UserBean bean=null;
        while (cursor.moveToNext()){
            bean=new UserBean();//实例化bean
            //获取列的数据
            bean.userName=cursor.getString(cursor.getColumnIndex("userName"));
            bean.nickName=cursor.getString(cursor.getColumnIndex("nickName"));
            bean.sex=cursor.getString(cursor.getColumnIndex("sex"));
            bean.signature=cursor.getString(cursor.getColumnIndex("signature"));
        }
        cursor.close();
        return bean;

    }

    //修改
    public void updateUserInfo(String key,String value,String userName){
        ContentValues values=new ContentValues();
        values.put(key,value);
        db.update(dbHelper.TB_Name,values,"userName=?",new String[]{userName});//最后一个是当前登录的用户名
    }
    //保存视频播放记录
    public void saveVideoPlayList(VideoBean bean,String username){
        if (hasVideoPlay(bean.chapterId,bean.videoId,username)){
            boolean isDelete = delVideoPlay(bean.chapterId,bean.videoId,username);
            if (!isDelete){
                return;
            }
        }
        ContentValues cv = new ContentValues();
        cv.put("userName",username);
        cv.put("chapterId",bean.chapterId);
        cv.put("videoId",bean.videoId);
        cv.put("videoPath",bean.videoPath);
        cv.put("title",bean.title);
        cv.put("videoTitle",bean.videoTitle);
        db.insert(dbHelper.VIDEO_PLAY_LIST,null,cv);
    }

    //判断视频之前是否看过
    public boolean hasVideoPlay(int chapterId,int videoId,String username){
        boolean hasVideo = false;
        String sql = "select * from "+dbHelper.VIDEO_PLAY_LIST+" where chapterId=? and videoId=? and userName=?";
        Cursor cursor = db.rawQuery(sql,new String[]{chapterId+"",videoId+"",username});
        if (cursor.moveToFirst()){
            hasVideo=true;
        }
        cursor.close();
        return hasVideo;
    }
    //删除记录
    public boolean delvideohistory(){
        boolean delSuccess = false;
        int row = db.delete(dbHelper.VIDEO_PLAY_LIST,null,null);
        if (row>0){
            delSuccess = true;
        }
        return delSuccess;
    }


    //删除视频记录
    public boolean delVideoPlay(int chapterId,int videoId,String username){
        boolean delSuccess = false;
        int row = db.delete(dbHelper.VIDEO_PLAY_LIST,"chapterId=? and videoId=? and userName=?",new String[]{chapterId+"",videoId+"",username});
        if (row>0){
            delSuccess = true;
        }
        return delSuccess;
    }
    //获取视频记录
    public List<VideoBean> getVideoHistory(String userName){
        String sql="select * from "+dbHelper.VIDEO_PLAY_LIST+" where userName=?";
        Cursor cursor=db.rawQuery(sql,new String[]{userName});
        List<VideoBean> vbl=new ArrayList<VideoBean>();
        VideoBean bean=null;
        while (cursor.moveToNext()){
            bean=new VideoBean();
            bean.chapterId=cursor.getInt(cursor.getColumnIndex("chapterId"));
            bean.videoId=cursor.getInt(cursor.getColumnIndex("videoId"));
            bean.videoPath=cursor.getString(cursor.getColumnIndex("videoPath"));
            bean.title=cursor.getString(cursor.getColumnIndex("title"));
            bean.videoTitle=cursor.getString(cursor.getColumnIndex("videoTitle"));
            vbl.add(bean);
            bean=null;//每次赋值之后将bean设置为空


        }
        cursor.close();
        return vbl;
    }


}
