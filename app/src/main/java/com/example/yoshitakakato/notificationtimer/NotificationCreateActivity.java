package com.example.yoshitakakato.notificationtimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Date;

public class NotificationCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_create);

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleNotification();
            }
        });


        final TextView textView = (TextView)findViewById(R.id.time_input);
        Calendar now = Calendar.getInstance();
        textView.setText(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));
        findViewById(R.id.layout_time_area).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar nowDt = Calendar.getInstance();
                TimePickerDialog dialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        textView.setText(hour + ":" + minute);
                    }
                }, nowDt.get(Calendar.HOUR_OF_DAY), nowDt.get(Calendar.MINUTE), true);
                dialog.show();
            }
        });
    }

    private void scheduleNotification(){
        Intent intent = new Intent(this, AlarmReceiver.class);
        //intent.putExtra(Constant.)
        //Title
        EditText titleView = (EditText)findViewById(R.id.titleInput);
        intent.putExtra(Constant.TITLE_KEY, titleView.getText());

        //Text
        EditText textView = (EditText)findViewById(R.id.textInput);
        intent.putExtra(Constant.TEXT_KEY, textView.getText());

        //URL
        EditText urlView = (EditText) findViewById(R.id.urlInput);
        intent.putExtra(Constant.URL_KEY, urlView.getText());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        TextView timeView = (TextView) findViewById(R.id.time_input);
        Calendar now = Calendar.getInstance();
        Calendar setDate = Calendar.getInstance();
        String[] time = timeView.getText().toString().split(":");
        String hour = time[0];
        String minute = time[1];
        setDate.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), Integer.parseInt(hour), Integer.parseInt(minute));

        int before = now.compareTo(setDate);

        switch(before){
            case(-1):
            case(0):
                break;
            case(1):
            default:
                setDate.add(Calendar.DATE, 1);
                break;
        }

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, setDate.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(this, "Alarm Set Success", Toast.LENGTH_SHORT).show();
    }
}
