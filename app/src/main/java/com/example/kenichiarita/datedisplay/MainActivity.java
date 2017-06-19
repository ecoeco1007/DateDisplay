package com.example.kenichiarita.datedisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Date now = new Date();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MINUTE, 60);

        final Date endDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MINUTE, 60);

        final Date afterStartDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MINUTE, 60);

        final Date afterEndDate = calendar.getTime();

        final ShiftDateView shiftDateView = (ShiftDateView) findViewById(R.id.dateView);
        shiftDateView.show(now, endDate, afterStartDate, afterEndDate);
    }
}
