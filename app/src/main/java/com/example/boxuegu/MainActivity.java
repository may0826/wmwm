package com.example.boxuegu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import view.CourseView;
import view.ExercisesView;
import view.MyinfoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout titleBar;
    private TextView tvBack;
    private TextView tvMainTitle;
    private FrameLayout mainBody;
    private LinearLayout mainBottomBar;
    private RelativeLayout bottomBarCourseBtn;
    private TextView bottomBarTextCourse;
    private ImageView bottomBarImageCourse;
    private RelativeLayout bottomBarExercisesBtn;
    private TextView bottomBarTextExercises;
    private ImageView bottomBarImageExercises;
    private RelativeLayout bottomBarMyinfoBtn;
    private TextView bottomBarTextMyinfo;
    private ImageView bottomBarImageMyinfo;
    private ExercisesView mExercisesView;
    private CourseView mCourseView;

    private MyinfoView myinfoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏
        init();
        setListener();
        //确保图标被选择  0708进入默认界面 清空效果 载入 课程 被选中
        clearBottomImageStatus();
        setSelectedStatus(0);
        //默认第一个视图
        createView(0);
    }
    //创建视图
    private void createView(int viewIndex){
        switch (viewIndex){
            case 0:
                //课程界面
                if (mCourseView==null){
                    mCourseView=new CourseView(this);
                    mainBody.addView(mCourseView.getView());
                }else {
                    mCourseView.getView();
                }
                mCourseView.showView();

                break;
            case 1:
                //习题界面
                if (mExercisesView==null){
                    mExercisesView=new ExercisesView(this);
                    mainBody.addView(mExercisesView.getView());

                }else {
                    mExercisesView.getView();
                }
                mExercisesView.showView();
                break;
            case 2:
                //我的界面
                if (myinfoView==null){
                    myinfoView=new MyinfoView(this);
                    mainBody.addView(myinfoView.getView());
                }else {
                    myinfoView.getView();
                }
                myinfoView.showView();

                break;
        }
    }
    //移除View 隐藏界面
    private void removeAllView(){
        for (int i=0;i<mainBody.getChildCount();i++){
            mainBody.getChildAt(i).setVisibility(View.GONE);
        }
    }
    //清除图标的状态
    private void clearBottomImageStatus(){
        bottomBarTextCourse.setTextColor(Color.parseColor("#666666"));
        bottomBarTextExercises.setTextColor(Color.parseColor("#666666"));
        bottomBarTextMyinfo.setTextColor(Color.parseColor("#666666"));

        bottomBarImageExercises.setImageResource(R.drawable.main_exercises_icon);
        bottomBarImageMyinfo.setImageResource(R.drawable.main_my_icon);
        bottomBarImageCourse.setImageResource(R.drawable.main_course_icon);
        for (int i=0;i<mainBottomBar.getChildCount();i++){
            mainBottomBar.getChildAt(i).setSelected(false);
        }
    }
    //设置图标选中之后的状态
    private void setSelectedStatus(int index){
        switch (index){
            case 0:
                bottomBarCourseBtn.setSelected(true);
                //设置选中图标image来源
                bottomBarImageCourse.setImageResource(R.drawable.main_course_icon_selected);
                //设置选中之后的颜色
                bottomBarTextCourse.setTextColor(Color.parseColor("#0097F7"));
                titleBar.setVisibility(View.VISIBLE);
                tvMainTitle.setText("博学谷课程");
                break;
            case 1:
                bottomBarExercisesBtn.setSelected(true);
                //设置选中图标image来源
                bottomBarImageExercises.setImageResource(R.drawable.main_exercises_icon_selected);
                //设置选中之后的颜色
                bottomBarTextExercises.setTextColor(Color.parseColor("#0097F7"));
                titleBar.setVisibility(View.VISIBLE);
                tvMainTitle.setText("博学谷习题");
                break;
            case 2:
                bottomBarMyinfoBtn.setSelected(true);
                //设置选中图标image来源
                bottomBarImageMyinfo.setImageResource(R.drawable.main_my_icon_selected);
                //设置选中之后的颜色
                bottomBarTextMyinfo.setTextColor(Color.parseColor("#0097F7"));
                titleBar.setVisibility(View.GONE);
                break;
        }
    }
    //以下是三个按钮的监听
    @Override
    //通过判断view的id来进行后续的处理 判断是否点击 图标的状态改变与否
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bottom_bar_course_btn:
               // Toast.makeText(MainActivity.this,"1",Toast.LENGTH_SHORT).show();
                clearBottomImageStatus();
                removeAllView();
                createView(0);
                setSelectedStatus(0);
                break;
            case R.id.bottom_bar_exercises_btn:
                //Toast.makeText(MainActivity.this,"2",Toast.LENGTH_SHORT).show();
                clearBottomImageStatus();
                removeAllView();
                createView(1);
                setSelectedStatus(1);
                break;
            case R.id.bottom_bar_myinfo_btn:
                //Toast.makeText(MainActivity.this,"3",Toast.LENGTH_SHORT).show();
                clearBottomImageStatus();
                removeAllView();
                createView(2);
                setSelectedStatus(2);
                break;
            default:
                break;
        }
    }

    //以下是三个按钮的监听 通过一个for语句根据底部标题栏的个数来进行事件的触发
    private void setListener(){
        for (int i=0;i<mainBottomBar.getChildCount();i++){
            mainBottomBar.getChildAt(i).setOnClickListener(this);
        }
    }
    public void init(){
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        titleBar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvBack.setVisibility(View.GONE);//隐藏界面
        tvMainTitle = (TextView) findViewById(R.id.tv_main_title);
        tvMainTitle.setText("博学谷课程");
        mainBody = (FrameLayout) findViewById(R.id.main_body);
        mainBottomBar = (LinearLayout) findViewById(R.id.main_bottom_bar);
        bottomBarCourseBtn = (RelativeLayout) findViewById(R.id.bottom_bar_course_btn);
        bottomBarTextCourse = (TextView) findViewById(R.id.bottom_bar_text_course);
        bottomBarImageCourse = (ImageView) findViewById(R.id.bottom_bar_image_course);
        bottomBarExercisesBtn = (RelativeLayout) findViewById(R.id.bottom_bar_exercises_btn);
        bottomBarTextExercises = (TextView) findViewById(R.id.bottom_bar_text_exercises);
        bottomBarImageExercises = (ImageView) findViewById(R.id.bottom_bar_image_exercises);
        bottomBarMyinfoBtn = (RelativeLayout) findViewById(R.id.bottom_bar_myinfo_btn);
        bottomBarTextMyinfo = (TextView) findViewById(R.id.bottom_bar_text_myinfo);
        bottomBarImageMyinfo = (ImageView) findViewById(R.id.bottom_bar_image_myinfo);
    }
    //当按到返回键 判断是否进行退出 返回至桌面
    boolean isQuit=false;
    private void exit(){
        if (!isQuit){
            Toast.makeText(MainActivity.this,"再次点击将退出",Toast.LENGTH_LONG).show();
            isQuit=true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        isQuit=false;
                    }
                }
            }).start();
        }else {
            MainActivity.this.finish();
            if (readLoginStatus()){
                clearLoginStatus();
            }
            System.exit(0);
        }
    }

    public boolean onKeyDown(int KeyCode, KeyEvent event){
        if (KeyCode==KeyEvent.KEYCODE_BACK){
            //KEYCODE_BACK代表的是系统自带的返回按钮
            exit();
        }
        return true;
    }
    //获取登陆状态
    private boolean readLoginStatus(){
        SharedPreferences sp=getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin=sp.getBoolean("islogin",false);
        return isLogin;
    }
    //清除登录状态
    private void clearLoginStatus(){
        SharedPreferences sp=getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("islogin",false);
        editor.putString("loginUsername","");
        editor.commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            boolean isLogin=data.getBooleanExtra("islogin",false);
            if (isLogin){
                clearBottomImageStatus();
                removeAllView();
                createView(0);
                setSelectedStatus(0);
            }
            if (myinfoView!=null){
                myinfoView.setLoginParams(isLogin);
            }
        }
    }
}
