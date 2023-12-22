package com.example.a20210305013;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText taskEditText;
    private Button datePickerButton, timePickerButton, addTaskButton;
    private ListView taskListView;
    private ArrayList<String> taskList;
    private ArrayAdapter<String> adapter;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskEditText = findViewById(R.id.taskEditText);
        datePickerButton = findViewById(R.id.datePickerButton);
        timePickerButton = findViewById(R.id.timePickerButton);
        addTaskButton = findViewById(R.id.addTaskButton);
        taskListView = findViewById(R.id.taskListView);

        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, taskList);
        taskListView.setAdapter(adapter);
        taskListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = taskEditText.getText().toString().trim();
                if (!task.isEmpty()) {
                    taskList.add(task);
                    adapter.notifyDataSetChanged();
                    taskEditText.setText("");
                }
            }
        });

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        taskListView.setOnItemClickListener((parent, view, position, id) -> {
            // Burada seçilen görevin işaretlenip işaretlenmediğini kontrol edebilirsiniz
            boolean isChecked = taskListView.isItemChecked(position);
            if (isChecked) {
                // İşaretlendiğinde yapılacak işlemler
                // Örneğin: taskList.set(position, taskList.get(position) + " ✓");
            } else {
                // İşaret kaldırıldığında yapılacak işlemler
                // Örneğin: taskList.set(position, taskList.get(position).replace(" ✓", ""));
            }
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        // Seçilen tarihi burada kullanabilirsiniz
                    }
                },
                mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;
                        // Seçilen saati burada kullanabilirsiniz
                    }
                },
                mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void setAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE);

        // Seçilen tarih ve saat için Calendar oluşturma
        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.set(Calendar.YEAR, mYear);
        alarmCalendar.set(Calendar.MONTH, mMonth);
        alarmCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        alarmCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        alarmCalendar.set(Calendar.MINUTE, mMinute);
        alarmCalendar.set(Calendar.SECOND, 0);

        long alarmTime = alarmCalendar.getTimeInMillis();

        // Belirlenen zamanı kullanarak alarmı ayarlama
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
    }

    public void onSetAlarmButtonClick(View view) {
        setAlarm();
    }
}











