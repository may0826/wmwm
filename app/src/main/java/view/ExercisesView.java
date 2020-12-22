package view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.boxuegu.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ExercisesAdapter;
import bean.ExercisesBean;

/**
 * Created by 狒狒 on 2020/7/13.
 */
public class ExercisesView {

    private ListView lvList;
    Activity mContext;
    private ExercisesAdapter adapter;
    private LayoutInflater mInflater;
    private View mCurrentView;//当前视图
    private List<ExercisesBean> ebl;

    public ExercisesView(Activity context) {
    this.mContext=context;
        mInflater=LayoutInflater.from(mContext);
    }
    private void createView(){
        initView();
    }

    private void initView() {
        mCurrentView =mInflater.inflate(R.layout.main_view_exercises,null);
        lvList= (ListView) mCurrentView.findViewById(R.id.lv_list);
        adapter=new ExercisesAdapter(mContext);
        initData();
        adapter.setData(ebl);
        lvList.setAdapter(adapter);
    }
    private void initData(){
        ebl=new ArrayList<ExercisesBean>();
        for (int i=0;i<=10;i++){
            ExercisesBean bean=new ExercisesBean();
            bean.id=(i+1);
            switch (i){
                case 0:
                    bean.title="第1章 Android基础入门";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 1:
                    bean.title="第2章 AndroidUI开发";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_2);
                    break;
                case 2:
                    bean.title="第3章 Activity";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_3);
                    break;
                case 3:
                    bean.title="第4章 数据存储";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_4);
                    break;
                case 4:
                    bean.title="第5章 Sqlite数据库存储";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 5:
                    bean.title="第6章 ListView";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_2);
                    break;
                case 6:
                    bean.title="第7章 广播";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_3);
                    break;
                case 7:
                    bean.title="第8章 服务";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_4);
                    break;
                case 8:
                    bean.title="第9章 网络编程";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 9:
                    bean.title="第10章 你猜";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_2);
                    break;
                default:
                    break;

            }
            ebl.add(bean);//将ebl添加进去
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
}
