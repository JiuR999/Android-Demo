package cn.swust.utils;

import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

public class FetchDataUtil {
    /**.
     *获取API接口数据。

     * @param url API地址。
     * @param jsonObject 绑定的Model对象。
     */
    public static void fetchData(String url, MutableLiveData<JSONObject> jsonObject) {
        new Thread(() -> {
            JSONObject object = OkHttpUtil.doGet(url);
            jsonObject.postValue(object);
        }).start();
    }
}
