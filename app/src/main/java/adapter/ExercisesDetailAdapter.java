package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.boxuegu.ExercisesDetailActivity;
import com.example.boxuegu.R;

import java.util.ArrayList;
import java.util.List;

import bean.ExercisesBean;
import utils.getXmlUtils;

/**
 * Created by 狒狒 on 2020/7/13.
 */
public class ExercisesDetailAdapter extends BaseAdapter {
    Context mcontext;
    List<ExercisesBean> ebl;//习题bean
    OnSelectListener onSelectListener;
    public ExercisesDetailAdapter(Context context,OnSelectListener onSelectListener){
        this.mcontext=context;
        this.onSelectListener=onSelectListener;

    }
    public  void setData(List<ExercisesBean> ebl){
        this.ebl=ebl;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return ebl==null?0:ebl.size();
    }

    @Override
    public  ExercisesBean getItem(int position) {
        return ebl==null?null:ebl.get(position);
    }

    @Override
    public long getItemId(int position) {
        //对应listviewActivity中的id
        return position;
    }
    ArrayList<String> selectedPositions=new ArrayList<String>();

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {//当前选项的位置，第二个是划出屏幕的view
        final ViewHolder vh;
        if (convertView==null){
            vh=new ViewHolder();
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.exercises_detail_list_item,null);//先找布局文件
            //找布局文件中的控件id
            vh.subject= (TextView) convertView.findViewById(R.id.tv_subject);
            vh.tv_a= (TextView) convertView.findViewById(R.id.tv_a);
           vh.tv_b= (TextView) convertView.findViewById(R.id.tv_b);
           vh.tv_c= (TextView) convertView.findViewById(R.id.tv_c);
           vh.tv_d= (TextView) convertView.findViewById(R.id.tv_d);
            vh.iv_a= (ImageView) convertView.findViewById(R.id.iv_a);
            vh.iv_b= (ImageView) convertView.findViewById(R.id.iv_b);
            vh.iv_c= (ImageView) convertView.findViewById(R.id.iv_c);
            vh.iv_d= (ImageView) convertView.findViewById(R.id.iv_d);

            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }
        final ExercisesBean bean= getItem(position);
        if (bean!=null){
            vh.subject.setText(bean.subject);
            vh.tv_a.setText(bean.a);
            vh.tv_b.setText(bean.b);
            vh.tv_c.setText(bean.c);
            vh.tv_d.setText(bean.d);


        }

        //处理单机事件的监听 错误的话显示正确答案并将选择的错误答案标红
        if (!selectedPositions.contains(""+position)){
            vh.iv_a.setImageResource(R.drawable.exercises_a);
            vh.iv_b.setImageResource(R.drawable.exercises_b);
            vh.iv_c.setImageResource(R.drawable.exercises_c);
            vh.iv_d.setImageResource(R.drawable.exercises_d);
            getXmlUtils.setABCDEnable(true,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
        }else{
            //若选中其中一个 其他不可选
            getXmlUtils.setABCDEnable(false,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
            switch (bean.select){
                case 0:
                    //用户选对
                    if (bean.answer==1){
                        vh.iv_a.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if (bean.answer==2){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }
                    else if (bean.answer==3){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }
                    else if (bean.answer==4){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_right_icon);
                    }
                    break;
                case 1:
                    //选A是错误的
                    vh.iv_a.setImageResource(R.drawable.exercises_error_icon);
                    if (bean.answer==2){
                        vh.iv_b.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if (bean.answer==3){
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }
                    else if (bean.answer==4){
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_right_icon);
                    }
                    break;
                case 2:
                    //选B是错误的
                    vh.iv_b.setImageResource(R.drawable.exercises_error_icon);
                    if (bean.answer==2){
                        vh.iv_a.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if (bean.answer==3){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_c.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }
                    else if (bean.answer==4){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_right_icon);
                    }
                    break;
                case 3:
                    //选C是错误的
                    vh.iv_c.setImageResource(R.drawable.exercises_error_icon);
                    if (bean.answer==2){
                        vh.iv_a.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if (bean.answer==3){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }
                    else if (bean.answer==4){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_d.setImageResource(R.drawable.exercises_right_icon);
                    }
                    break;
                case 4:
                    //选D是错误的
                    vh.iv_c.setImageResource(R.drawable.exercises_error_icon);
                    if (bean.answer==2){
                        vh.iv_a.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                    }else if (bean.answer==3){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                    }
                    else if (bean.answer==4){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_right_icon);
                    }
                    break;
                default:
                    break;

            }
        }
        vh.iv_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPositions.contains(""+position)){
                    selectedPositions.remove(""+position);

                }else {
                    selectedPositions.add(""+position);

                }
                onSelectListener.onSelectA(position,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
            }
        });
        vh.iv_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPositions.contains(""+position)){
                    selectedPositions.remove(""+position);

                }else {
                    selectedPositions.add(""+position);

                }
                onSelectListener.onSelectB(position,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
            }
        });
        vh.iv_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPositions.contains(""+position)){
                    selectedPositions.remove(""+position);

                }else {
                    selectedPositions.add(""+position);

                }
                onSelectListener.onSelectC(position,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
            }
        });
        vh.iv_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPositions.contains(""+position)){
                    selectedPositions.remove(""+position);

                }else {
                    selectedPositions.add(""+position);

                }
                onSelectListener.onSelectD(position,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
            }
        });


        return convertView;
    }
    class ViewHolder{
        public TextView tv_a,tv_b,tv_c,tv_d,subject;
        public ImageView iv_a,iv_b,iv_c,iv_d;

    }
    //选项单击事件
    public interface OnSelectListener{
        void onSelectA(int position,ImageView iv_a,ImageView iv_b,ImageView iv_c,ImageView iv_d);
        void onSelectB(int position,ImageView iv_a,ImageView iv_b,ImageView iv_c,ImageView iv_d);
        void onSelectC(int position,ImageView iv_a,ImageView iv_b,ImageView iv_c,ImageView iv_d);
        void onSelectD(int position,ImageView iv_a,ImageView iv_b,ImageView iv_c,ImageView iv_d);


    }

}
