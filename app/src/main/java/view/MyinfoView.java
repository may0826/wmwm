package view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boxuegu.AboutActivity;
import com.example.boxuegu.LoginActivity;
import com.example.boxuegu.PlayHistoryActivity;
import com.example.boxuegu.R;
import com.example.boxuegu.SettingActivity;
import com.example.boxuegu.UserInfoActivity;

import utils.ReadLoginName;

/**
 * Created by 狒狒 on 2020/7/8.
 */
public class MyinfoView {
    private LinearLayout llHead;
    private ImageView ivHeadIcon;
    private TextView tvUserName;
    private RelativeLayout r1CourseHistory;
    private ImageView ivCourseHistory;
    private RelativeLayout r1Setting;
    private ImageView ivUserinfoIcon;
    private RelativeLayout r1About;
    private ImageView ivAboutIcon;
    private Activity mContext;
    private LayoutInflater mInflater;//适配器
    private View mCurrentView;//当前视图

    public MyinfoView(Activity context) {
            mContext=context;
        mInflater=LayoutInflater.from(mContext);
    }
    private void createView(){
        initView();
    }
    //初始化视图
    private void initView(){
        mCurrentView=mInflater.inflate(R.layout.main_view_myinfo,null);
        llHead = (LinearLayout)mCurrentView. findViewById(R.id.ll_head);
        ivHeadIcon = (ImageView) mCurrentView. findViewById(R.id.iv_head_icon);
        tvUserName = (TextView)mCurrentView.  findViewById(R.id.tv_user_name);
        r1CourseHistory = (RelativeLayout)mCurrentView.  findViewById(R.id.r1_course_history);
        ivCourseHistory = (ImageView)mCurrentView.  findViewById(R.id.iv_course_history);
        r1Setting = (RelativeLayout) mCurrentView. findViewById(R.id.r1_setting);
        ivUserinfoIcon = (ImageView)mCurrentView.  findViewById(R.id.iv_userinfo_icon);
        r1About = (RelativeLayout) mCurrentView. findViewById(R.id.r1_about);
        ivAboutIcon = (ImageView) mCurrentView. findViewById(R.id.iv_about_icon);
        mCurrentView.setVisibility(View.VISIBLE);
        setLoginParams(readLoginStatus());
        llHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (readLoginStatus()){
                    //Toast.makeText(mContext,"已登录",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(mContext, UserInfoActivity.class);
                    mContext.startActivity(intent);
                }else{
                    Intent intent=new Intent(mContext, LoginActivity.class);
                    mContext.startActivityForResult(intent,1);
                }
            }
        });
        r1CourseHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (readLoginStatus()){
                   // Toast.makeText(mContext,"假装跳转到播放记录页面",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(mContext, PlayHistoryActivity.class);
                    mContext.startActivity(intent);
                }else {
                    Toast.makeText(mContext,"您还没有登录，请进行登录",Toast.LENGTH_LONG).show();
                }
            }
        });
        r1Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (readLoginStatus()){
                    Intent intent=new Intent(mContext, SettingActivity.class);
                    mContext.startActivityForResult(intent,1);
                }else {
                    Toast.makeText(mContext,"您还没有登录，请进行登录",Toast.LENGTH_LONG).show();
                }
            }
        });
        r1About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (readLoginStatus()){
                    Intent intent=new Intent(mContext, AboutActivity.class);
                    mContext.startActivityForResult(intent,1);
                }else {
                    Toast.makeText(mContext,"您还没有登录，请进行登录",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public void setLoginParams(boolean isLogin){
        if (isLogin){
            tvUserName.setText(ReadLoginName.readLoginUserName(mContext));

        }else {
            tvUserName.setText("点击登录");
        }
    }
    //读取登陆的状态
    private boolean readLoginStatus(){
        SharedPreferences sp=mContext.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin=sp.getBoolean("islogin",false);
        return isLogin;
    }

        //创建视图
    public View getView() {
       if (mCurrentView==null){
           createView();
       }
        return mCurrentView;
    }

    public void showView() {
        if (mCurrentView==null){
            createView();

        }
        //视图显示
        mCurrentView.setVisibility(View.VISIBLE);
    }
}
