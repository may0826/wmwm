package com.example.boxuegu;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class VideoPlayActivity extends AppCompatActivity {
      VideoView videoView;
    MediaController controller;
    String videoPath;
    int position;
    private DanmakuView danmakuView;
    private LinearLayout llSend;
    private EditText etText;
    private Button btnSend;
    DanmakuContext danmakuContext;
    boolean showDanmaKu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        //设置全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //视频路径
        videoPath=getIntent().getStringExtra("videoPath");
        position=getIntent().getIntExtra("position",0);

        init();

        initDanmaKu();
    }
    private void init(){
        videoView= (VideoView) findViewById(R.id.videoView);
        controller=new MediaController(this);
        videoView.setMediaController(controller);
        danmakuView = (DanmakuView) findViewById(R.id.danmaku);
        llSend = (LinearLayout) findViewById(R.id.ll_send);
        etText = (EditText) findViewById(R.id.et_text);
        btnSend = (Button) findViewById(R.id.btn_send);
        play();


    }
    private void play(){
        if (TextUtils.isEmpty(videoPath)){
            Toast.makeText(this,"暂无对应视频",Toast.LENGTH_SHORT).show();
            return;
        }
        String url="android.resource://"+getPackageName()+"/"+R.raw.video1;
        videoView.setVideoPath(url);
        videoView.start();
    }
    //创建弹幕解析器来解析弹幕
    private BaseDanmakuParser parser=new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();

        }
    };
    //初始化弹幕
    private void initDanmaKu(){
        //回调函数
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                //按钮的单击事件等等都要有
                //显示弹幕
                showDanmaKu=true;
                danmakuView.start();
                generateDanmaKu();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        danmakuContext=DanmakuContext.create();
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.prepare(parser,danmakuContext);
        //当点击框是才会出现
        danmakuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llSend.getVisibility()==View.GONE){
                    llSend.setVisibility(View.VISIBLE);
                }else {
                    llSend.setVisibility(View.GONE);
                }
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=etText.getText().toString();
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(VideoPlayActivity.this,"请输入内容之后发送",Toast.LENGTH_SHORT).show();
                }else {
                    //发送弹幕
                    addDanmaKu(content,true);
                    etText.setText("");
                }
            }
        });
    }
    //添加弹幕
    private void addDanmaKu(String content,boolean border){
        BaseDanmaku danmaku=danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);//设置弹幕从右向左滚动
        danmaku.text=content;//设置弹幕库的内容
        danmaku.setTime(danmakuView.getCurrentTime());//弹幕库视图的当前时间
        danmaku.padding=5;
        danmaku.textSize=20+(int)(Math.random()*20);
        Random random=new Random();
        int r=30+random.nextInt(200);
        int g=30+random.nextInt(200);
        int b=30+random.nextInt(200);
        danmaku.textColor= Color.rgb(r,g,b);
        if (border==true){
            danmaku.borderColor= Color.rgb(r,g,b);

        }
        danmakuView.addDanmaku(danmaku);//增加弹幕
    }
    private void generateDanmaKu(){
        new Thread(new Runnable() {
            @Override
            public void run() {while (showDanmaKu){
                String[] word={"下次一定","减肥成功！我要减肥","我受不了自己这么肥"};
                addDanmaKu(word[(int)(Math.random()*word.length)],false);
                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            }
        }).start();
    }

}
