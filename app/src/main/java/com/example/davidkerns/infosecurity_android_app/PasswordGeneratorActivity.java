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
    private final String alpha_specials = "!@#$%^&*_+-=/{}[]()<>;:'?|,.~`";
    private final double guesses = 1.0e10;

    private EditText lengthText;
    private EditText capsText;
    private EditText digitsText;
    private EditText specialsText;
    private TextView generatedText;
    private TextView cracktimeText;

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
        cracktimeText = (TextView)findViewById(R.id.cracktime);

        Button password_valid = (Button) findViewById(R.id.generate_pword);

        password_valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = fieldValidation();
                if(flag){
                    generatePassword(length, caps, digits, specials);
                    calcCrackTime(length, caps, digits, specials);
                }
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

    private void calcCrackTime(int length, int caps, int digits, int specials){

        int sumAlphabet = 0;
        String cracktimeStr = "";
        if(caps > 0) sumAlphabet+=alpha_caps.length();
        if(digits > 0) sumAlphabet+=alpha_digits.length();
        if(specials > 0) sumAlphabet+=alpha_specials.length();
        if(caps+digits+specials < length) sumAlphabet+=alpha_lowers.length();

        double cracktime = Math.pow(sumAlphabet, length) / guesses;
        if(cracktime < 60) cracktimeStr = Double.toString(Math.round(cracktime * 10000d) / 10000d) + " seconds to crack";
        else if(cracktime < 3600) cracktimeStr = Double.toString(Math.round((cracktime/60) * 10000d) / 10000d) + " minutes to crack";
        else if(cracktime < 86400) cracktimeStr = Double.toString(Math.round((cracktime/3600) * 10000d) / 10000d) + " hours to crack";
        else if(cracktime < 604800) cracktimeStr = Double.toString(Math.round((cracktime/86400) * 10000d) / 10000d) + " days to crack";
        else if(cracktime < 2419200) cracktimeStr = Double.toString(Math.round((cracktime/604800) * 10000d) / 10000d) + " weeks to crack";
        else if(cracktime < 29030400) cracktimeStr = Double.toString(Math.round((cracktime/2419200) * 10000d) / 10000d) + " months to crack";
        else cracktimeStr = Double.toString(Math.round((cracktime/29030400) * 1000d) / 1000d) + " years to crack";
        cracktimeText.setText(cracktimeStr);
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

        if(length > 50 || length < 0){
            lengthText.setError("Length must be lower than 50 and greater than 0.");
            return false;
        }

        if(capsText.getText().toString().trim().equalsIgnoreCase("")){
            caps = 0;
        }else{
            try {
                caps = Integer.parseInt(capsText.getText().toString());
            } catch (NumberFormatException e) {
                capsText.setError("Please enter an integer");
                return false;
            }
        }

        if(caps < 0){
            capsText.setError("Cannot be less than 0");
            return false;
        }

        if(digitsText.getText().toString().trim().equalsIgnoreCase("")){
            digits = 0;
        }else{
            try {
                digits = Integer.parseInt(digitsText.getText().toString());
            } catch (NumberFormatException e) {
                digitsText.setError("Please enter an integer");
                return false;
            }
        }

        if(digits < 0){
            digitsText.setError("Cannot be less than 0");
            return false;
        }

        if(specialsText.getText().toString().trim().equalsIgnoreCase("")){
            specials = 0;
        }else{
            try {
                specials = Integer.parseInt(specialsText.getText().toString());
            } catch (NumberFormatException e) {
                specialsText.setError("Please enter an integer");
                return false;
            }
        }

        if(specials < 0){
            specialsText.setError("Cannot be less than 0");
            return false;
        }

        if(caps + digits + specials > length){
            capsText.setError("Sum of number of capitals, digits, and special characters must be " +
                    "less than or equal to the desired length.");
            return false;
        }

        return true;
    }

}
