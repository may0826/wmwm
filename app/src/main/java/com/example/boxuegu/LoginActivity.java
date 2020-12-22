package com.example.boxuegu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import utils.CountDownButton;
import utils.MD5Utils;
import utils.sendCode;

public class LoginActivity extends AppCompatActivity {
    private RelativeLayout titleBar;
    private TextView tvBack;
    private TextView tvMainTitle;
    private ImageView imageView;
    private EditText etUserName;
    private EditText etPwd;
    private EditText etPhone;
    private CountDownButton sendcode;
    private EditText etCode;
    private Button btnLogin;
    private TextView tvRegister;
    private TextView tvFindpwd;
    private ImageView ivMimakejian;
    private  boolean isshow=false;



    String username,pwd,code,phone,spPsw;
    public static char[] checkCode={'0','1','2','3','4','5','6','7','8','9'};
    static String word="";
    String inputCode="";
    String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();


    }
    private void init(){
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();//返回上一级界面
            }
        });
        tvMainTitle = (TextView) findViewById(R.id.tv_main_title);
        tvMainTitle.setText("登   录");
        imageView = (ImageView) findViewById(R.id.imageView);
        etUserName = (EditText) findViewById(R.id.et_user_name);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etPhone = (EditText) findViewById(R.id.et_phone);
        sendcode = (CountDownButton) findViewById(R.id.sendcode);
        ivMimakejian = (ImageView) findViewById(R.id.iv_mimakejian);
        ivMimakejian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isshow){
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                    ivMimakejian.setImageResource(R.drawable.mimakejian);
                    etPwd.setInputType(0x90);
                    isshow=false;
                    etPwd.setSelection(etPwd.length());
                }else {
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivMimakejian.setImageResource(R.drawable.mimabukejian);
                    etPwd.setInputType(0x81);
                    etPwd.setSelection(etPwd.length());
                    isshow=true;
                }
            }
        });

        sendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone=etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(LoginActivity.this,"请输入手机号",Toast.LENGTH_LONG).show();
                    return;
                }else if (!(phone.matches(regex))){
                    Toast.makeText(LoginActivity.this,"输入手机号有误",Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    sendcode.startCount();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i=0;i<4;i++){
                                word+=checkCode[(int) (checkCode.length*Math.random())];
                            }
                            inputCode=word;
//                  Looper.prepare();
//                  Toast.makeText(MainActivity.this,"验证码是："+word,Toast.LENGTH_LONG).show();
//                  Looper.loop();
//                  word="";
                            sendCode sc=new sendCode();
                            String json=sc.bowlingJson(word,etPhone.getText().toString().trim());
                            Log.e("验证码",word);
                            Looper.prepare();
                            Toast.makeText(LoginActivity.this,word,Toast.LENGTH_SHORT).show();
                            Looper.loop();
                            try {
                                String response=sc.post("https://open.ucpaas.com/ol/sms/sendsms",json);
                                System.out.print(response);
                                word="";
                                Looper.prepare();
                                Toast.makeText(LoginActivity.this,"验证码已发送",Toast.LENGTH_LONG).show();
                                Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                                Looper.loop();
                            } catch (IOException e) {
                                Looper.prepare();
                                Toast.makeText(LoginActivity.this,"验证码发送失败",Toast.LENGTH_LONG).show();
                                Looper.loop();
                                e.printStackTrace();

                            }
                        }
                    }).start();
                }

            }
        });


        etCode = (EditText) findViewById(R.id.et_code);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=etUserName.getText().toString().trim();
                pwd=etPwd.getText().toString().trim();
                code=etCode.getText().toString().trim();
                phone=etPhone.getText().toString().trim();
                String md5Pwd= MD5Utils.md5(pwd);//对密码进行加密
                spPsw=readPwd(username);
                //判定
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(LoginActivity.this,"请输入用户名！",Toast.LENGTH_LONG).show();
                    return;
                }
                else if (TextUtils.isEmpty(pwd)){
                    Toast.makeText(LoginActivity.this,"请输入密码！",Toast.LENGTH_LONG).show();
                    return;
                }
                else if (TextUtils.isEmpty(phone)){
                    Toast.makeText(LoginActivity.this,"请输入手机号！",Toast.LENGTH_LONG).show();
                    return;
                }else if(!(phone.length()<20)){
                    Toast.makeText(LoginActivity.this,"手机号输入不合法",Toast.LENGTH_LONG).show();
                    return;
                }
                else if (!(inputCode.equals(code))){
                    Toast.makeText(LoginActivity.this,"验证码输入有误！",Toast.LENGTH_LONG).show();
                    return;
                }  else if (TextUtils.isEmpty(code)){
                    Toast.makeText(LoginActivity.this,"请输入验证码！",Toast.LENGTH_LONG).show();
                    return;
                }else if (md5Pwd.equals(spPsw)){
                    Toast.makeText(LoginActivity.this,"登陆成功！",Toast.LENGTH_LONG).show();
                    saveLoginStatus(true,username);
                    Intent data=new Intent();
                    data.putExtra("islogin",true);
                    setResult(RESULT_OK,data);
                    LoginActivity.this.finish();
                    return;
                }else if(!TextUtils.isEmpty("spPwd")&&!md5Pwd.equals(spPsw)){
                    Toast.makeText(LoginActivity.this,"密码和用户名不匹配！",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LoginActivity.this,"用户不存在！",Toast.LENGTH_LONG).show();
                }



            }
        });
        tvRegister = (TextView) findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
        tvFindpwd = (TextView) findViewById(R.id.tv_findpwd);
        tvFindpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,FindPwdActivity.class);
                startActivity(intent);
            }
        });
    }

    //根据用户名读取密码
    private String readPwd(String username){
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        return sp.getString(username,"");//此处得username是变量
    }
    //保存登陆状态
    private void saveLoginStatus(boolean status,String username){
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("islogin",status);
        editor.putString("loginUsername",username);
        editor.commit();
    }
    //记住用户名
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            String username=data.getStringExtra("username");
            if (!TextUtils.isEmpty(username)){
                etUserName.setText(username);
                etUserName.setSelection(username.length());
            }
        }
    }

}
