package ru.test.calendarnotes.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import ru.test.calendarnotes.R;

public class NewTaskActivity extends AppCompatActivity {

    private Calendar startDateAndTime = Calendar.getInstance();
    private Calendar finishDateAndTime = Calendar.getInstance();
    private TextView date, timeStart, timeFinish;
    private EditText newTaskName, newTaskDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        getSupportActionBar().setTitle(getString(R.string.new_task_string));

        date = findViewById(R.id.new_task_date);
        timeStart = findViewById(R.id.new_task_time_start);
        timeFinish = findViewById(R.id.new_task_time_finish);

        newTaskDescription = findViewById(R.id.new_task_description);
        newTaskName = findViewById(R.id.new_task_name);

        setInitialDateTime();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onTimeFinishClick(View v){
        new TimePickerDialog(this,
                finishTimeSetListener,
                finishDateAndTime.get(Calendar.HOUR_OF_DAY),
                finishDateAndTime.get(Calendar.MINUTE), true).show();
    }

    public void onTimeStartClick(View v){
        new TimePickerDialog(this, startTimeSetListener,
                startDateAndTime.get(Calendar.HOUR_OF_DAY),
                startDateAndTime.get(Calendar.MINUTE), true).show();
    }

    public void onDateClick(View v){
        new DatePickerDialog(this, dateSetListener,
                startDateAndTime.get(Calendar.YEAR),
                startDateAndTime.get(Calendar.MONTH),
                startDateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    TimePickerDialog.OnTimeSetListener startTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            startDateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            startDateAndTime.set(Calendar.MINUTE, minute);

            timeStart.setText(DateUtils.formatDateTime(getBaseContext(),
                    startDateAndTime.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_TIME));
        }
    };

    TimePickerDialog.OnTimeSetListener finishTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            finishDateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            finishDateAndTime.set(Calendar.MINUTE, minute);

            timeFinish.setText(DateUtils.formatDateTime(getBaseContext(),
                    finishDateAndTime.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_TIME));
        }
    };

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            startDateAndTime.set(Calendar.YEAR, year);
            startDateAndTime.set(Calendar.MONTH, month);
            startDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            finishDateAndTime.set(Calendar.YEAR, year);
            finishDateAndTime.set(Calendar.MONTH, month);
            finishDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            date.setText(DateUtils.formatDateTime(getBaseContext(),
                    startDateAndTime.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        }
    };

    private void setInitialDateTime() {

        date.setText(DateUtils.formatDateTime(this,
                startDateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));

        timeStart.setText(DateUtils.formatDateTime(this, startDateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));

        timeFinish.setText(DateUtils.formatDateTime(this, startDateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
    }
}
