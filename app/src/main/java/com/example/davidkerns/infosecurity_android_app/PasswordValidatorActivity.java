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

        Button show = (Button)findViewById(R.id.show_pword);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpass();
            }
        });

        Button gen = (Button)findViewById(R.id.genpass);
        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generate_sim_pass();
            }
        });
        EditText passField = (EditText)findViewById(R.id.passfield);
        passField.addTextChangedListener(passwordWatcher);

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

    void generate_sim_pass(){
        EditText passField = (EditText)findViewById(R.id.passfield);
        String password = passField.getText().toString();

        password = lettonum(password);

        password = fix_pass(password);

        passField.setText(password);

        showpass();
    }

    String lettonum(String oldpass){
        char[] newpass = oldpass.toCharArray();
        String oldlower = oldpass.toLowerCase();

        int indexa = oldlower.indexOf("a");
        if(indexa != -1)
            newpass[indexa] = '@';

        int indexe = oldlower.indexOf("e");
        if (indexe != -1)
            newpass[indexe] = '3';

        int indexi = oldlower.indexOf("i");
        if(indexi != -1)
            newpass[indexi] = '!';

        int indexo = oldlower.lastIndexOf("o");
        if(indexo != -1)
            newpass[indexo] = '0';

        int indexs = oldlower.indexOf("s");
        if(indexs != -1)
            newpass[indexs] = '$';



        return new String(newpass);
    }

    String fix_pass(String oldpass){

        char[] syms = "!@#$%^&*".toCharArray();

        if(!oldpass.matches(".*[a-zA-Z]+.*")){
            int letter;
            do{
                letter = (int)Math.floor(Math.random()*57+'A');
            }while(!(letter <= 90 || letter >= 97));

            oldpass += (char)letter;
        }

        if(!oldpass.matches(".*\\d+.*")) {
            int num = (int)Math.floor(Math.random()*10+'0');

            oldpass += (char)num;
        }


        if(!oldpass.matches(".*[!@#$%^&*]+.*")) {

            int sym = (int)Math.floor(Math.random()*syms.length);

            oldpass += syms[sym];
        }

        if(oldpass.length() < 10){
            while(oldpass.length() <10) {
                int toInsert = (int) Math.floor(Math.random()*3);

                char charAdd=' ';

                switch (toInsert) {
                    case 0:
                        charAdd = (char)((int) Math.floor(Math.random() * 26 + 'a'));
                        break;
                    case 1:
                        charAdd = syms[(int)Math.floor(Math.random()*syms.length)];
                        break;
                    case 2:
                        charAdd = (char) ((int) Math.floor(Math.random() * 10 + '0'));
                    default:
                        break;
                }
                oldpass += charAdd;
            }
        }

        return oldpass;
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

        if(password.length() >= 10) {
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
