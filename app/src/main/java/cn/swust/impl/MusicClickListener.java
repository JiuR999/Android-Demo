package cn.swust.impl;

import android.widget.SeekBar;

import java.io.IOException;

import cn.swust.adapter.MusicAdapter;

public interface MusicClickListener {
    void play(int position, MusicAdapter.ViewHolder holder) throws IOException;
    //void stop(int position, MusicAdapter.ViewHolder holder) throws IOException;
    //void change(SeekBar seekBar, int progress, boolean fromUser,MusicAdapter.ViewHolder holder,int position);
}
