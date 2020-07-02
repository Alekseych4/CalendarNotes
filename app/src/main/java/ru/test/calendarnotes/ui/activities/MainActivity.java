package ru.test.calendarnotes.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import ru.test.calendarnotes.R;
import ru.test.calendarnotes.data.TaskModel;
import ru.test.calendarnotes.data.TaskViewModel;
import ru.test.calendarnotes.data.TimeIntervalsModel;
import ru.test.calendarnotes.ui.adapters.ScheduleAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String PICKED_DAY_START = "PICKED_DAY_START";

    private RecyclerView scheduleList;
    private Gson gson;
    private Realm realm;
    private TaskViewModel taskViewModel;
    private List<TimeIntervalsModel> schedule;
    private List<TaskModel> taskModelList;
    private DatePicker datePicker;
    private Calendar dayStartTime;
    private OnDateChangedListener adapterListener;
    private ScheduleAdapter scheduleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datePicker = findViewById(R.id.date_picker);
        scheduleList = findViewById(R.id.schedule_list);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        if (savedInstanceState != null){
            dayStartTime = (Calendar) savedInstanceState.getSerializable(PICKED_DAY_START);
        }else {
            initialDateSetup();
        }

        Log.d("Main onCreate", DateUtils.formatDateTime(this,
                dayStartTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));

        datePicker.setOnDateChangedListener(dateChangedListener);


        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), NewTaskActivity.class);
                startActivity(intent);
            }
        });


        //modelSerialization(t);
        addTimeIntervals();

        formList();

        scheduleList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        scheduleAdapter = new ScheduleAdapter(getBaseContext(), schedule, taskModelList, taskViewModel);
        adapterListener = scheduleAdapter;
        scheduleList.setAdapter(scheduleAdapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PICKED_DAY_START, dayStartTime);
    }

    @Override
    protected void onStop() {
        super.onStop();
        realm.close();
    }

    private void modelSerialization(TaskModel taskToSerialize){
        gson = new Gson();
        Log.d("Main", gson.toJson(taskToSerialize));
    }

    private void initialDateSetup(){
        dayStartTime = Calendar.getInstance();
        dayStartTime.set(Calendar.HOUR_OF_DAY, 0);
        //dayStartTime.set(Calendar.HOUR, 0);
        dayStartTime.set(Calendar.MINUTE, 0);
        dayStartTime.set(Calendar.SECOND, 0);
        dayStartTime.set(Calendar.MILLISECOND, 0);
    }

    DatePicker.OnDateChangedListener dateChangedListener = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dayStartTime.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
            //dayStartTime.set(Calendar.HOUR, 0);
            dayStartTime.set(Calendar.MILLISECOND, 0);

//            adapterListener.onDateClick();
            formList();
            scheduleAdapter.notifyDataSetChanged();
            //adapterListener.onDateClick();
        }
    };

    private void addTimeIntervals(){
        schedule = new ArrayList<>();
        for (int i = 0; i < 24; i++){
            TimeIntervalsModel intervals = new TimeIntervalsModel();

            intervals.setTimeStart(dayStartTime.getTimeInMillis());

            dayStartTime.add(Calendar.HOUR_OF_DAY, 1);

            intervals.setTimeFinish(dayStartTime.getTimeInMillis());

            schedule.add(intervals);
        }
        initialDateSetup();
    }

    public interface OnDateChangedListener{
        void onDateClick();
    }

    private void formList(){
        long start = dayStartTime.getTimeInMillis();
        dayStartTime.add(Calendar.DATE, 1);
        taskModelList = taskViewModel.getTasksForDay(start, dayStartTime.getTimeInMillis());
        Log.d("MAIN LIST", Integer.toString(taskModelList.size()));
    }
}
