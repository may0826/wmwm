package com.example.boxuegu;

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

public class FindPwdActivity extends AppCompatActivity {
    private RelativeLayout titleBar;
    private TextView tvBack;
    private TextView tvMainTitle;
    private TextView tvUsername;
    private EditText etUsername;
    private EditText etValidateName;
    private TextView tvResetPwd;
    private Button btnValidate;

    String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        from=getIntent().getStringExtra("from");
        init();
    }
    private void init(){
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindPwdActivity.this.finish();
            }
        });
        tvMainTitle = (TextView) findViewById(R.id.tv_main_title);
        tvUsername = (TextView) findViewById(R.id.tv_username);

        etUsername = (EditText) findViewById(R.id.et_username);
        etValidateName = (EditText) findViewById(R.id.et_validate_name);
        tvResetPwd = (TextView) findViewById(R.id.tv_reset_pwd);
        btnValidate = (Button) findViewById(R.id.btn_validate);
        //验证 首先判断是从找回密码过来还是设置密保过来的
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过这个来进行接收姓名
                String validateName=etValidateName.getText().toString().trim();
                //判断是设置密保界面还是找回密码界面
                if ("security".equals(from)){
                    if (TextUtils.isEmpty(validateName)){
                        Toast.makeText(FindPwdActivity.this,"请输入需要验证的姓名！",Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        Toast.makeText(FindPwdActivity.this,"密保设置成功！",Toast.LENGTH_SHORT).show();
                        saveSecurity(validateName);
                        FindPwdActivity.this.finish();
                    }
                }else{
                    String username=etUsername.getText().toString().trim();
                    String sp_security=readSecurity(username);
                    if (TextUtils.isEmpty(username)){
                        Toast.makeText(FindPwdActivity.this,"请输入您的用户名！",Toast.LENGTH_SHORT).show();
                        return;
                    }else if (!isExistUserName(username)){
                        Toast.makeText(FindPwdActivity.this,"您输入的用户名不存在！",Toast.LENGTH_SHORT).show();
                        return;
                    }else if (TextUtils.isEmpty(validateName)){
                        Toast.makeText(FindPwdActivity.this,"请输入你需要验证的姓名！",Toast.LENGTH_SHORT).show();
                        return;
                    }else if(!validateName.equals(sp_security)){
                        Toast.makeText(FindPwdActivity.this,"输入密保不正确！",Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        tvResetPwd.setVisibility(View.VISIBLE);
                        tvResetPwd.setText("恢复为初始密码：123456");
                        savePwd(username);
                    }

                }

            }
        });
        //判断为空的时候 可以换下变量
        if ("security".equals(from)){
            tvMainTitle.setText("设 置 密 保");
        }else{
            tvMainTitle.setText("找 回 密 码");
            tvUsername.setVisibility(View.VISIBLE);
            etUsername.setVisibility(View.VISIBLE);
        }
    }
    //修改为初始密码
    private void savePwd(String username){
        String md5pwd= MD5Utils.md5("123456");
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(username,md5pwd);
        editor.commit();
    }
    //将密保内容放在缓存中
    private String readSecurity(String username){
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        String security=sp.getString(username+"_security","");
        return security;

    }

    //将密保内容放在缓存中
    private void saveSecurity(String validateName){
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        //将密保跟与之对应的用户名保存在缓存中
        editor.putString(ReadLoginName.readLoginUserName(this)+"_security",validateName);
        editor.commit();

    }
    //我们通过sharedpreferences中输入的用户名，判断用户名是否已经存在
    private  boolean isExistUserName(String username){
        boolean has_userName=false;
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        String spPwd=sp.getString(username,"");
        if (!TextUtils.isEmpty(spPwd)){
            has_userName=true;

        }
        return has_userName;
    }
}
