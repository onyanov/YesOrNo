package ru.onyanov.yesorno;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView viewQuestion;
    private TextView viewScore;
    private TextView viewComment;
    private Button yes;
    private Button no;
    private Button next;
    private LinearLayout ll;
    private ImageView sound;
    JsonHelper jsonHelper;
    int correct;
    private int score;
    int green;
    int red;
    int blue;
    private String question;
    private String comment;
    private SoundPool soundPool;
    private int soundRight;
    private int soundWrong;
    boolean loaded = false;
    boolean useSound = true;
    boolean showAd = true;
    int skipAd = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //AdBuddiz.getInstance().cacheAds(this);
        setContentView(R.layout.activity_main);

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {

            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                loaded = true;

            }
        });
        soundRight = soundPool.load(this, R.raw.right, 1);
        soundWrong = soundPool.load(this, R.raw.wrong, 1);

        blue = getResources().getColor(R.color.blue);
        green = getResources().getColor(R.color.green);
        red = getResources().getColor(R.color.red);

        score = 0;
        ll = (LinearLayout) findViewById(R.id.main);
        viewScore = (TextView) findViewById(R.id.score);
        viewQuestion = (TextView) findViewById(R.id.question);
        viewComment = (TextView) findViewById(R.id.comment);
        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);
        next = (Button) findViewById(R.id.next);
        sound = (ImageView) findViewById(R.id.sound);
        ImageView skip = (ImageView) findViewById(R.id.skip);

        jsonHelper = new JsonHelper(getBaseContext());


        skip.setOnClickListener(getListener(0));
        next.setOnClickListener(getListener(0));
        yes.setOnClickListener(getListener(1));
        no.setOnClickListener(getListener(2));
        sound.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (useSound) {
                    sound.setImageResource(R.drawable.ic_nosound);
                    useSound = false;
                } else {
                    sound.setImageResource(R.drawable.ic_sound);
                    useSound = true;
                }
            }
        });

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(),
                        StartActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //AdBuddiz.getInstance().onStart(this);
        showRiddle();
    }

    private View.OnClickListener getListener(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer(i);
            }
        };
    }

    protected void showAnswer(int answer) {
        if (answer > 0) {
            if (correct == answer) {
                changeBg(ll, green);
                score += 1;
                playSound(soundRight);
                showAd = false;
            } else {
                changeBg(ll, red);
                if (score > 0)
                    score -= 2;
                playSound(soundWrong);

                if (skipAd == 0) {
                    showAd = true;
                    skipAd = 5;
                } else skipAd--;

            }
            viewScore.setText("" + score);
            showComment();
        } else {
            showRiddle();
        }
    }

    private void playSound(int sound) {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = actualVolume / maxVolume;
        if (loaded && useSound) {
            soundPool.play(sound, volume, volume, 1, 0, 1f);
        }
    }

    private void showComment() {
        viewComment.setText(question);
        viewComment.setVisibility(View.VISIBLE);
        viewQuestion.setText(comment);
        yes.setVisibility(View.GONE);
        no.setVisibility(View.GONE);
        next.setVisibility(View.VISIBLE);

    }

    private void showRiddle() {
        changeBg(ll, blue);
        JSONObject riddle = jsonHelper.getRiddle();
        try {
            question = riddle.getString("question");
            viewQuestion.setText(question);
            comment = riddle.getString("comment");
            correct = riddle.getInt("correct");
        } catch (JSONException e) {
            e.printStackTrace();
            showRiddle();
        }
        viewComment.setVisibility(View.GONE);
        yes.setVisibility(View.VISIBLE);
        no.setVisibility(View.VISIBLE);
        next.setVisibility(View.GONE);
        if (showAd) {
            Log.d("mAdBuddiz", "showAd");
            //AdBuddiz.getInstance().showAd();
            showAd = false;
        }
    }

    private void changeBg(View view, int color) {
        view.setBackgroundColor(color);
    }
}
