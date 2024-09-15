package cn.swust.impl;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
 
    public SpacesItemDecoration(int space) {
        this.space = space;
    }
 
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // 获取当前item的position
        int position = parent.getChildAdapterPosition(view);
        List<Integer> pos = List.of(1,3,4,6,8);
        // 设置间隔
        if (pos.contains(position)) {
            outRect.top = space;
        }
    }
}