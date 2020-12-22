package adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.boxuegu.R;

import java.util.List;

import bean.VideoBean;

/**
 * Created by 狒狒 on 2020/7/14.
 */
public class VideoListAdapter extends BaseAdapter {
    Context mContext;
    List<VideoBean> vbl;
    int selectedPosition=-1;

    //监听
  OnSelectListener onSelectListener;

    public VideoListAdapter(Context mContext, OnSelectListener onSelectListener) {
        this.mContext = mContext;
        this.onSelectListener = onSelectListener;
    }

    public void setSelectedPosition(int position){
        selectedPosition=position;

    }
    public void setData(List<VideoBean> vbl){
        this.vbl=vbl;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return vbl==null?0:vbl.size();
    }

    @Override
    public VideoBean getItem(int position) {
        return vbl==null?null:vbl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
//视图
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView==null){
            vh=new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.video_list_item,null);//先找布局文件
            //找布局文件中的控件id
            vh.tv_title=(TextView)convertView.findViewById(R.id.tv_video_title);
            vh.iv_icon= (ImageView) convertView.findViewById(R.id.iv_left_icon);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }
        final VideoBean bean=getItem(position);
        vh.iv_icon.setImageResource(R.drawable.course_bar_icon);
        vh.tv_title.setTextColor(Color.parseColor("#333333"));
        if (bean!=null){
            vh.tv_title.setText(bean.videoTitle);
            if (selectedPosition==position){
                vh.iv_icon.setImageResource(R.drawable.course_bar_icon);
                vh.tv_title.setTextColor(Color.parseColor("#009958"));
            }else {
                vh.iv_icon.setImageResource(R.drawable.course_bar_icon);
                vh.tv_title.setTextColor(Color.parseColor("#333333"));
            }
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //视频播放
                if (bean==null){
                    return;
                }
                onSelectListener.onSelect(position,vh.iv_icon);
            }
        });
        return convertView;
    }
    public interface OnSelectListener{
        void onSelect(int position, ImageView imageView);
    }
    class ViewHolder{
        public TextView tv_title;
        public ImageView iv_icon;
    }
}
