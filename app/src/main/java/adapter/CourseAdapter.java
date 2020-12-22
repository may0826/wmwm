package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boxuegu.ExercisesDetailActivity;
import com.example.boxuegu.R;
import com.example.boxuegu.VideoListActivity;

import java.util.List;

import bean.CourseBean;
import bean.ExercisesBean;

/**
 * Created by 狒狒 on 2020/7/13.
 */
public class CourseAdapter extends BaseAdapter {
    Context mcontext;
    List<List<CourseBean>> cbl;//习题bean

    public CourseAdapter(Context context){
        this.mcontext=context;

    }

    public  void setData( List<List<CourseBean>> cbl){
        this.cbl=cbl;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return cbl==null?0:cbl.size();
    }

    @Override
    public List<CourseBean> getItem(int position) {
        return cbl==null?null:cbl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//当前选项的位置，第二个是划出屏幕的view
        final ViewHolder vh;
        if (convertView==null){
            vh=new ViewHolder();
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.course_list_item,null);//先找布局文件
            //找布局文件中的控件id
            vh.tv_left_img_title=(TextView)convertView.findViewById(R.id.tv_left_img_title);
            vh.tv_left_title=(TextView)convertView.findViewById(R.id.tv_left_title);
            vh.tv_right_img_title=(TextView)convertView.findViewById(R.id.tv_right_img_title);
            vh.tv_right_title=(TextView)convertView.findViewById(R.id.tv_right_title);
            vh.iv_left_img= (ImageView) convertView.findViewById(R.id.iv_left_img);
            vh.iv_right_img= (ImageView) convertView.findViewById(R.id.iv_right_img);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }
        final List<CourseBean> list=getItem(position);
        if (list!=null){
            for (int j=0;j<list.size();j++){
                final CourseBean bean=list.get(j);
                switch (j){
                    case 0:
                        vh.tv_left_img_title.setText(bean.imgTitle);
                        vh.tv_left_title.setText(bean.title);
                        //图片设置
                        setLeftImg(bean.id,vh.iv_left_img);
                        vh.iv_left_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               // Toast.makeText(mcontext,"你点的是左边",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(mcontext,VideoListActivity.class);
                                intent.putExtra("id",bean.id);
                                intent.putExtra("intro",bean.intro);
                                mcontext.startActivity(intent);
                            }
                        });
                        break;
                    case 1:
                        vh.tv_right_img_title.setText(bean.imgTitle);
                        vh.tv_right_title.setText(bean.title);
                        //图片设置
                        setRightImg(bean.id,vh.iv_right_img);
                        vh.iv_right_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                              //  Toast.makeText(mcontext,"你点的是右边",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(mcontext,VideoListActivity.class);
                                intent.putExtra("id",bean.id);
                                intent.putExtra("intro",bean.intro);
                                mcontext.startActivity(intent);
                            }
                        });
                        break;
                    default:
                        break;

                }

            }
        }

        return convertView;
    }
    private void setLeftImg(int id,ImageView iv_left_img){
        switch (id){
            case 1:
                //设置左边的图片源
                iv_left_img.setImageResource(R.drawable.chapter_1_icon);
                break;
            case 3:
                //设置左边的图片源
                iv_left_img.setImageResource(R.drawable.chapter_3_icon);
                break;
            case 5:
                //设置左边的图片源
                iv_left_img.setImageResource(R.drawable.chapter_5_icon);
                break;
            case 7:
                //设置左边的图片源
                iv_left_img.setImageResource(R.drawable.chapter_7_icon);
                break;
            case 9:
                //设置左边的图片源
                iv_left_img.setImageResource(R.drawable.chapter_9_icon);
                break;
        }

    }
    private void setRightImg(int id,ImageView iv_right_img){
        switch (id){
            case 2:
                //设置右边的图片源
                iv_right_img.setImageResource(R.drawable.chapter_2_icon);
                break;
            case 4:
                //设置右边的图片源
                iv_right_img.setImageResource(R.drawable.chapter_4_icon);
                break;
            case 6:
                //设置右边的图片源
                iv_right_img.setImageResource(R.drawable.chapter_6_icon);
                break;
            case 8:
                //设置右边的图片源
                iv_right_img.setImageResource(R.drawable.chapter_8_icon);
                break;
            case 10:
                //设置右边的图片源
                iv_right_img.setImageResource(R.drawable.chapter_10_icon);
                break;
        }

    }
    class ViewHolder{
        public TextView tv_left_img_title,tv_left_title,tv_right_img_title,tv_right_title;
        public ImageView iv_left_img,iv_right_img;


    }
}
