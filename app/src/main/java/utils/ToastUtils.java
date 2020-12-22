package utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 狒狒 on 2020/7/16.
 */
public class ToastUtils {
    public static Toast mToast;
    public static void showMsg(Context context,String msg){
        if (mToast==null){
            mToast=Toast.makeText(context,msg,Toast.LENGTH_SHORT);

        }else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}
