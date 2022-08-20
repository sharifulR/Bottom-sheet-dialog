package com.example.bottomsheet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView clickTV,showTime,lTimeTv,countTime;
    long save;
    long lTime;
    long countDownTime;

    private LocalStore timePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickTV=findViewById(R.id.clickTV);
        showTime=findViewById(R.id.dTime);
        lTimeTv=findViewById(R.id.lTime);
        countTime=findViewById(R.id.countTime);

        timePrefs=LocalStore.getInstance(this);

        getSupportActionBar().hide();

        countDownTimes();

        clickTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*BottomSheetFragment bottomSheetFragment=new BottomSheetFragment();
                bottomSheetFragment.show(getSupportFragmentManager(),bottomSheetFragment.getTag());*/

                SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss a");
                long currentHours = sdtf.getCalendar().getTime().getHours();
                long hoursToMin=currentHours*60;
                long currentMinutes = sdtf.getCalendar().getTime().getMinutes();
                save=5+currentMinutes+hoursToMin;
                timePrefs.insertDateTime(save);
                showTime.setText(String.valueOf(timePrefs.getTime()));
                countDownTimes();
            }
        });

    }

    private void countDownTimes() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss a");
        long currentHours = sdf.getCalendar().getTime().getHours();
        long hoursToMin=currentHours*60;
        long currentMinutes = sdf.getCalendar().getTime().getMinutes();
        lTime=currentMinutes+hoursToMin;
        lTimeTv.setText(String.valueOf(lTime));

        if (lTime==timePrefs.getTime()){
            // cancel button is gone
            countTime.setText("done!");
            lTimeTv.setVisibility(View.GONE);
        }else {
            //show
            countDownTime=timePrefs.getTime()-lTime;
            //countTime.setText(String.valueOf("countTime : "+ (save-lTime)));

            new CountDownTimer((countDownTime * 60L) * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    long Mmin = (millisUntilFinished / 1000) / 60;
                    long Ssec = (millisUntilFinished / 1000) % 60;
                    if (Ssec < 10) {
                        countTime.setText("" + Mmin + ":0" + Ssec);
                    } else countTime.setText("" + Mmin + ":" + Ssec);

                }

                public void onFinish() {
                    countTime.setText("done!");
                }

            }.start();

        }
    }
}