package cn.swust.utils;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtil {

    public static JSONObject doGet(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                return new JSONObject(response.body().string());
            }
        } catch (IOException e) {
            return null;
        } catch (JSONException e) {
            return null;
        }
        return null;
    }
}
