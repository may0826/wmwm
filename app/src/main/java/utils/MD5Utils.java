package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 狒狒 on 2020/7/6.
 * 密码 :使用MD5的加密算法
 */

public class MD5Utils {
    public static String md5(String text){
        MessageDigest digest=null;
        try {

            digest=MessageDigest.getInstance("md5");
            byte[] result=digest.digest(text.getBytes());//将密码string型用比特型表示
            StringBuilder sb=new StringBuilder();
            for (byte b:result){
                int number=b&0xff;
                String hex=Integer.toHexString(number);
                if (hex.length()==1){
                    sb.append("0"+hex);
                }else{
                    sb.append(hex);
                }

            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
