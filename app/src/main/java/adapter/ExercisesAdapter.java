package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.boxuegu.ExercisesDetailActivity;
import com.example.boxuegu.R;

import java.util.List;

import bean.ExercisesBean;

/**
 * Created by 狒狒 on 2020/7/13.
 */
public class ExercisesAdapter extends BaseAdapter {
    Context mcontext;
    List<ExercisesBean> ebl;//习题bean

    public ExercisesAdapter(Context context){
        this.mcontext=context;

    }
    @Override
    //类似于获取多少章
    public int getCount() {
        return ebl==null?0:ebl.size();//若ebl为空就返回0否则返回ebl的长度
    }

    @Override
    public ExercisesBean getItem(int position) {
        return ebl==null?null:ebl.get(position);//若为空则返回null否则返回等同于判断语句
    }

    @Override
    public long getItemId(int position) {
        //对应listviewActivity中的id
        return position;
    }
    public  void setData(List<ExercisesBean> ebl){
        this.ebl=ebl;
        notifyDataSetChanged();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//当前选项的位置，第二个是划出屏幕的view
        final ViewHolder vh;
        if (convertView==null){
            vh=new ViewHolder();
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.exercises_list_item,null);//先找布局文件
            //找布局文件中的控件id
            vh.title=(TextView)convertView.findViewById(R.id.tv_tittle);
            vh.content=(TextView)convertView.findViewById(R.id.tv_content);
            vh.order=(TextView)convertView.findViewById(R.id.tv_order);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }
        final ExercisesBean bean=getItem(position);
        if (bean!=null){
            vh.order.setText(position+1+"");//防止报类型错误
            vh.content.setText(bean.content);
            vh.title.setText(bean.title);
            vh.order.setBackgroundResource(bean.background);

        }
        //设置监听
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean==null){
                    return;
                }
                Intent intent=new Intent(mcontext, ExercisesDetailActivity.class);
                intent.putExtra("id",bean.id);
                intent.putExtra("title",bean.title);
                mcontext.startActivity(intent);

            }
        });
        return convertView;
    }
    class ViewHolder{
        public TextView title;
        public TextView content;
        public TextView order;
    }
}
