package cn.swust.ui.music;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cn.swust.pojo.Music;

public class MusicViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private  List<Music> musics;

    private Context context;
    public MusicViewModel() {
        mText = new MutableLiveData<>();

        //initMusics();

        mText.setValue("This is notifications fragment");
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public LiveData<String> getText() {
        return mText;
    }

    public List<Music> getMusics(){
        return this.musics;
    }
}