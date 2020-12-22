package adapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import bean.CourseBean;
import fragment.AdBannerFragment;
import view.CourseView;

/**
 * Created by 狒狒 on 2020/7/14.
 */
public class AdBannerAdapter extends FragmentStatePagerAdapter implements View.OnTouchListener {
    //课程实体类
    Handler mHandler;
    List<CourseBean> courseBeen;
    public AdBannerAdapter(FragmentManager fm) {
        super(fm);
        courseBeen=new ArrayList<CourseBean>();
    }
    public AdBannerAdapter(FragmentManager fm,Handler handler) {
        super(fm);
        courseBeen=new ArrayList<CourseBean>();
        mHandler=handler;
    }
    public void setDatas(List<CourseBean> courseBeen){
        this.courseBeen=courseBeen;
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {
        Bundle args=new Bundle();
        if (courseBeen.size()>0){
            args.putString("ad",courseBeen.get(position%courseBeen.size()).icon);
        }
        return AdBannerFragment.newInstance(args);
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
    public  int getItemPosition(Object object){
        return POSITION_NONE;
    }
    //长度
    public  int getSize(){
        return courseBeen==null?0:courseBeen.size();
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mHandler.removeMessages(CourseView.MSG_AD_SLID);
        return false;
    }
}
