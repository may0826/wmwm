package com.example.boxuegu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import utils.MD5Utils;
import utils.ReadLoginName;

public class ModifyPwdActivity extends AppCompatActivity {
    private RelativeLayout titleBar;
    private TextView tvBack;
    private TextView tvMainTitle;
    private EditText etOldPwd;
    private EditText etNewPwd;
    private EditText etNewPwdAgain;
    private Button btnSave;
    String oldPwd,newPwd,newPwd2;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        //通过工具类 获取用户名
        username= ReadLoginName.readLoginUserName(this);


    }
    private void init(){
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPwdActivity.this.finish();
            }
        });
        tvMainTitle = (TextView) findViewById(R.id.tv_main_title);
        tvMainTitle.setText("修 改 密 码");
        etOldPwd = (EditText) findViewById(R.id.et_old_pwd);
        etNewPwd = (EditText) findViewById(R.id.et_new_pwd);
        etNewPwdAgain = (EditText) findViewById(R.id.et_new_pwd_again);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPwd=etOldPwd.getText().toString().trim();
                newPwd=etNewPwd.getText().toString().trim();
                newPwd2=etNewPwdAgain.getText().toString().trim();
                if (TextUtils.isEmpty(oldPwd)){
                    Toast.makeText(ModifyPwdActivity.this,"请输入原始密码",Toast.LENGTH_LONG).show();
                    return;
                }else if (TextUtils.isEmpty(newPwd)){
                    Toast.makeText(ModifyPwdActivity.this,"请输入新密码",Toast.LENGTH_LONG).show();
                    return;
                }else if (TextUtils.isEmpty(newPwd2)){
                    Toast.makeText(ModifyPwdActivity.this,"请再次输入新密码",Toast.LENGTH_LONG).show();
                    return;
                }else if (!MD5Utils.md5(oldPwd).equals(readPwd())){
                    Toast.makeText(ModifyPwdActivity.this,"输入旧密码与登陆密码不一致！",Toast.LENGTH_LONG).show();
                    return;
                }else if (MD5Utils.md5(newPwd).equals(readPwd())){
                    Toast.makeText(ModifyPwdActivity.this,"输入新密码与登陆密码不能相同！",Toast.LENGTH_LONG).show();
                    return;
                }
                else if (!newPwd.equals(newPwd2)){
                    Toast.makeText(ModifyPwdActivity.this,"两次输入的新密码不一致！",Toast.LENGTH_LONG).show();
                    return;
                }else {
                    Toast.makeText(ModifyPwdActivity.this,"新密码设置成功！",Toast.LENGTH_LONG).show();
                    modifyPwd(newPwd);
                    Intent intent=new Intent(ModifyPwdActivity.this,LoginActivity.class);
                    startActivity(intent);
                    //通过设置一个全局变量讲settingActivity赋值给instance，解决修改密码后登录之后无法关闭设置界面
                    SettingActivity.instance.finish();
                    ModifyPwdActivity.this.finish();

                }


            }
        });
    }

    //读取原始密码
    private String readPwd( ){
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        String spPwd=sp.getString(username,"");
        return spPwd;
    }
    //将新密码转化成MD5
    private void modifyPwd(String newpwd){
        String md5Pwd=MD5Utils.md5(newpwd);
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
//        editor.putString("username",username);
//        editor.putString("pwd",md5Pwd);
//        editor.putString("location",address);
        editor.putString(username,md5Pwd);
        editor.commit();

    }

}
