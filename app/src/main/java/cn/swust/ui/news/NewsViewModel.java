package cn.swust.ui.news;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONObject;

public class NewsViewModel extends ViewModel {
    private MutableLiveData<JSONObject> jsonObject;

    public MutableLiveData<JSONObject> getJsonObject() {
        if(jsonObject == null){
            jsonObject = new MutableLiveData<>();
        }
        return jsonObject;
    }
}
