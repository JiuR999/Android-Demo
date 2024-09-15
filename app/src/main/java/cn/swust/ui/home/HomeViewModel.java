package cn.swust.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.swust.R;
import cn.swust.pojo.Chat;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    private List<Chat> chats = new ArrayList<>();
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public List<Chat> getChats(){
        if(chats != null && !chats.isEmpty()){
            return chats;
        }else {
            for (int i = 0; i < 20; i++) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    String time = format.format(System.currentTimeMillis()).toString();
                    Chat chat = new Chat(R.drawable.mine,"用户"+i,""+i,time);
                    chats.add(chat);
                }
            }
        }
        return chats;
    }
}