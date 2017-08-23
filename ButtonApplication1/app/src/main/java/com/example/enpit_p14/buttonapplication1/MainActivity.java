package com.example.enpit_p14.buttonapplication1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button changeButton= (Button) findViewById(R.id.button);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView changeText= (TextView) findViewById(R.id.ChangeText);
                changeText.setText("ほっけほっけほっけ");
            }
        });

    }

    public void onClick(View view) {
        TextView changeText= (TextView) findViewById(R.id.ChangeText);
        changeText.setText("ほっけほっけほっけ");
    }


}
