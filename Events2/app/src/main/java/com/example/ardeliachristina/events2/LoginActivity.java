package com.example.ardeliachristina.events2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public static String currentUserID;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_login);
        Button btnLogin2 = findViewById(R.id.btnLogin2);
        final EditText username = findViewById(R.id.etUsername);
        final EditText password = findViewById(R.id.etPassword);
        databaseHelper = new DatabaseHelper(this);

        btnLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*validasi*/
                if(username.getText().toString().length()==0){
                    Toast.makeText(LoginActivity.this, "Username must be filled", Toast.LENGTH_SHORT).show();
                }else if(password.getText().toString().length()==0){
                    Toast.makeText(LoginActivity.this, "Password must be filled", Toast.LENGTH_SHORT).show();
                }else{
                    int cek=0;

                    for(int i=0; i<databaseHelper.getUserList().size();i++) {
                        if(username.getText().toString().equals( databaseHelper.getUserList().get(i).username)){
                            cek=1;
                            Log.d("pwd1" , "p = " + databaseHelper.getUserList().get(i).password);
                            if(password.getText().toString().equals(databaseHelper.getUserList().get(i).password)){
                                Log.d("pwd" , "p = " + databaseHelper.getUserList().get(i).password);
                                cek=2;
                                break;
                            }
                        }else cek=0;
                    }

                    if(cek==0){
                        Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }else if(cek==1){
                        Toast.makeText(LoginActivity.this, "password is not correct", Toast.LENGTH_SHORT).show();
                    }else {

                     //   currentUserID = username.getText().toString();
                        for(int i=0;i<databaseHelper.getUserList().size();i++){
                            if(username.getText().toString().equals(databaseHelper.getUserList().get(i).username)){
                                currentUserID = databaseHelper.getUserList().get(i).userID; break;
                            }
                        }


                        Intent inten = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(inten);
                    }

                }
            }
        });


    }
}
