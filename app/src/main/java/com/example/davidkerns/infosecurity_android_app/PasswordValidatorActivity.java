package com.example.davidkerns.infosecurity_android_app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordValidatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_validator);

        Button password_valid = (Button) findViewById(R.id.validate_pword);

        password_valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpass();
            }
        });
    }

    void showpass(){
        EditText passField = (EditText)findViewById(R.id.passfield);
        String toastMessage = "Password: " + passField.getText().toString();
        Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT);
        toast.show();

        TextView letter = (TextView) findViewById(R.id.haslet);
        TextView number = (TextView) findViewById(R.id.hasnum);
        TextView special = (TextView) findViewById(R.id.hasspec);
        TextView criteria = (TextView) findViewById(R.id.criteriamet);

        int metcount = 0;

        if(toastMessage.matches(".*[a-zA-Z]+.*")) {
            letter.setTextColor(Color.GREEN);
            metcount++;
        }
        else
            letter.setTextColor(Color.RED);

        if(toastMessage.matches(".*\\d+.*")) {
            number.setTextColor(Color.GREEN);
            metcount++;
        }
        else
            number.setTextColor(Color.RED);

        if(toastMessage.matches(".*[!@#$%^&*]+.*")) {
            special.setTextColor(Color.GREEN);
            metcount++;
        }
        else
            special.setTextColor(Color.RED);

        criteria.setText("Met "+metcount+" of 3 safety criteria");
    }



}
