package com.example.diseaze;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CardPagerAdapter adapter;
    private Timer timer;
    private final int[] cardLayouts = {
            R.layout.card_layout_1,
            R.layout.card_layout_2,
            R.layout.card_layout_3
    };
    private int currentPage = 0;

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        btn = findViewById(R.id.btnGetStarted);
        adapter = new CardPagerAdapter(this, cardLayouts);
        viewPager.setAdapter(adapter);

        // Set the off-screen page limit to display more pages
        viewPager.setOffscreenPageLimit(cardLayouts.length);

        // Auto-scroll timer
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (currentPage == cardLayouts.length - 1) {
                            currentPage = 0;
                        } else {
                            currentPage++;
                        }
                        viewPager.setCurrentItem(currentPage, true);
                    }
                });
            }
        }, 3000, 3000);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotonext();
            }
        });
    }

    private void gotonext() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}