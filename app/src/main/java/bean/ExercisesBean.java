package bean;

/**
 * Created by 狒狒 on 2020/7/13.
 */
public class ExercisesBean {
    public int id;//章节id
    public  String title;//章节标题
    public String content;//习题数目
    public int background;//习题前的序号 可以看见
    public int subjectId;//每道习题的id 通常看不见
    public String subject;//每道习题的题干
    public String a;//A选项
    public String b;//B选项
    public String c;//C选项
    public String d;//D选项
    public int answer;//正确选项，存状态
    public int select;//选择的选项（悬得是否正确，0代表选择正确，1代表选A错误，以此类推）
}
