package com.cmcc.attendance.activity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;


public class Player implements MediaPlayer.OnPreparedListener{ 
    public MediaPlayer mediaPlayer; 
    private SeekBar skbProgress; 
    private Timer mTimer=new Timer(); 
    public Player(SeekBar skbProgress)  
    { 
        this.skbProgress=skbProgress; 
        try { 
            mediaPlayer = new MediaPlayer(); 
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC); 
            mediaPlayer.setOnPreparedListener(this); 
        } catch (Exception e) { 
            Log.e("mediaPlayer", "error", e); 
        } 
        mTimer.schedule(mTimerTask, 0, 10); 
    } 
     
    TimerTask mTimerTask = new TimerTask() { 
        @Override 
        public void run() { 
            if(mediaPlayer==null) 
                return; 
            if(mediaPlayer!=null){ 
            	try{
                if (mediaPlayer.isPlaying() && skbProgress.isPressed() == false) { 
                    handleProgress.sendEmptyMessage(0); 
                } 
            	}catch(Exception e){
            		e.printStackTrace();
            	}
            } 
        } 
    }; 
     
    Handler handleProgress = new Handler() { 
        public void handleMessage(Message msg) { 
            if(mediaPlayer==null){ 
                return; 
            } 
            int position = mediaPlayer.getCurrentPosition(); 
            int duration = mediaPlayer.getDuration(); 
            if (duration > 0) { 
                long pos = skbProgress.getMax() * position / duration; 
                skbProgress.setProgress((int) pos); 
            } 
        }; 
    }; 
 
     
    public void playUrl(String videoUrl) 
    { 
        try { 
            mediaPlayer.reset(); 
            mediaPlayer.setDataSource(videoUrl); 
            mediaPlayer.prepareAsync(); 
        } catch (IllegalArgumentException e) { 
            e.printStackTrace(); 
        } catch (IllegalStateException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
    } 
 
    @Override 
    public void onPrepared(MediaPlayer arg0) { 
        arg0.start(); 
        Log.e("mediaPlayer", "onPrepared"); 
    } 
} 