package com.android.foxxyy.mailtoex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

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

        if (cbg.isChecked() && !cbb.isChecked()) {
            text = getString(R.string.girlEr);
        }
        if (cbb.isChecked() && !cbg.isChecked()) {
            text = getString(R.string.barEr);
        }
        if (cbb.isChecked() && cbg.isChecked()) {
            text = getString(R.string.bothEr);
        }
        mes.setText(text);
    }
}
