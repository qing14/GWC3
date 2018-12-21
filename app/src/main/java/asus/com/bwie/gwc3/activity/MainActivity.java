package asus.com.bwie.gwc3.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import asus.com.bwie.gwc3.R;
import asus.com.bwie.gwc3.history.HistoryFlowLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        final HistoryFlowLayout historyfl = findViewById(R.id.historyfl);
        final EditText edit = findViewById(R.id.edit);
        findViewById(R.id.addtext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = new TextView(MainActivity.this);
                textView.setText(edit.getText());
                textView.setTextColor(Color.CYAN);
                historyfl.addView(textView);
                Intent intent=new Intent(MainActivity.this,RecycleActivity.class);
                startActivity(intent);


            }
        });




    }
}
