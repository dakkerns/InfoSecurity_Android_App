package com.example.davidkerns.infosecurity_android_app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

        Button password_valid = (Button) findViewById(R.id.show_pword);

        EditText passField = (EditText)findViewById(R.id.passfield);
        passField.addTextChangedListener(passwordWatcher);

        password_valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpass();
            }
        });
    }

    private final TextWatcher passwordWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        public void afterTextChanged(Editable s) {
            validatepass();
        }
    };

    void showpass(){
        EditText passField = (EditText)findViewById(R.id.passfield);
        String password = passField.getText().toString();

        Toast pass = Toast.makeText(getApplicationContext(), password, Toast.LENGTH_SHORT);
        pass.show();
    }

    void validatepass(){
        EditText passField = (EditText)findViewById(R.id.passfield);
        String password = passField.getText().toString();

        TextView letter = (TextView) findViewById(R.id.haslet);
        TextView number = (TextView) findViewById(R.id.hasnum);
        TextView special = (TextView) findViewById(R.id.hasspec);
        TextView length = (TextView) findViewById(R.id.haslen);
        TextView criteria = (TextView) findViewById(R.id.criteriamet);

        int metcount = 0;

        if(password.matches(".*[a-zA-Z]+.*")) {
            letter.setTextColor(Color.GREEN);
            metcount++;
        }
        else
            letter.setTextColor(Color.RED);

        if(password.matches(".*\\d+.*")) {
            number.setTextColor(Color.GREEN);
            metcount++;
        }
        else
            number.setTextColor(Color.RED);

        if(password.matches(".*[!@#$%^&*]+.*")) {
            special.setTextColor(Color.GREEN);
            metcount++;
        }
        else
            special.setTextColor(Color.RED);

        if(password.length() >= 8) {
            length.setTextColor(Color.GREEN);
            metcount++;
        }
        else
            length.setTextColor(Color.RED);

        criteria.setText("Met "+metcount+" of 4 safety criteria");

        if(metcount < 2)
            criteria.setTextColor(Color.RED);
        else if(metcount < 4)
            criteria.setTextColor(Color.YELLOW);
        else
            criteria.setTextColor(Color.GREEN);

    }



}
