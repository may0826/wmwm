package utils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 狒狒 on 2020/7/7.
 */
public class sendCode {
    public static final MediaType JSON=MediaType.parse("application/json;charest=utf-8");
    OkHttpClient client=new OkHttpClient();


    public String post(String url, String json)throws IOException {
        RequestBody body=RequestBody.create(JSON,json);
        Request request=new Request.Builder().url(url).post(body).build();

        try(Response response=client.newCall(request).execute()) {
            return response.body().string();

        }
    }
    public String bowlingJson(String checkCode, String phoneNo){
        return "{'sid':'58d6df9f51ca633196267ce4c5e7ff95'," +
                " 'token':'88ed981c26187aa89944779b033496aa'," +
                " 'appid':'01894f7c7f0747fc86653bcd04f966e9'," +
                " 'templateid':'554733'," +
                " 'param':'"+checkCode+",60'," +
                " 'mobile':'"+phoneNo+"'," +
                " 'uid':''}";
    }

}
