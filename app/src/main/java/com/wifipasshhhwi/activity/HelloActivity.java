package com.wifipasshhhwi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tapjoy.Tapjoy;
import com.wifipasshhhwi.R;


public class HelloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        Button startButton = (Button) findViewById(R.id.startNow);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startNow = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startNow);
            }
        });

        Tapjoy.setDebugEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Tapjoy.onActivityStart(this);
    }

    @Override
    protected void onStop() {
        Tapjoy.onActivityStop(this);
        super.onStop();
    }

}
