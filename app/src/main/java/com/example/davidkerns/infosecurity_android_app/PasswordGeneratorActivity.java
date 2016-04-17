package com.example.davidkerns.infosecurity_android_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class PasswordGeneratorActivity extends AppCompatActivity {

    private final String alpha_caps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String alpha_lowers = "abcdefghijklmnopqrstuvwxyz";
    private final String alpha_digits = "0123456789";
    private final String alpha_specials = "!@#$%^&*_+-=/{}()<>;:'?|,.~`";
    private final double guessesLow = 5.0e9;
    private final double guessesMedium = 3.5e11;
    private final double guessesHigh = 1.0e14;

    private EditText lengthText;
    private EditText capsText;
    private EditText digitsText;
    private EditText specialsText;
    private EditText phraseText;
    private TextView generatedText;
    private TextView cracktimeText;
    private RadioButton radioLow;
    private RadioButton radioMedium;
    private RadioButton radioHigh;

    private NumberFormat smallFormatter;
    private NumberFormat largeFormatter;

    private int length = 0, caps = 0, digits = 0, specials = 0;
    private double guesses;
    private String phrase;

    private Random rnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_generator);

        rnd = new Random();
        smallFormatter = new DecimalFormat("0.00");
        largeFormatter = new DecimalFormat("0.000E0");

        lengthText = (EditText)findViewById(R.id.password_length);
        capsText = (EditText)findViewById(R.id.password_caps);
        digitsText = (EditText)findViewById(R.id.password_digits);
        specialsText = (EditText)findViewById(R.id.password_specials);
        phraseText = (EditText)findViewById(R.id.password_phrase);
        generatedText = (TextView)findViewById(R.id.generated_password);
        cracktimeText = (TextView)findViewById(R.id.cracktime);
        radioLow = (RadioButton) findViewById(R.id.radio_low);
        radioMedium = (RadioButton) findViewById(R.id.radio_medium);
        radioHigh = (RadioButton) findViewById(R.id.radio_high);

        radioLow.setChecked(true);

        Button password_valid = (Button) findViewById(R.id.generate_pword);

        password_valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = fieldValidation();
                if(flag){
                    guesses = crackSpeed();
                    generatePassword(length, caps, digits, specials, phrase);
                    calcCrackTime(length, caps, digits, specials, phrase, guesses);
                }
            }
        });
    }

    private void generatePassword(int length, int caps, int digits, int specials, String phrase){

        char[] password = new char[length];
        int index;

        if(phrase != ""){
            index = getNextIndex(length - phrase.length(), password);
            for(int i = index; i < index + phrase.length(); i++){
                password[i] = phrase.charAt(i-index);
            }
        }

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

    private double crackSpeed(){
        if(radioLow.isChecked()) return guessesLow;
        else if(radioMedium.isChecked()) return guessesMedium;

        return guessesHigh;
    }

    private void calcCrackTime(int length, int caps, int digits, int specials, String phrase, double guesses){

        int sumAlphabet = 0;
        String cracktimeStr = "";
        if(caps > 0 || phrase.matches(".*[A-Z].*")) sumAlphabet+=alpha_caps.length();
        if(digits > 0 || phrase.matches(".*\\d.*")) sumAlphabet+=alpha_digits.length();
        if(specials > 0 || phrase.matches(".*[!@#$%^&*_+-=/{}()<>;:'?|,.~`].*")) sumAlphabet+=alpha_specials.length();
        if(caps+digits+specials+phrase.length() < length || phrase.matches(".*[a-z].*")) sumAlphabet+=alpha_lowers.length();

        double cracktime = Math.pow(sumAlphabet, length) / guesses;

        if(cracktime < 60.0) cracktimeStr = smallFormatter.format(cracktime) + " seconds to crack";
        else if(cracktime < 3600.0) cracktimeStr = smallFormatter.format(cracktime / 60.0) + " minutes to crack";
        else if(cracktime < 86400.0) cracktimeStr = smallFormatter.format(cracktime / 3600.0) + " hours to crack";
        else if(cracktime < 604800.0) cracktimeStr = smallFormatter.format(cracktime/86400.0) + " days to crack";
        else if(cracktime < 2419200.0) cracktimeStr = smallFormatter.format(cracktime/604800.0) + " weeks to crack";
        else if(cracktime < 29030400.0) cracktimeStr = smallFormatter.format(cracktime/2419200.0) + " months to crack";
        else if(cracktime < 29030400.0 * 10.0) cracktimeStr = smallFormatter.format(cracktime/29030400.0) + " years to crack";
        else if(cracktime < 29030400.0 * 100.0) cracktimeStr = smallFormatter.format(cracktime/(29030400.0 * 10.0)) + " decades to crack";
        else if(cracktime < 29030400.0 * 1000.0) cracktimeStr = smallFormatter.format(cracktime/(29030400.0 * 100.0)) + " centuries to crack";
        else if(cracktime < 29030400.0 * 1000.0 * 1000000.0) cracktimeStr = smallFormatter.format(cracktime/(29030400.0 * 1000.0)) + " millenia to crack";
        else cracktimeStr = largeFormatter.format(cracktime/(29030400.0 * 1000.0)) + " millenia to crack";
        cracktimeText.setText(cracktimeStr);
    }

    private boolean fieldValidation(){

        lengthText.setError(null);
        capsText.setError(null);
        digitsText.setError(null);
        specialsText.setError(null);
        phraseText.setError(null);

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

        if(length >= 100 || length < 0){
            lengthText.setError("Length must be lower than 100 and greater than 0.");
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

        if(phraseText.getText().toString().trim().equalsIgnoreCase("")){
            phrase = "";
        }else{
            phrase = phraseText.getText().toString();
        }

        if(caps + digits + specials + phrase.length() > length){
            phraseText.setError("Sum of number of capitals, digits, special characters, and phrase "
                    + "length must be less than or equal to the desired length.");
            return false;
        }

        return true;
    }

}
