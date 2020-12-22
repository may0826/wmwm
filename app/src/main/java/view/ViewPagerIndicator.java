package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.boxuegu.R;

/**
 * Created by 狒狒 on 2020/7/14.
 */
//自定义的一个控件
public class ViewPagerIndicator extends LinearLayout {
    int mCount;//小点个数
    int mIndex;
    Context context;
    public ViewPagerIndicator(Context context){
        this(context,null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        //当前界面是某一界面
        this.context=context;
        setGravity(Gravity.CENTER);
    }
    public void setCurrentPosition(int currentIndex){
        mIndex=currentIndex;
        removeAllViews();//移除所有视图
        int pex=5;
        for (int i=0;i<mCount;i++){
            ImageView imageView=new ImageView(context);
            if (mIndex==i){
                imageView.setImageResource(R.drawable.indicator_on);
            }else {
                imageView.setImageResource(R.drawable.indicator_off);

            }
            imageView.setPadding(pex,0,pex,0);
            addView(imageView);
        }
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }
}
