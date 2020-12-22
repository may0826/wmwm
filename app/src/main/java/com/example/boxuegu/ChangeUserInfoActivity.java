package com.example.boxuegu;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import utils.ToastUtils;

public class ChangeUserInfoActivity extends AppCompatActivity {
    private int flag;//1表示修改昵称，2表示修改签名
    private String title,content;

    private RelativeLayout titleBar;
    private TextView tvBack;
    private TextView tvMainTitle;
    private TextView tvSave;
    private EditText etContent;
    private ImageView ivDelete;
    private ToastUtils toastUtils;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }
    private void init(){
        title=getIntent().getStringExtra("title");//从上一个界面传过来的值 即userinfoactivity
        content=getIntent().getStringExtra("content");//从上一个界面传过来的值 即userinfoactivity
        flag=getIntent().getIntExtra("flag",flag);
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        titleBar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeUserInfoActivity.this.finish();//当前界面的finish
            }
        });
        tvMainTitle = (TextView) findViewById(R.id.tv_main_title);
        tvMainTitle.setText(title);
        tvSave = (TextView) findViewById(R.id.tv_save);
        tvSave.setVisibility(View.VISIBLE);
        etContent = (EditText) findViewById(R.id.et_content);
        //光标放置最后
        if (!TextUtils.isEmpty(content)){
            etContent.setText(content);
            etContent.setSelection(content.length());
        }

        ivDelete = (ImageView) findViewById(R.id.iv_delete);
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setText("");
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent();
                String et_Content=etContent.getText().toString().trim();
                switch (flag){
                    case 1:
                        if (!TextUtils.isEmpty(et_Content)){
                            data.putExtra("nickName",et_Content);
                            setResult(RESULT_OK,data);
                           Toast.makeText(ChangeUserInfoActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                            ChangeUserInfoActivity.this.finish();
                        }else {
                            Toast.makeText(ChangeUserInfoActivity.this,"昵称不能为空！",Toast.LENGTH_SHORT).show();

                        }
                        break;
                    case 2:
                        if (!TextUtils.isEmpty(et_Content)){
                            data.putExtra("signature",et_Content);
                            setResult(RESULT_OK,data);
                            Toast.makeText(ChangeUserInfoActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                            ChangeUserInfoActivity.this.finish();
                        }else {
                            Toast.makeText(ChangeUserInfoActivity.this,"签名不能为空！",Toast.LENGTH_SHORT).show();

                        }
                        break;
                }
            }
        });
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable=etContent.getText();
                //若是文本框无内容则叉叉不显示
                int len=editable.length();
                if (len>0){
                    ivDelete.setVisibility(View.VISIBLE);
                }else {
                    ivDelete.setVisibility(View.GONE);
                }
                switch (flag){
                    case 1:
                        if (len>8){
                            int selEndIndex= Selection.getSelectionEnd(etContent.getText());
                            toastUtils.showMsg(ChangeUserInfoActivity.this,"昵称最多只能输入8位");
                            String str=etContent.getText().toString();
                            String newstr=str.substring(0,8);
                            etContent.setText(newstr);
                            //为防止光标移至前面
                            editable=etContent.getText();
                            int newLen=editable.length();
                            if (selEndIndex>newLen){
                                selEndIndex=editable.length();
                            }
                            Selection.setSelection(editable,selEndIndex);
                        }
                        break;
                    case 2:
                        if (len>16){
                            //Toast.makeText(ChangeUserInfoActivity.this,"签名最多只能输入16位",Toast.LENGTH_SHORT).show();
                            toastUtils.showMsg(ChangeUserInfoActivity.this,"签名最多只能输入16位");
                            int selEndIndex= Selection.getSelectionEnd(etContent.getText());
                            String str=etContent.getText().toString();
                            String newstr=str.substring(0,16);
                            etContent.setText(newstr);
                            //为防止光标移至前面
                            editable=etContent.getText();
                            int newLen=editable.length();
                            if (selEndIndex>newLen){
                                selEndIndex=editable.length();
                            }
                            Selection.setSelection(editable,selEndIndex);
                        }
                        break;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }



}
