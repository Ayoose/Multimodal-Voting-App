package com.example.myvote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

    MyDb mdb;
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase db, sqLiteDatabase;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String messageReceived = "";
        if (bundle != null) {
            //----retrieve the SMS message received
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                messageReceived += msgs[i].getMessageBody().toString();
                messageReceived += "\n";
            }

            mdb = new MyDb(context);
            db = mdb.getWritableDatabase();
            db.execSQL("insert into voting values (?,?);", new String[]{messageReceived, msgs[0].getOriginatingAddress()});

            sqLiteHelper = new SQLiteHelper(context);
            sqLiteDatabase = sqLiteHelper.getWritableDatabase();
            sqLiteDatabase.execSQL("update MyCandidates set votes = votes + 1 where id = ?", new String[]{messageReceived});
            //db.execSQL("update MyCandidates set votes = votes + 1 where id = ?", new String[]{messageReceived});
        }
    }
}
