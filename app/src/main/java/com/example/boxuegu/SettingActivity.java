package com.example.boxuegu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    private RelativeLayout titleBar;
    private TextView tvBack;
    private TextView tvMainTitle;
    private RelativeLayout rlModifyPwd;
    private RelativeLayout r1SecuritySetting;
    private RelativeLayout r1ExitLogin;

    public static SettingActivity instance=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        //将当前界面赋值给instance
        instance=this;
    }
    private  void init(){
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        titleBar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tvBack = (TextView) findViewById(R.id.tv_back);
        //返回键的代码 返回上一页面
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });
        tvMainTitle = (TextView) findViewById(R.id.tv_main_title);
        tvMainTitle.setText("设   置");
        rlModifyPwd = (RelativeLayout) findViewById(R.id.rl_modify_pwd);
        rlModifyPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(SettingActivity.this,"假装跳转到修改密码界面",Toast.LENGTH_LONG).show();
                Intent intent =new Intent(SettingActivity.this,ModifyPwdActivity.class);
                startActivity(intent);
            }
        });
        r1SecuritySetting = (RelativeLayout) findViewById(R.id.r1_security_setting);
        r1SecuritySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(SettingActivity.this,"假装跳转到设置密保界面",Toast.LENGTH_LONG).show();
                //无需传值 直接跳转到设置密保界面
                Intent intent=new Intent(SettingActivity.this,FindPwdActivity.class);
                intent.putExtra("from","security");
                startActivity(intent);

            }
        });
        r1ExitLogin = (RelativeLayout) findViewById(R.id.r1_exit_login);
        r1ExitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this,"退出登录成功",Toast.LENGTH_LONG).show();
                clearLoginStatus();
                Intent data=new Intent();
                data.putExtra("islogin",false);
                setResult(RESULT_OK,data);
                SettingActivity.this.finish();
            }
        });

    }
    //清除登录状态
    private void clearLoginStatus(){
        SharedPreferences sp=getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("islogin",false);
        editor.putString("loginUsername","");
        editor.commit();

    }
}
