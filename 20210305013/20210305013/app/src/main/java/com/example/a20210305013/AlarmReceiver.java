package com.example.a20210305013;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Alarm tetiklendiğinde burada yapılacak işlemleri tanımlayın
        // Örneğin, bir Toast mesajı gösterebilirsiniz
        Toast.makeText(context, "Alarm Triggered!", Toast.LENGTH_SHORT).show();
    }
}

