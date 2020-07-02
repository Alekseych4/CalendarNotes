package ru.test.calendarnotes.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import io.realm.Realm;
import ru.test.calendarnotes.R;
import ru.test.calendarnotes.data.TaskModel;
import ru.test.calendarnotes.data.TaskViewModel;

public class NewTaskActivity extends AppCompatActivity {

    private static final String START_TIME_KEY = "date_and_time_start";
    private static final String FINISH_TIME_KEY = "date_and_time_finish";

    private Realm realm = Realm.getDefaultInstance();
    private TaskViewModel taskViewModel;

    private Calendar timeStart;
    private Calendar timeFinish;
    private TextView dateView, timeStartView, timeFinishView;
    private EditText newTaskNameView, newTaskDescriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        getSupportActionBar().setTitle(getString(R.string.new_task_string));

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        if (savedInstanceState != null){
            timeStart = (Calendar) savedInstanceState.getSerializable(START_TIME_KEY);
            timeFinish = (Calendar) savedInstanceState.get(FINISH_TIME_KEY);
        }else {
            timeStart = Calendar.getInstance();
            timeFinish = Calendar.getInstance();
        }
        dateView = findViewById(R.id.new_task_date);
        timeStartView = findViewById(R.id.new_task_time_start);
        timeFinishView = findViewById(R.id.new_task_time_finish);

        newTaskDescriptionView = findViewById(R.id.new_task_description);
        newTaskNameView = findViewById(R.id.new_task_name);

        setInitialDateTime();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(START_TIME_KEY, timeStart);
        outState.putSerializable(FINISH_TIME_KEY, timeFinish);

        super.onSaveInstanceState(outState);
    }

    public void onTimeFinishClick(View v){
        new TimePickerDialog(this,
                finishTimeSetListener,
                timeFinish.get(Calendar.HOUR_OF_DAY),
                timeFinish.get(Calendar.MINUTE), true).show();
    }

    public void onTimeStartClick(View v){
        new TimePickerDialog(this, startTimeSetListener,
                timeStart.get(Calendar.HOUR_OF_DAY),
                timeStart.get(Calendar.MINUTE), true).show();
    }

    public void onDateClick(View v){
        new DatePickerDialog(this, dateSetListener,
                timeStart.get(Calendar.YEAR),
                timeStart.get(Calendar.MONTH),
                timeStart.get(Calendar.DAY_OF_MONTH)).show();
    }

    TimePickerDialog.OnTimeSetListener startTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timeStart.set(Calendar.HOUR_OF_DAY, hourOfDay);
            timeStart.set(Calendar.MINUTE, minute);

            timeStartView.setText(DateUtils.formatDateTime(getBaseContext(),
                    timeStart.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_TIME));
        }
    };

    TimePickerDialog.OnTimeSetListener finishTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timeFinish.set(Calendar.HOUR_OF_DAY, hourOfDay);
            timeFinish.set(Calendar.MINUTE, minute);

            timeFinishView.setText(DateUtils.formatDateTime(getBaseContext(),
                    timeFinish.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_TIME));
        }
    };

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            timeStart.set(Calendar.YEAR, year);
            timeStart.set(Calendar.MONTH, month);
            timeStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            timeFinish.set(Calendar.YEAR, year);
            timeFinish.set(Calendar.MONTH, month);
            timeFinish.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            dateView.setText(DateUtils.formatDateTime(getBaseContext(),
                    timeStart.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        }
    };

    private void setInitialDateTime() {
        dateView.setText(DateUtils.formatDateTime(this,
                timeStart.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));

        timeStartView.setText(DateUtils.formatDateTime(this, timeStart.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));

        timeFinishView.setText(DateUtils.formatDateTime(this, timeFinish.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
    }

    public void onSaveButtonClick(View v){
        taskViewModel.setNewTaskData(newTaskNameView.getText().toString(),
                timeStart.getTimeInMillis(),
                timeFinish.getTimeInMillis(),
                newTaskDescriptionView.getText().toString());
        Toast.makeText(this, getString(R.string.task_saved_toast), Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}
