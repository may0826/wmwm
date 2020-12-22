package com.example.boxuegu;

import android.Manifest;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.View.OnClickListener;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bean.UserInfoBean;
import utils.DBUtils;
import utils.MD5Utils;

public class RegisterActivity extends AppCompatActivity {
    private RelativeLayout titleBar;
    private TextView tvBack;
    private TextView tvMainTitle;
    private ImageView imageView;
    private EditText etUserName;
    private EditText etPwd;
    private EditText etPwdAgain;
    private EditText etLocation;
    private Button btnRegister;
    private CheckBox checkbox_agree;
    private TextView tvShort;
    private TextView tvMiddle;
    private TextView tvStrong;



    String regexZ = "\\d*";
    String regexS = "[a-zA-Z]+";
    String regexT = "\\W+$";
    String regexZT = "\\D*";
    String regexST = "[\\d\\W]*";
    String regexZS = "\\w*";
    String regexZST = "[\\w\\W]*";





    LocationManager lm;
    private static String[] PERMISSION_ALL={
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static int REQUEST_PERMISSION_CODE=1;


    String username,pwd1,pwd2,loc,status;
    UserInfoBean userInfoBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        getGPS();


    }
    // 校验密码不少于6位
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }


    private void init() {
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_CHECKIN_PROPERTIES)!=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,PERMISSION_ALL,REQUEST_PERMISSION_CODE);
            }
        }
        tvShort = (TextView) findViewById(R.id.tv_short);
        tvMiddle = (TextView) findViewById(R.id.tv_middle);
        tvStrong = (TextView) findViewById(R.id.tv_strong);
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
        tvMainTitle = (TextView) findViewById(R.id.tv_main_title);
        tvMainTitle.setText("注   册");
        imageView = (ImageView) findViewById(R.id.imageView);
        etUserName = (EditText) findViewById(R.id.et_user_name);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String inputstr=etPwd.getText().toString().trim();
                if (!hasFocus){
                    if (isPasswordValid(inputstr)){
                        etPwd.setError(null);
                    }else {
                        etPwd.setError("密码不能小于6位");
                    }
                    if (inputstr.length()<=0){
                        tvShort.setBackgroundColor(Color.rgb(205,205,205));
                        tvMiddle.setBackgroundColor(Color.rgb(205,205,205));
                        tvStrong.setBackgroundColor(Color.rgb(205,205,205));
                    }
                    else if (inputstr.matches(regexS)){
                        tvShort.setBackgroundColor(Color.rgb(255,129,128));
                        tvMiddle.setBackgroundColor(Color.rgb(205,205,205));
                        tvStrong.setBackgroundColor(Color.rgb(205,205,205));
                    }else if (inputstr.matches(regexZ)){
                        tvShort.setBackgroundColor(Color.rgb(255,129,128));
                        tvMiddle.setBackgroundColor(Color.rgb(205,205,205));
                        tvStrong.setBackgroundColor(Color.rgb(205,205,205));
                    }else if (inputstr.matches(regexT)){
                        tvShort.setBackgroundColor(Color.rgb(255,129,128));
                        tvMiddle.setBackgroundColor(Color.rgb(205,205,205));
                        tvStrong.setBackgroundColor(Color.rgb(205,205,205));
                    }else if (inputstr.matches(regexZS)){
                        tvShort.setBackgroundColor(Color.rgb(205,205,205));
                        tvMiddle.setBackgroundColor(Color.rgb(255,129,128));
                        tvStrong.setBackgroundColor(Color.rgb(205,205,205));
                    }else if (inputstr.matches(regexZT)){
                        tvShort.setBackgroundColor(Color.rgb(205,205,205));
                        tvMiddle.setBackgroundColor(Color.rgb(255,129,128));
                        tvStrong.setBackgroundColor(Color.rgb(205,205,205));
                    }else if (inputstr.matches(regexST)){
                        tvShort.setBackgroundColor(Color.rgb(205,205,205));
                        tvMiddle.setBackgroundColor(Color.rgb(255,129,128));
                        tvStrong.setBackgroundColor(Color.rgb(205,205,205));
                    }else if (inputstr.matches(regexZST)){
                        tvShort.setBackgroundColor(Color.rgb(205,205,205));
                        tvMiddle.setBackgroundColor(Color.rgb(205,205,205));
                        tvStrong.setBackgroundColor(Color.rgb(255,129,128));
                    }

                }
            }
        });
        etPwdAgain = (EditText) findViewById(R.id.et_pwd_again);
        etLocation = (EditText) findViewById(R.id.et_location);
        checkbox_agree= (CheckBox) findViewById(R.id.checkbox_agree);
        checkbox_agree.setOnClickListener(listener);
        checkbox_agree.setChecked(false);


        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=etUserName.getText().toString().trim();//.trim()忽略大小写；
                pwd1=etPwd.getText().toString().trim();
                pwd2=etPwdAgain.getText().toString().trim();
                loc=etLocation.getText().toString().trim();
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_LONG).show();
                    return;
                }else if(TextUtils.isEmpty(pwd2)){
                    Toast.makeText(RegisterActivity.this,"请再次输入密码",Toast.LENGTH_LONG).show();
                    return;
                }else if(TextUtils.isEmpty(pwd1)){
                    Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_LONG).show();
                    return;
                }else if(TextUtils.isEmpty(loc)){
                    Toast.makeText(RegisterActivity.this,"请输入所在地",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(!pwd1.equals(pwd2)){
                    Toast.makeText(RegisterActivity.this,"两次输入密码不一致",Toast.LENGTH_LONG).show();

                }
                else if(isExistUserName(username)){
                    //用户名不存在
                    Toast.makeText(RegisterActivity.this,"用户名已存在",Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    //注册成功
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                    saveRegisterInfo(username,pwd1);
                    Intent data=new Intent();
                    data.putExtra("username",username);
                    setResult(RESULT_OK,data);
                    RegisterActivity.this.finish();

                }

            }
        });

    }


    private OnClickListener listener=new OnClickListener(){
        @Override
        public void onClick(View v) {
            if (checkbox_agree.isChecked()){
                btnRegister.setEnabled(true);


            }

        }


    };
    //保存数据 将密码转化成MD5
   private void saveRegisterInfo(String username,String pwd){

        String md5Pwd=MD5Utils.md5(pwd);


        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
//        editor.putString("username",username);
//        editor.putString("pwd",md5Pwd);
//        editor.putString("location",address);
        editor.putString(username,md5Pwd);
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

    public void getGPS() {
        //定位服务 利用GPS 大写的都是常量例如LOCATION_SERVICE
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!isGPSAble(lm)) {
            Toast.makeText(RegisterActivity.this, "请先打开GPS功能", Toast.LENGTH_LONG).show();
            openGPS();
        }
        //从GPS获取最新位置信息
        //判断权限问题
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location lc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateShow(lc);

      }
    //判断GPS服务是否开启
    private boolean isGPSAble(LocationManager lm){
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)?true:false;
    }
    private void openGPS(){
        Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
       startActivityForResult(intent,0);
    }
    //获取位置信息
    private void updateShow(Location location){
        if(location!=null){
            StringBuilder sb=new StringBuilder();
            List<Address> addressList=null;
            Geocoder gc=new Geocoder(RegisterActivity.this);
            try {
                addressList=gc.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addressList!=null&&addressList.size()>0){
                Address ad=addressList.get(0);
                sb.append(ad.getAdminArea()+" "+ad.getLocality());
            }
            etLocation.setText(sb.toString());

        }else{
            Toast.makeText(RegisterActivity.this, "定位失败,请手动输入", Toast.LENGTH_LONG).show();
        }
    }
}
