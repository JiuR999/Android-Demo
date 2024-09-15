package cn.swust.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import cn.swust.R;

public class MusicService extends Service {
    public final static String PAUSE = "pause";
    public final static String PLAY = "play";
    public final static String STOP = "stop";
    public final static String CMD = "cmd";
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    public  MusicService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this,R.raw.sun);
        }
        String cmd = intent.getStringExtra("cmd");
        assert cmd != null &&(! "".equals(cmd));
        if(cmd .equals(PLAY)){
            mediaPlayer.start();
        } else if(cmd.equals(PAUSE)){
            mediaPlayer.pause();
        } else {
            mediaPlayer.stop();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        mediaPlayer = null;
    }
}