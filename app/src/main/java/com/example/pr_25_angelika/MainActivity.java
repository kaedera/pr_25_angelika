package com.example.pr_25_angelika;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int sJett, sReyna, sViper, sSage, sKilljoy, sRaze;
    private int mStreamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        sJett = loadSound("jett.mp3");
        sReyna = loadSound("reyna.mp3");
        sViper = loadSound("viper.mp3");
        sSage = loadSound("sage.mp3");
        sKilljoy = loadSound("killjoy.mp3");
        sRaze = loadSound("raze.mp3");

        ImageButton JettImageButton = findViewById(R.id.JettBtn);
        JettImageButton.setOnClickListener(onClickListener);

        ImageButton ReynaImageButton = findViewById(R.id.ReynaBtn);
        ReynaImageButton.setOnClickListener(onClickListener);

        ImageButton ViperImageButton = findViewById(R.id.ViperBtn);
        ViperImageButton.setOnClickListener(onClickListener);

        ImageButton SageImageButton = findViewById(R.id.SageBtn);
        SageImageButton.setOnClickListener(onClickListener);

        ImageButton KilljoyImageButton = findViewById(R.id.KilljoyBtn);
        KilljoyImageButton.setOnClickListener(onClickListener);

        ImageButton RazeImageButton = findViewById(R.id.RazeBtn);
        RazeImageButton.setOnClickListener(onClickListener);



    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int id = v.getId();

            if (id == R.id.JettBtn)
            {
                playSound(sJett);
            }
            else if (id == R.id.ReynaBtn)
            {
                playSound(sReyna);
            }
            else if (id == R.id.ViperBtn)
            {
                playSound(sViper);
            }
            else if (id == R.id.SageBtn)
            {
                playSound(sSage);
            }
            else if (id == R.id.KilljoyBtn)
            {
                playSound(sKilljoy);
            }
            else if (id == R.id.RazeBtn)
            {
                playSound(sRaze);
            }
        }
    };


    @TargetApi(Build.VERSION_CODES.TIRAMISU)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        sJett = loadSound("jett.mp3");
        sReyna = loadSound("reyna.mp3");
        sViper = loadSound("viper.mp3");
        sSage = loadSound("sage.mp3");
        sKilljoy = loadSound("killjoy.mp3");
        sRaze = loadSound("raze.mp3");

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }
}