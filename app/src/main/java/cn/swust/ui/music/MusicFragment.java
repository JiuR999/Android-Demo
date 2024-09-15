package cn.swust.ui.music;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import cn.swust.R;
import cn.swust.adapter.MusicAdapter;
import cn.swust.databinding.FragmentMusicBinding;
import cn.swust.databinding.MusicItemBinding;
import cn.swust.impl.MusicClickListener;
import cn.swust.pojo.Music;
import cn.swust.service.MusicService;

public class MusicFragment extends Fragment implements View.OnClickListener {

    public static final int MUSIC_NAME = 5;
    private static final int MUSIC_PLAY = 0;
    private static final int MUSIC_STOP = 1;
    private static final int MUSIC_PAUSE = 2;
    private FragmentMusicBinding binding;
    private Handler handler;
    private MusicViewModel musicViewModel;
    private MediaPlayer mediaPlayer;
    private List<Music> musics = new ArrayList<>();
    private boolean isPause;

    private boolean isServicePause = true;
    private MusicAdapter adapter;
    private int current = 0;
    private Intent intent;
    private final int MUSIC_PROGRESS = 3;

    private MusicThread musicThread;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        musicViewModel = new ViewModelProvider(this).get(MusicViewModel.class);
        musicViewModel.setContext(getActivity().getApplicationContext());
        binding = FragmentMusicBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initHandler();
        initDate();
        initRecycleView();
        registerClick();

        return root;
    }

    private void initHandler() {
        handler = new Handler(Objects.requireNonNull(Looper.myLooper())){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                int code = msg.what;
                if(code == MUSIC_PLAY){
                    if(current != mediaPlayer.getDuration()){
                        binding.slider.setProgress(current);
                        binding.currentProgress.setText(format(current));
                    } else {
                        stop();
                    }
                }
            }
        };
    }

    private void initDate() {
        Context context = getContext();
        intent = new Intent(getActivity(), MusicService.class);
        String[] musicNames = context.getResources().getStringArray(R.array.music_name_list);
        int[] musicDatas = {R.raw.daerwen, R.raw.daoxiang, R.raw.sun, R.raw.huahai, R.raw.hongdou};
        String[] musicSingers = context.getResources().getStringArray(R.array.music_singer_list);
        int[] covers = {R.drawable.daerwen, R.drawable.daoxiang, R.drawable.qingtian, R.drawable.huahai, R.drawable.hongdou};
        musics = new ArrayList<>();
        for (int i = 0; i < musicSingers.length; i++) {
            Music music = new Music(musicNames[i], musicSingers[i], musicDatas[i], covers[i]);
            music.setCurrent(0);
            music.setPlay(false);
            musics.add(music);
        }
    }

    private void initRecycleView() {
        adapter = new MusicAdapter(getActivity(), musics);

        adapter.setClickListener(new MusicClickListener() {
            @Override
            public void play(int position, MusicAdapter.ViewHolder holder) throws IOException {
                Music music = musics.get(position);
                if(mediaPlayer != null){
                    stop();
                }
                mediaPlayer = new MediaPlayer();
                mediaPlayer = MediaPlayer.create(getActivity(), music.getUrl());
                int duration = mediaPlayer.getDuration();
                music.setTotal(duration);
                binding.slider.setMax(duration);
                binding.totalProgress.setText(format(duration));
                mediaPlayer.start();
                musicThread = new MusicThread();
                musicThread.start();
                binding.imgCover.setImageResource(music.getCover());
                binding.tvMusicName.setText(music.getName());
                binding.imgPlay.setImageResource(R.drawable.music_pause);
                //TODO
                //组合多个动画 相当于缩放效果
                binding.recycleviewMusic.setVisibility(View.GONE);
                binding.cardviewMusic.setVisibility(View.VISIBLE);
                scaleIn(binding.cardviewMusic);
            }
        });
        binding.recycleviewMusic.setAdapter(adapter);
        binding.recycleviewMusic.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    private void registerClick() {
        binding.imgStop.setOnClickListener(this);
        binding.imgPlay.setOnClickListener(this);
        binding.imgList.setOnClickListener(this);
        binding.imgServicePlay.setOnClickListener(this);
        binding.imgServiceStop.setOnClickListener(this);
        binding.slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(fromUser){
                        current = progress;
                        mediaPlayer.seekTo(progress);
                    }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //getActivity().stopService(intent);
        binding = null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.img_stop) {
            stop();
        } else if (id == R.id.img_list) {
            stop();
            scaleOut(binding.cardviewMusic);
            binding.recycleviewMusic.setVisibility(View.VISIBLE);
            binding.cardviewMusic.setVisibility(View.INVISIBLE);
        } else if (id == R.id.img_play) {
            if (isPause) {
                mediaPlayer.start();
                musicThread = new MusicThread();
                musicThread.start();
                binding.imgPlay.setImageResource(R.drawable.music_pause);
            } else {
                mediaPlayer.pause();
                musicThread.interrupt();
                binding.imgPlay.setImageResource(R.drawable.music_play);
            }
            isPause = !isPause;
        } else if (id == R.id.img_list) {
            stop();
        } else if (id == R.id.img_service_play) {
            if(isServicePause){
                intent.putExtra(MusicService.CMD,MusicService.PLAY);
                binding.imgServicePlay.setImageResource(R.drawable.music_pause);
            } else {
                intent.putExtra(MusicService.CMD,MusicService.PAUSE);
                binding.imgServicePlay.setImageResource(R.drawable.music_play);
            }
            getActivity().startService(intent);
            isServicePause = ! isServicePause;
        } else if (id == R.id.img_service_stop) {
            intent.putExtra("cmd",MusicService.STOP);
            binding.imgServicePlay.setImageResource(R.drawable.music_play);
            getActivity().startService(intent);
        }
    }

    private void scaleOut(View view) {
        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f,1f);
        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f,1f);
        PropertyValuesHolder p3 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f,1f);
        ObjectAnimator.ofPropertyValuesHolder(view, p1, p2, p3).setDuration(800).start();
    }

    private void scaleIn(View view) {

        PropertyValuesHolder p4 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder p5 = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        PropertyValuesHolder p6 = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, p4, p5, p6).setDuration(1000).start();
    }
    private String format(int item){
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        return format.format(item);
    }

    class MusicThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(!Thread.currentThread().isInterrupted()){
                if(current > mediaPlayer.getDuration()){
                    current = 0;
                    mediaPlayer.seekTo(0);
                }
                try {
                    Thread.sleep(1000);
                    current += 1000;
                    handler.sendEmptyMessage(MUSIC_PLAY);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    private void stop() {
        if (!isPause) {
            mediaPlayer.pause();
        }
        isPause = false;
        if(!musicThread.isInterrupted()){
            musicThread.interrupt();
        }
        current = 0;
        mediaPlayer.release();
        mediaPlayer = null;
        binding.recycleviewMusic.setVisibility(View.VISIBLE);
        binding.cardviewMusic.setVisibility(View.GONE);
        handler.removeMessages(MUSIC_PLAY);
    }
}