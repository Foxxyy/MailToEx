package com.android.foxxyy.mailtoex;

import android.app.ActionBar;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void send(View view) {
        String text = "All OK";
        TextView mes = (TextView) findViewById(R.id.userMessages);
        CheckBox cbg = (CheckBox) findViewById(R.id.cbg);
        CheckBox cbb = (CheckBox) findViewById(R.id.cbb);
        mes.setTextColor(this.getResources().getColor(R.color.colorPrimary));

        if (cbg.isChecked() && !cbb.isChecked()) {
            text = getString(R.string.girlEr);
            mes.setTextColor(Color.RED);
        }
        if (cbb.isChecked() && !cbg.isChecked()) {
            text = getString(R.string.barEr);
            mes.setTextColor(Color.RED);
        }
        if (cbb.isChecked() && cbg.isChecked()) {
            text = getString(R.string.bothEr);
            mes.setTextColor(Color.RED);
        }
        mes.setText(text);
    }

    Boolean flagPhoto = false;
    public void addPhoto(View view) {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.photo);
        LinearLayout ll = (LinearLayout) findViewById(R.id.phSettings);
        ViewGroup.LayoutParams params = rl.getLayoutParams();


        Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        if(rl.getVisibility()==View.INVISIBLE){
            //rl.startAnimation(slideDown);
            rl.setVisibility(View.VISIBLE);
            params.height = -2;
            rl.setLayoutParams(params);
        } else {
            //rl.startAnimation(slideUp);
            rl.setVisibility(View.INVISIBLE);
            params.height = 0;
            rl.setLayoutParams(params);
        }
    }

}
