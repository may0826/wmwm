package utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 狒狒 on 2020/7/8.
 */
public class ReadLoginName {
    public static String readLoginUserName(Context context){
        SharedPreferences sp=context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        String username=sp.getString("loginUsername","");
    //   String username= DBUtils.getInstance(context).getRegisterUsernames();
        return username;
    }
}
