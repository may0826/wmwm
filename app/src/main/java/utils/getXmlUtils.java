package utils;

import android.util.Xml;
import android.widget.ImageView;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import bean.CourseBean;
import bean.ExercisesBean;

/**
 * Created by 狒狒 on 2020/7/13.
 */
public class getXmlUtils {
    //xml文件的解析
    public static List<ExercisesBean> getExercisesInfos(InputStream is) throws Exception {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is,"utf-8");
        List<ExercisesBean> exercisesInfos = null;
        ExercisesBean exercisesInfo = null;
        int type = parser.getEventType();
        //判断标签元素
        while(type!=XmlPullParser.END_DOCUMENT){
            switch (type){
                //第一个标签
                case XmlPullParser.START_TAG:
                    if("infos".equals(parser.getName())){
                        exercisesInfos = new ArrayList<ExercisesBean>();
                    }else if("exercises".equals(parser.getName())){
                        exercisesInfo = new ExercisesBean();
                        String ids = parser.getAttributeValue(0);
                        exercisesInfo.subjectId = Integer.parseInt(ids);
                    }else if("subject".equals(parser.getName())){
                        String subject = parser.nextText();
                        exercisesInfo.subject = subject;
                    }else if("a".equals(parser.getName())){
                        String a = parser.nextText();
                        exercisesInfo.a = a;
                    }else if("b".equals(parser.getName())){
                        String b = parser.nextText();
                        exercisesInfo.b = b;
                    }else if("c".equals(parser.getName())){
                        String c = parser.nextText();
                        exercisesInfo.c = c;
                    }else if("d".equals(parser.getName())){
                        String d = parser.nextText();
                        exercisesInfo.d = d;
                    }else if("answer".equals(parser.getName())){
                        String answer = parser.nextText();
                        exercisesInfo.answer = Integer.parseInt(answer);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if("exercises".equals(parser.getName())){
                        exercisesInfos.add(exercisesInfo);
                        exercisesInfo = null;
                    }
                    break;
            }
            type = parser.next();
        }
        return exercisesInfos;
    }
    //设置是否被选中 即可编辑
    public static  void setABCDEnable(boolean value, ImageView iv_a,ImageView iv_b,ImageView iv_c,ImageView iv_d){
        iv_a.setEnabled(value);
        iv_b.setEnabled(value);
        iv_c.setEnabled(value);
        iv_d.setEnabled(value);
    }
    public static List<List<CourseBean>> getCourseInfos(InputStream is) throws Exception {
        XmlPullParser parser=Xml.newPullParser();
        parser.setInput(is,"utf-8");
        List<List<CourseBean>> courseInfos=null;
        List<CourseBean> courseList=null;
        CourseBean courseInfo=null;
        int count=0;
        int type=parser.getEventType();
        while (type!=XmlPullParser.END_DOCUMENT){
            switch (type){
                case XmlPullParser.START_TAG:
                    if("infos".equals(parser.getName())){
                        courseInfos = new ArrayList<List<CourseBean>>();
                        courseList=new ArrayList<CourseBean>();
                    }else if("course".equals(parser.getName())){
                        courseInfo = new CourseBean();
                        String ids = parser.getAttributeValue(0);
                        courseInfo.id = Integer.parseInt(ids);
                    }else if("title".equals(parser.getName())){
                        String title = parser.nextText();
                        courseInfo.title = title;
                    }else if("imgtitle".equals(parser.getName())){
                        String imgtitle = parser.nextText();
                        courseInfo.imgTitle = imgtitle;
                    }else if("intro".equals(parser.getName())){
                        String intro = parser.nextText();
                        courseInfo.intro = intro;
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if("course".equals(parser.getName())){
                        count++;
                        courseList.add(courseInfo);
                        //确保两个放在一行
                        if (count%2==0){
                            courseInfos.add(courseList);
                            courseList = null;
                            courseList=new ArrayList<CourseBean>();
                        }
                        courseInfo=null;

                    }
                    break;
            }
            type=parser.next();
        }
        return courseInfos;
    }
}
