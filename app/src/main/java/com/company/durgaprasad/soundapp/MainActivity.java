package com.company.durgaprasad.soundapp;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button pauseButton;
    MediaPlayer mPlayer;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.start_button);
        pauseButton = (Button) findViewById(R.id.pause_button);

        mPlayer = MediaPlayer.create(this, R.raw.hello);

        //To provides access to volume and ringer mode control.
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //To return the maximum volume index for a particular stream.
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        //To return the current volume index for a particular stream.
        int currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar volumeControl = (SeekBar) findViewById(R.id.seekBar);
        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(currVolume);

        final SeekBar audioControl = (SeekBar) findViewById(R.id.audioBar);
        audioControl.setMax(mPlayer.getDuration());

        //To run the program after every second
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            // Run the code immediately as well as run after every second
            public void run() {
                audioControl.setProgress(mPlayer.getCurrentPosition());
            }
        }, 0, 100);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.start();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.pause();
            }
        });

        //boolean fromUser to check whether the change in input was from the user or not
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            //Method to track the changes in the seekbar
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.v("Seekbar value", Integer.toString(progress));

                //To set the volume index for a particular stream
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        audioControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Setting the song to particular time
                mPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
