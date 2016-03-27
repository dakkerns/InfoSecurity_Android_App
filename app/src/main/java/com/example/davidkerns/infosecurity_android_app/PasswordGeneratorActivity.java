package com.example.davidkerns.infosecurity_android_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Random;

public class PasswordGeneratorActivity extends AppCompatActivity {

    private final String alpha_caps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String alpha_lowers = "abcdefghijklmnopqrstuvwxyz";
    private final String alpha_digits = "0123456789";
    private final String alpha_specials = "!@#$%^&*_+-=/{}[]<>";

    private EditText lengthText;
    private EditText capsText;
    private EditText digitsText;
    private EditText specialsText;
    private TextView generatedText;

    private int length = 0, caps = 0, digits = 0, specials = 0;

    private Random rnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_generator);

        rnd = new Random();

        lengthText = (EditText)findViewById(R.id.password_length);
        capsText = (EditText)findViewById(R.id.password_caps);
        digitsText = (EditText)findViewById(R.id.password_digits);
        specialsText = (EditText)findViewById(R.id.password_specials);

        generatedText = (TextView)findViewById(R.id.generated_password);

        Button password_valid = (Button) findViewById(R.id.generate_pword);

        password_valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = fieldValidation();
                if(flag) generatePassword(length, caps, digits, specials);
            }
        });
    }

    private void generatePassword(int length, int caps, int digits, int specials){

        char[] password = new char[length];
        int index;
        for(int i = 0; i < caps; i++){
            index = getNextIndex(length, password);
            password[index] = alpha_caps.charAt(rnd.nextInt(alpha_caps.length()));
        }

        for(int i = 0; i < digits; i++){
            index = getNextIndex(length, password);
            password[index] = alpha_digits.charAt(rnd.nextInt(alpha_digits.length()));
        }

        for(int i = 0; i < specials; i++){
            index = getNextIndex(length, password);
            password[index] = alpha_specials.charAt(rnd.nextInt(alpha_specials.length()));
        }

        for(int i = 0; i < length; i++){
            if(password[i] == 0)password[i] = alpha_lowers.charAt(rnd.nextInt(alpha_lowers.length()));
        }

        generatedText.setText(new String(password));
    }

    private int getNextIndex(int length, char[] password){
        int ind;
        while(password[ind = rnd.nextInt(length)] != 0);
        return ind;
    }

    private boolean fieldValidation(){

        try{
            if(lengthText.getText().toString().trim().equalsIgnoreCase("")){
                lengthText.setError("This field cannot be blank.");
                return false;
            }
            length = Integer.parseInt(lengthText.getText().toString());
        }catch(NumberFormatException e){
            lengthText.setError("Please enter an integer");
            return false;
        }

        if(length > 50){
            lengthText.setError("Length must be 50 or below.");
            return false;
        }

        if(!capsText.getText().toString().trim().equalsIgnoreCase("")) {
            try {
                caps = Integer.parseInt(capsText.getText().toString());
            } catch (NumberFormatException e) {
                capsText.setError("Please enter an integer");
                return false;
            }
        }

        if(!digitsText.getText().toString().trim().equalsIgnoreCase("")) {
            try {
                digits = Integer.parseInt(digitsText.getText().toString());
            } catch (NumberFormatException e) {
                digitsText.setError("Please enter an integer");
                return false;
            }
        }

        if(!specialsText.getText().toString().trim().equalsIgnoreCase("")) {
            try {
                specials = Integer.parseInt(specialsText.getText().toString());
            } catch (NumberFormatException e) {
                specialsText.setError("Please enter an integer");
                return false;
            }
        }

        if(caps + digits + specials > length){
            capsText.setError("Sum of number of capitals, digits, and special characters must be " +
                    "less than or equal to the desired length.");
            return false;
        }

        return true;
    }

}
