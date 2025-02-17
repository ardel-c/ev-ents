package com.example.ardeliachristina.events2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BuyActivity extends AppCompatActivity {

    EditText eventid;
    EditText qty;
    Button btnBuy, btnLoc;
    DatabaseHelper databaseHelper;
    public static double latitude, longitude;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        eventid = findViewById(R.id.eteventID);
        qty = findViewById(R.id.etQty);
        btnBuy = findViewById(R.id.buttonBuy);
        btnLoc = findViewById(R.id.btnLoc);
        databaseHelper = new DatabaseHelper(getBaseContext());

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check=0;
                for(int i=0;i<databaseHelper.getEventList().size();i++){
                    if(eventid.getText().toString().equals(databaseHelper.getEventList().get(i).eventID)){
                        check=1; break;
                    }
                }

                if(eventid.getText().toString().equals("")){
                    Toast.makeText(BuyActivity.this, "Insert EventID", Toast.LENGTH_SHORT).show();
                }else if(check == 0){
                    Toast.makeText(BuyActivity.this, "Event not found", Toast.LENGTH_SHORT).show();
                } else if(qty.getText().toString().equals("")){
                    Toast.makeText(BuyActivity.this, "Insert quantity", Toast.LENGTH_SHORT).show();
                } else if(Integer.valueOf(qty.getText().toString()) < 1){
                    Toast.makeText(BuyActivity.this, "quantity must not less than 1", Toast.LENGTH_SHORT).show();
                }else{

                    DateFormat df = new SimpleDateFormat("dd MM yyyy");
                    String date = df.format(Calendar.getInstance().getTime());
                    boolean insert;

                    insert = databaseHelper.insertTransaction(LoginActivity.currentUserID, eventid.getText().toString(), date, qty.getText().toString());
                    if(insert){
                        Toast.makeText(BuyActivity.this, "Ticket Bought", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(BuyActivity.this, "Process Failed", Toast.LENGTH_SHORT).show();
                    }

                    Intent home= new Intent(getApplicationContext(), HomeActivity.class);
                    home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(home);
                    }

            }
        });



        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check=0;
                for(int i=0;i<databaseHelper.getEventList().size();i++){
                    if(eventid.getText().toString().equals(databaseHelper.getEventList().get(i).eventID)){
                        check=1; break;
                    }
                }

                if(eventid.getText().toString().equals("")){
                    Toast.makeText(BuyActivity.this, "Insert EventID", Toast.LENGTH_SHORT).show();
                }else if(check == 0){
                    Toast.makeText(BuyActivity.this, "Event not found", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    latitude = databaseHelper.getEvent(eventid.getText().toString()).latitude;
                    longitude = databaseHelper.getEvent(eventid.getText().toString()).longitude;
                    startActivity(intent);
                }
            }
        });

    }


}
