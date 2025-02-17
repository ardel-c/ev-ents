package com.example.ardeliachristina.events2;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.CharacterIterator;


public class RegisterActivity extends AppCompatActivity {

    Button register;
    EditText fullname;
    EditText username;
    EditText password;
    EditText phone;
    EditText address;
    RadioButton rbMale;
    RadioButton rbFemale;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = findViewById(R.id.btnRegister2);
        fullname = findViewById(R.id.etFullname);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        phone = findViewById(R.id.etPhoneNumber);
        address = findViewById(R.id.etAddress);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        databaseHelper = new DatabaseHelper(this);
        //request permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //validasi

                //check whether phone is all digit
                String ph = phone.getText().toString();
                boolean isDigits;
                    if(TextUtils.isDigitsOnly(phone.getText().toString())){
                        isDigits=true;
                    }else{
                        isDigits = false;
                    }

                    //check whether password is all alphabetic
                String pwd =password.getText().toString();

                    boolean allAlpha=true;
                    for(int i=0; i< pwd.length();i++) {
                        if (Character.isAlphabetic(pwd.charAt(i)) == false){
                            allAlpha = false;
                        }
                    }
                if(fullname.getText().toString().length()==0){
                    Toast.makeText(RegisterActivity.this, "Fullname must be filled", Toast.LENGTH_SHORT).show();
                }else if(username.getText().toString().length()==0){
                    Toast.makeText(RegisterActivity.this, "Username must be filled", Toast.LENGTH_SHORT).show();
                }else if(username.getText().toString().length()<5 || username.getText().toString().length()>30){
                    Toast.makeText(RegisterActivity.this, "Username must be between 5 and 30 characters", Toast.LENGTH_LONG).show();
                }else if(pwd.length()==0){
                    Toast.makeText(RegisterActivity.this, "Password must be filled", Toast.LENGTH_SHORT).show();
                }else if(pwd.length()<5){
                    Toast.makeText(RegisterActivity.this, "Password must be at least 5 characters long", Toast.LENGTH_SHORT).show();
                }else if(pwd.equals(pwd.toUpperCase())){
                    Toast.makeText(RegisterActivity.this, "Password must contain at least 1 lowercase letter", Toast.LENGTH_SHORT).show();
                }else if(pwd.equals(pwd.toLowerCase())){
                    Toast.makeText(RegisterActivity.this, "Password must contain at least 1 uppercase letter", Toast.LENGTH_SHORT).show();
                }else if(allAlpha){
                    Toast.makeText(RegisterActivity.this, "Password must contain at least 1 numeric character", Toast.LENGTH_SHORT).show();
                }else if(!isDigits){
                    Toast.makeText(RegisterActivity.this, "Phone number must be numeric", Toast.LENGTH_SHORT).show();
                }else if(phone.getText().toString().length()< 8 || phone.getText().toString().length()>20){
                    Toast.makeText(RegisterActivity.this, "Phone number must between 8 and 20 digits", Toast.LENGTH_SHORT).show();
                }else if(address.getText().toString().length()==0){
                    Toast.makeText(RegisterActivity.this, "Address must be filled", Toast.LENGTH_SHORT).show();
                }else if(!rbMale.isChecked() && !rbFemale.isChecked()) {
                    Toast.makeText(RegisterActivity.this, "Select gender first", Toast.LENGTH_SHORT).show();
                }
                    else {
                        String gender;
                        if(rbMale.isChecked()) gender="Male"; else gender="Female";

                        boolean isInserted;

                        isInserted = databaseHelper.insertUser(fullname.getText().toString().trim(), username.getText().toString().trim(),
                                password.getText().toString().trim(), phone.getText().toString().trim(),
                                address.getText().toString().trim(),gender);


                    LoginActivity.currentUserID = "US" + String.format("%03d", databaseHelper.getUserList().size()+1);

                    String message = "Registration Info : " + fullname + username + password + phone + address + gender;
                    String phone = ph;

                    try{
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(phone, null,message,null,null);
                        Toast.makeText(RegisterActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    Intent intent= new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);

                }
            }
        });
    }
}
