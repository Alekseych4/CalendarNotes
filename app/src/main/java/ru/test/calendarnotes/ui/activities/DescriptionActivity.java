package ru.test.calendarnotes.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.TextView;

import java.util.Calendar;

import ru.test.calendarnotes.R;
import ru.test.calendarnotes.data.TaskModel;
import ru.test.calendarnotes.data.TaskViewModel;

public class DescriptionActivity extends AppCompatActivity {
    public static final String ID = "ID";
    private TaskViewModel taskViewModel;
    private TextView nameView, descriptionView, dateView, timeStartView, timeFinishView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        getSupportActionBar().setTitle(getString(R.string.descr));

        nameView = findViewById(R.id.task_name_descr);
        descriptionView = findViewById(R.id.task_description_desc);
        dateView = findViewById(R.id.task_date_desc);
        timeStartView = findViewById(R.id.task_time_start_desc);
        timeFinishView = findViewById(R.id.task_time_finish_desc);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        long taskId = 0;
        if (extras != null){
           taskId = extras.getLong(ID);
        }

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        TaskModel tm = taskViewModel.getTaskById(taskId);

        if (tm != null){
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(tm.getDateStart());

            nameView.setText(tm.getName());
            descriptionView.setText(tm.getDescription());
            dateView.setText(DateUtils.formatDateTime(this, c.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
            timeStartView.setText(DateUtils.formatDateTime(this, c.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_TIME));

            c.setTimeInMillis(tm.getDateFinish());
            timeFinishView.setText(DateUtils.formatDateTime(this, c.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_TIME));
        }
    }


}
