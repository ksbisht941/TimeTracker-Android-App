package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

        ImageView alarm;
        MediaPlayer mediaPlayer;
        SeekBar timeSeekBar;
        TextView timeTextView;
        Animation animShake;
        Button controllerButton;
        Boolean counterIsActive = false;
        CountDownTimer countDownTimer;

        public void  resetTimer() {
            timeTextView.setText("0:30");
            timeSeekBar.setProgress(30);
            timeSeekBar.setEnabled(true);
            countDownTimer.cancel();
            counterIsActive = false;
            controllerButton.setText("GO!");
        }

        public void updateTimer(int secondLeft) {
            int minutes = (int) secondLeft / 60;
            int seconds = secondLeft - minutes * 60;

            String secondString = Integer.toString(seconds);

            if (seconds <= 9) {
                secondString = "0" + secondString;
            }

            timeTextView.setText(Integer.toString(minutes) + ":" + secondString);
        }

        public void timerControl(View view) {

        if (counterIsActive == false) {

            counterIsActive = true;
            timeSeekBar.setEnabled(false);
            controllerButton.setText("STOP!");

            countDownTimer = new CountDownTimer(timeSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);

                }

                @Override
                public void onFinish() {
                    resetTimer();
                    alarm.startAnimation(animShake);
                    mediaPlayer.start();
                }
            }.start();
        } else {
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarm = findViewById(R.id.alarm);
        timeSeekBar = findViewById(R.id.timeSeekBar);
        timeTextView = findViewById(R.id.timeTextView);
        animShake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
        controllerButton = findViewById(R.id.controllerButton);

        timeTextView.setEnabled(false);

        mediaPlayer = MediaPlayer.create(this, R.raw.airhorn);

        timeSeekBar.setMax(600);
        timeSeekBar.setProgress(30);

        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);

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
