package com.example.boxuegu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import bean.UserBean;
import utils.DBUtils;
import utils.ReadLoginName;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout titleBar;
    private TextView tvBack;
    private TextView tvMainTitle;
    private RelativeLayout rlHead;
    private ImageView ivHeadIcon;
    private RelativeLayout rlAccount;
    private TextView tvUserNames;
    private RelativeLayout rlNickname;
    private TextView tvNickname;
    private RelativeLayout rlSex;
    private TextView tvSex;
    private RelativeLayout rlSignature;
    private TextView tvSignature;

    String spUserName;

    //设置修改昵称等等常量
    private static final int CHANGE_NICKNAME=1;
    private static final int CHANGE_SINGATURE=2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        spUserName= ReadLoginName.readLoginUserName(this);//通过调用工具类来读取username 此时已经是登陆过的 所以可以直接获取当前登录的用户名
        init();
        initData();
        setListener();
    }
    private void init(){
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        titleBar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.this.finish();
            }
        });
        tvMainTitle = (TextView) findViewById(R.id.tv_main_title);
        tvMainTitle.setText("个 人 资 料");
        rlHead = (RelativeLayout) findViewById(R.id.rl_head);
        ivHeadIcon = (ImageView) findViewById(R.id.iv_head_icon);
        rlAccount = (RelativeLayout) findViewById(R.id.rl_account);
        tvUserNames = (TextView) findViewById(R.id.tv_user_names);
        rlNickname = (RelativeLayout) findViewById(R.id.rl_nickname);
        tvNickname = (TextView) findViewById(R.id.tv_nickname);
        rlSex = (RelativeLayout) findViewById(R.id.rl_sex);
        tvSex = (TextView) findViewById(R.id.tv_sex);
        rlSignature = (RelativeLayout) findViewById(R.id.rl_signature);
        tvSignature = (TextView) findViewById(R.id.tv_signature);
    }
    private void setListener(){
        tvBack.setOnClickListener(this);
        rlNickname.setOnClickListener(this);
        rlSex.setOnClickListener(this);
        rlSignature.setOnClickListener(this);
    }
    private void initData(){
        //为textView赋值 通过调用数据库中存储的数据
        UserBean bean=null;
        bean= DBUtils.getInstance(this).getUserInfo(spUserName);
        //若数据库有则赋值，若没有则为默认值
        if (bean==null){
            bean=new UserBean();
            //默认值
            bean.userName=spUserName;
            bean.nickName="未设置";
            bean.signature="未设置";
            bean.sex="男";
            DBUtils.getInstance(this).saveUserInfo(bean);
        }
        //将数据库中的值显示在页面上
        tvUserNames.setText(bean.userName);
        tvNickname.setText(bean.nickName);
        tvSex.setText(bean.sex);
        tvSignature.setText(bean.signature);

    }
   //单机事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                this.finish();
                break;
            case R.id.rl_nickname:
                String name=tvNickname.getText().toString().trim();
                Bundle bnames=new Bundle();
                bnames.putString("content",name);
                bnames.putString("title","昵称");
                bnames.putInt("flag",1);
                enterActivityForResult(ChangeUserInfoActivity.class,CHANGE_NICKNAME,bnames);
                break;
            case R.id.rl_sex:
               String sex=tvSex.getText().toString().trim();
                sexDialog(sex);
                break;
            case R.id.rl_signature:
                String signature=tvSignature.getText().toString().trim();
                Bundle bsignature=new Bundle();
                bsignature.putString("content",signature);
                bsignature.putString("title","签名");
                bsignature.putInt("flag",2);
                enterActivityForResult(ChangeUserInfoActivity.class,CHANGE_SINGATURE,bsignature);
                break;
            default:
                break;

        }
    }
    //通过一个弹框来让客户选择性别
    private void sexDialog(String sex){
        int sexFlag=0;
        if ("男".equals(sex)){
            sexFlag=0;

        }else if ("女".equals(sex)) {
            sexFlag = 1;
        }
        final String items[]={"男","女"};
        AlertDialog.Builder ab=new AlertDialog.Builder(this);
        ab.setTitle("请选择性别");
        //对话框的内容
        ab.setSingleChoiceItems(items, sexFlag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialog消除
                dialog.dismiss();
                Toast.makeText(UserInfoActivity.this,"选择性别为"+items[which],Toast.LENGTH_SHORT).show();
                //传值
                tvSex.setText(items[which]);
                //数据库的对应值也要改变
                DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("sex",items[which],spUserName);//第一个列名，第二个参数的值，第三个where的值

            }
        });
        ab.create().show();
    }
    //进行昵称的回调 将数据的值跳转传送到目标Activity
    public void enterActivityForResult(Class<?> to,int requestCode,Bundle b){
        Intent i=new Intent(this,to);
        i.putExtras(b);
        startActivityForResult(i,requestCode);
    }
    String new_info;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHANGE_NICKNAME:
                if (data != null) {
                    new_info = data.getStringExtra("nickName");
                    if (TextUtils.isEmpty(new_info)) {
                        return;
                    } else {
                        tvNickname.setText(new_info);
                        DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("nickName", new_info, spUserName);
                    }

                }
            case CHANGE_SINGATURE:
                if (data != null) {
                    new_info = data.getStringExtra("signature");
                    if (TextUtils.isEmpty(new_info)) {
                        return;
                    } else {
                        tvSignature.setText(new_info);
                        DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("signature", new_info, spUserName);
                    }
                }
        }
    }
}
