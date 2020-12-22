package com.example.boxuegu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import adapter.VideoListAdapter;
import bean.VideoBean;
import utils.DBUtils;
import utils.ReadLoginName;

public class VideoListActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvIntro;
    private TextView tvVideo;
    private ListView lvVideoList;
    private ScrollView svChapterIntro;
    private TextView tvChapterIntro;

  VideoListAdapter adapter;
    DBUtils db;
    List<VideoBean> videoList;
    int chapterId;
    String intro;

    private RelativeLayout titleBar;
    private TextView tvBack;
    private TextView tvMainTitle;
    private TextView tvSave;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        chapterId=getIntent().getIntExtra("id",0);
        intro=getIntent().getStringExtra("intro");
        db=DBUtils.getInstance(VideoListActivity.this);
        initData();
        init();

    }
    private void init(){
        tvIntro = (TextView) findViewById(R.id.tv_intro);
        tvVideo = (TextView) findViewById(R.id.tv_video);
        lvVideoList = (ListView) findViewById(R.id.lv_video_list);
        svChapterIntro = (ScrollView) findViewById(R.id.sv_chapter_intro);
        tvChapterIntro = (TextView) findViewById(R.id.tv_chapter_intro);
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        titleBar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoListActivity.this.finish();
            }
        });
        tvMainTitle = (TextView) findViewById(R.id.tv_main_title);
        tvMainTitle.setText("课 程 详 情");
        tvSave = (TextView) findViewById(R.id.tv_save);

        adapter=new VideoListAdapter(this, new VideoListAdapter.OnSelectListener() {
            @Override
            public void onSelect(int position, ImageView imageView) {
                adapter.setSelectedPosition(position);

                VideoBean bean=videoList.get(position);
                String videoPath=bean.videoPath;
                adapter.notifyDataSetChanged();
                if (TextUtils.isEmpty(videoPath)){
                    Toast.makeText(VideoListActivity.this,"暂无视频，无法播放",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    //如果登录 则跳转
                    if (readLoginStatus()){
                        String userName= ReadLoginName.readLoginUserName(VideoListActivity.this);
                        db.saveVideoPlayList(videoList.get(position),userName);
                    }
                    Intent intent=new Intent(VideoListActivity.this,VideoPlayActivity.class);
                    intent.putExtra("videoPath",videoPath);
                    intent.putExtra("position",position);
                    startActivityForResult(intent,1);

                }

            }
        });
        lvVideoList.setAdapter(adapter);
        tvIntro.setOnClickListener(this);
        tvVideo.setOnClickListener(this);
        adapter.setData(videoList);
        tvChapterIntro.setText(intro);
        //默认情况
        tvIntro.setBackgroundColor(Color.parseColor("#30B4FF"));
        tvVideo.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tvIntro.setTextColor(Color.parseColor("#FFFFFF"));
        tvVideo.setTextColor(Color.parseColor("#000000"));

    }
    private boolean readLoginStatus(){
        SharedPreferences sp=getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin=sp.getBoolean("islogin",false);
        return isLogin;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_intro:
                lvVideoList.setVisibility(View.GONE);
                svChapterIntro.setVisibility(View.VISIBLE);
                tvIntro.setBackgroundColor(Color.parseColor("#30B4FF"));
                tvVideo.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tvIntro.setTextColor(Color.parseColor("#FFFFFF"));
                tvVideo.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_video:
                lvVideoList.setVisibility(View.VISIBLE);
                svChapterIntro.setVisibility(View.GONE);
                tvIntro.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tvVideo.setBackgroundColor(Color.parseColor("#30B4FF"));
                tvIntro.setTextColor(Color.parseColor("#000000"));
                tvVideo.setTextColor(Color.parseColor("#FFFFFF"));
                //Toast.makeText(VideoListActivity.this,"1111shi"+chapterId,Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
    //Json解析
    private  void initData(){
        JSONArray jsonArray;
        InputStream is=null;
        try{
            is=getResources().getAssets().open("data.json");
            jsonArray=new JSONArray(read(is));//读取流文件并转换为字节
            videoList=new ArrayList<VideoBean>();
            for (int i=0;i<jsonArray.length();i++){
                VideoBean bean=new VideoBean();
                JSONObject jsonObject=jsonArray.getJSONObject(i);//获取json第一个对象
                if (jsonObject.getInt("chapterId")==chapterId){
                    bean.chapterId=jsonObject.getInt("chapterId");
                    bean.videoId=Integer.parseInt(jsonObject.getString("videoId"));
                    bean.title=jsonObject.getString("title");
                    bean.videoTitle=jsonObject.getString("videoTitle");
                    bean.videoPath=jsonObject.getString("videoPath");
                    videoList.add(bean);
                }
                bean=null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //读取流文件并转换为字节
    private  String read(InputStream in){
        BufferedReader reader=null;
        StringBuilder sb=null;
        String line=null;
        sb=new StringBuilder();
        reader=new BufferedReader(new InputStreamReader(in));
        try {
            while ((line=reader.readLine())!=null){
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }finally {
            try{
                if (in!=null){
                    in.close();
                }
                if (reader!=null){
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
   return sb.toString();

    }
}
