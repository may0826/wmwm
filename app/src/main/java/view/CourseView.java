package view;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.boxuegu.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import adapter.AdBannerAdapter;
import adapter.CourseAdapter;
import adapter.ExercisesAdapter;
import bean.CourseBean;
import bean.ExercisesBean;
import fragment.AdBannerFragment;
import utils.getXmlUtils;

/**
 * Created by 狒狒 on 2020/7/13.
 */
public class CourseView {

    private ListView lvList;
    CourseAdapter adapter;
    FragmentActivity mContent;
    private LayoutInflater mInflater;
    private View mCurrentView;//当前视图
    private List<List<CourseBean>> cbl;
    ViewPager adPager;
    View adBannerLay;
    AdBannerAdapter ada;
   public  static final int MSG_AD_SLID=002;
    ViewPagerIndicator vpi;
    List<CourseBean> cadl;
    MHandler mHandler;




    public CourseView( FragmentActivity mContent) {
    this.mContent=mContent;
        mInflater=LayoutInflater.from(mContent);
    }
    private void createView(){
        mHandler=new MHandler();
        initAdData();
        getCourseData();
        initView();
        new AdAutoSlidThread().start();

    }
    //初始化视图
    private  void initView(){
        mCurrentView =mInflater.inflate(R.layout.main_view_course,null);
        lvList= (ListView) mCurrentView.findViewById(R.id.lv_list);
        adapter=new CourseAdapter(mContent);
        adapter.setData(cbl);
        lvList.setAdapter(adapter);
        adPager= (ViewPager) mCurrentView.findViewById(R.id.vp_advertBanner);
        adPager.setLongClickable(false);
        ada=new AdBannerAdapter(mContent.getSupportFragmentManager(),mHandler);
        adPager.setAdapter(ada);
        adPager.setOnTouchListener(ada);//设置监听
        vpi= (ViewPagerIndicator) mCurrentView.findViewById(R.id.vpi_advert_indicator);//自定义控件的id
        adBannerLay=mCurrentView.findViewById(R.id.rl_adBanner);//设置显示布局
        //页面改变得监听
        adPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (ada.getSize()>0){
                    vpi.setCurrentPosition(position%ada.getSize());
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        resetSize();
        if (cadl!=null){
            if (cadl.size()>0){
                vpi.setmCount(cadl.size());
                vpi.setCurrentPosition(0);
            }
            //设置适配器的数据
            ada.setDatas(cadl);
        }
    }

        //显示视图
        public  View getView() {
            if (mCurrentView == null) {
                //创建视图
                       createView();
            }
            return mCurrentView;
        }
    public void showView(){
        if (mCurrentView==null){
            createView();
        }
        mCurrentView.setVisibility(View.VISIBLE);
    }
    class MHandler extends Handler{
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what){
                case MSG_AD_SLID:
                    if (ada.getCount()>0){
                        //设置当前界面为下一个界面 自己切换
                        adPager.setCurrentItem(adPager.getCurrentItem()+1);
                    }
                    break;
            }
        }
    }
    class AdAutoSlidThread extends Thread{
        //多线程
        @Override
        public void run() {
            super.run();
            while (true){
                try {
                    sleep(3000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (mHandler!=null){
                    mHandler.sendEmptyMessage(MSG_AD_SLID);
                }
            }
        }
    }
    private void resetSize(){
        int sw=getScreenWidth(mContent);
        //设置广告条的高度
        int adLheight=sw/2;
        ViewGroup.LayoutParams adlp=adBannerLay.getLayoutParams();
        adlp.width=sw;
        adlp.height=adLheight;
        adBannerLay.setLayoutParams(adlp);

    }
    private void initAdData(){
        cadl=new ArrayList<CourseBean>();
        for (int i=0;i<3;i++){
            CourseBean bean=new CourseBean();
            bean.id=(i+1);
            switch (i){
                case 0:
                    bean.icon="banner_1";
                    break;
                case 1:
                    bean.icon="banner_2";
                    break;
                case 2:
                    bean.icon="banner_3";
                    break;
            }
            cadl.add(bean);

        }
    }
    public static int getScreenWidth(Activity context){
        DisplayMetrics metrics=new DisplayMetrics();
        Display display=context.getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }

    //解析xml文件
    private void getCourseData(){
        //获取xml信息
        try {
            InputStream is=mContent.getResources().getAssets().open("chaptertitle.xml");
            cbl= getXmlUtils.getCourseInfos(is);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
