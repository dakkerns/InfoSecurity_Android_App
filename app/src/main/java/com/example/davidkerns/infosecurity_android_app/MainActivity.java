package com.example.davidkerns.infosecurity_android_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button passwordGeneratorButton = (Button) findViewById(R.id.password_generator_button);
        Button passwordValidatorButton = (Button) findViewById(R.id.password_validator_button);

        passwordGeneratorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PasswordGeneratorActivity.class);
                startActivity(i);
            }
        });

        passwordValidatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PasswordValidatorActivity.class);
                startActivity(i);
            }
        });
    }
}