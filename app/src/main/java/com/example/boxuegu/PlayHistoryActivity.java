package com.example.boxuegu;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.PlayHistoryAdapter;
import bean.VideoBean;
import utils.DBUtils;
import utils.ReadLoginName;

public class PlayHistoryActivity extends AppCompatActivity {
    private RelativeLayout titleBar;
    private TextView tvBack;
    private TextView tvMainTitle;
    private TextView tvSave;
    private ListView lvList;
    private TextView tvNone;

    List<VideoBean> videoList;
    PlayHistoryAdapter adapter;
    DBUtils db;

    private Button btnClearhistory;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_history);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        db=DBUtils.getInstance(this);
        videoList=new ArrayList<VideoBean>();
        videoList=db.getVideoHistory(ReadLoginName.readLoginUserName(this));
        init();

    }
    private void init(){
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        titleBar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayHistoryActivity.this.finish();
            }
        });
        tvMainTitle = (TextView) findViewById(R.id.tv_main_title);
        tvMainTitle.setText("播 放 记 录");
        tvSave = (TextView) findViewById(R.id.tv_save);
        btnClearhistory = (Button) findViewById(R.id.btn_clearhistory);

        lvList = (ListView) findViewById(R.id.lv_list);
        tvNone = (TextView) findViewById(R.id.tv_none);
        //适配器
        if (videoList.size()>0){
            tvNone.setVisibility(View.VISIBLE);
            btnClearhistory.setVisibility(View.VISIBLE);
        }
        adapter=new PlayHistoryAdapter(this);
        adapter.setData(videoList);
        lvList.setAdapter(adapter);
        btnClearhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (delHistory()){
                  Toast.makeText(PlayHistoryActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                  videoList.clear();
                  adapter.setData(null);
                  btnClearhistory.setVisibility(View.GONE);
              }

                }


        });
    }
    public boolean delHistory(){
        boolean delSucess=false;
       delSucess= DBUtils.getInstance(this).delvideohistory();
        return delSucess;
    }


}
