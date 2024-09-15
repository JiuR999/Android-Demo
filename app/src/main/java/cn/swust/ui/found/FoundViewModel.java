package cn.swust.ui.found;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.swust.R;
import cn.swust.pojo.FoundMenu;

public class FoundViewModel extends AndroidViewModel {
    private List<FoundMenu> menus = new ArrayList<>();
    private String[] texts;
    private final int[] imgs = {R.drawable.pyq, R.drawable.sys, R.drawable.yyy, R.drawable.kyk
            , R.drawable.fjdr, R.drawable.plp, R.drawable.shop, R.drawable.game, R.drawable.xcx};

    public FoundViewModel(@NonNull Application application) {
        super(application);
        texts = getApplication().getResources().getStringArray(R.array.funtions);
    }

    public List<FoundMenu> getListDate() {
        if (menus != null && !menus.isEmpty()) {
            return menus;
        } else {
            for (int i = 0; i < imgs.length; i++) {
                FoundMenu menu = new FoundMenu(imgs[i], texts[i]);
                menus.add(menu);
            }
        }
        return menus;
    }
}