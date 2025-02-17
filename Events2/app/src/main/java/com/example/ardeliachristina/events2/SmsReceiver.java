package com.example.ardeliachristina.events2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle b = intent.getExtras();
        SmsMessage[] msgs = null;
        String toast = "";

        if(b != null){
            Object[] obj = (Object[]) b.get("pdus");
            msgs = new SmsMessage[obj.length];

            for(int i =0; i<msgs.length;i++){
                msgs[i] = SmsMessage.createFromPdu((byte[]) obj[i], b.getString("format"));
                toast += "SMS From " + msgs[i].getOriginatingAddress();
                toast += " " + msgs[i].getMessageBody();
                toast += "\n";
            }
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();

        }
    }
}
